package com.bwyap.lwjgl.engine.render3d.scene;

import static com.bwyap.lwjgl.mesh.InstancedMesh.INSTANCE_SIZE_FLOATS;
import static com.bwyap.lwjgl.mesh.InstancedMesh.MATRIX_SIZE_FLOATS;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.bwyap.engine.render3d.entity.RotatableEntity;
import com.bwyap.lwjgl.engine.entity.AnimatedTexture;
import com.bwyap.lwjgl.engine.entity.RenderableEntity;
import com.bwyap.lwjgl.engine.render3d.LWJGLRenderer;
import com.bwyap.lwjgl.engine.render3d.light.DirectionalLight;
import com.bwyap.lwjgl.engine.render3d.light.PointLight;
import com.bwyap.lwjgl.engine.render3d.light.SceneLighting;
import com.bwyap.lwjgl.engine.render3d.light.SpotLight;
import com.bwyap.lwjgl.engine.render3d.shader.PhongLightingShader;
import com.bwyap.lwjgl.mesh.InstancedMesh;
import com.bwyap.lwjgl.mesh.Mesh;

/** <p>
 * A scene layer that can hold renderable entities
 * that are lit by a Phong lighting shader.
 * All objects in the layer are updated and rendered
 * through the {@code update} and {@code render} methods.
 * </p>
 * <p>
 * All non-instanced meshes are rendered first, and
 * all objects with the same mesh are rendered in the
 * same cycle.
 * </p>
 * 
 * @author bwyap */
public abstract class LightedObjectLayer extends EntityLayer
{
	protected SceneLighting lighting;

	public LightedObjectLayer() throws Exception
	{
		super();
		lighting = new SceneLighting();
		shader = new PhongLightingShader();
		shader.init();
	}

	/** Get the {@code SceneLighting} object used to light
	 * the objects in this scene layer
	 * 
	 * @return */
	public SceneLighting getLighting()
	{ return lighting; }

	@Override
	protected void beginRender(Matrix4f projectionMatrix, Matrix4f viewMatrix)
	{
		shader.bind();
		shader.setUniform("projectionMatrix", projectionMatrix);
		shader.setUniform("textureSampler", 0);
		calculateLighting(lighting, viewMatrix);
	}

	/** {@inheritDoc}
	 * <p>
	 * It is assumed that all entities that share the same instanced mesh
	 * also share the same material type and texture.
	 * To have entities with the same instanced mesh but different textures,
	 * use a mesh with a different name (or use non-instanced meshes).
	 * </p> */
	@Override
	protected void renderObjects(LWJGLRenderer renderer, Matrix4f viewMatrix)
	{
		// Render non instanced objects
		shader.setUniform("isInstanced", 0);
		// Iterate through each mesh and render each object that has that mesh
		for (String meshName : meshMap.keySet())
		{
			Mesh mesh = Mesh.getMesh(meshName);
			renderer.renderMeshList(mesh, meshMap.get(meshName), (RenderableEntity o) ->
			{
				// Set the material information
				((PhongLightingShader) shader).setUniform("material", o.getMaterial());
				shader.setUniform("useTexture", o.isTextured() ? 1 : 0);
				if (o.isTextured())
				{
					// Set the texture information in the shader
					shader.setUniform("textureSampler", 0);
					shader.setUniform("numCols", o.getTexture().getCols());
					shader.setUniform("numRows", o.getTexture().getRows());
					shader.setUniform("nonInstancedTexOffset", new Vector2f(0, 0));
					renderer.setTexture(o.getTexture());
					renderer.setTransparent(o.getTexture().hasAlpha());
				}
				if (o instanceof AnimatedTexture)
				{
					// If the texture is animated, set the texture co-ordinates
					AnimatedTexture a = (AnimatedTexture) o;
					int col = a.getTexturePosition() % o.getTexture().getCols();
					int row = a.getTexturePosition() / o.getTexture().getCols();
					float texXOffset = (float) col / o.getTexture().getCols();
					float texYOffset = (float) row / o.getTexture().getRows();
					shader.setUniform("nonInstancedTexOffset", new Vector2f(texXOffset, texYOffset));
				}
				// Set the model view matrix uniform
				Matrix4f modelViewMatrix;
				if (o instanceof RotatableEntity)
					modelViewMatrix = renderer.transform.buildModelViewMatrix(o, viewMatrix);
				else modelViewMatrix = renderer.transform.buildModelBillboardMatrix(o);
				shader.setUniform("modelViewNonInstancedMatrix", modelViewMatrix);
			});
		}
		// Render instanced objects
		shader.setUniform("isInstanced", 1);
		// Iterate through each mesh and render each object that has that mesh
		for (String meshName : instancedMeshMap.keySet())
		{
			InstancedMesh mesh = (InstancedMesh) Mesh.getMesh(meshName);
			// Set up the instance data in the buffer
			List<RenderableEntity> list = instancedMeshMap.get(meshName);
			int chunkSize = mesh.getInstances();
			int cycles = list.size() / chunkSize + (list.size() % chunkSize == 0 ? 0 : 1);
			boolean billboard = !(list.get(0) instanceof RotatableEntity);
			boolean transparent = list.get(0).isTextured() ? (list.get(0).getTexture().hasAlpha()) : false;
			for (int c = 0; c < cycles; c++)
			{
				// It is expected that all meshes of the same instance in this 
				// layer are textured in the same way with the same material
				RenderableEntity object = instancedMeshMap.get(meshName).get(c);
				((PhongLightingShader) shader).setUniform("material", object.getMaterial());
				shader.setUniform("useTexture", object.isTextured() ? 1 : 0);
				if (object.isTextured())
				{
					// Set the texture information 
					shader.setUniform("textureSampler", 0);
					shader.setUniform("numCols", object.getTexture().getCols());
					shader.setUniform("numRows", object.getTexture().getRows());
					renderer.setTexture(object.getTexture());
				}
				// Set the instance data buffer for a chunk of entities
				for (int i = 0; i < chunkSize && (c * chunkSize + i < list.size()); i++)
				{
					int pos = i + (c * chunkSize);
					RenderableEntity o = list.get(pos);
					Matrix4f modelViewMatrix;
					if (billboard)
						modelViewMatrix = renderer.transform.buildModelBillboardMatrix(o);
					else modelViewMatrix = renderer.transform.buildModelViewMatrix(o, viewMatrix);
					modelViewMatrix.get(INSTANCE_SIZE_FLOATS * i, mesh.getInstanceDataBuffer());
					if (o instanceof AnimatedTexture)
					{
						// If the texture is animated, set the texture co-ordinates
						AnimatedTexture a = (AnimatedTexture) o;
						int col = a.getTexturePosition() % o.getTexture().getCols();
						int row = a.getTexturePosition() / o.getTexture().getCols();
						float textXOffset = (float) col / o.getTexture().getCols();
						float textYOffset = (float) row / o.getTexture().getRows();
						int buffPos = INSTANCE_SIZE_FLOATS * i + MATRIX_SIZE_FLOATS * 1;
						mesh.getInstanceDataBuffer().put(buffPos, textXOffset);
						mesh.getInstanceDataBuffer().put(buffPos + 1, textYOffset);
					}
				}
				// Render the instances
				int end = Math.min(list.size(), (c + 1) * chunkSize);
				renderer.renderMeshListInstanced(mesh, end - (c * chunkSize), billboard, transparent, mesh.getInstanceDataBuffer());
				mesh.getInstanceDataBuffer().clear();
			}
		}
	}

	@Override
	protected void endRender()
	{ shader.unbind(); }

	/** Calculate the lighting for the objects and set the shader uniforms
	 * 
	 * @param lighting
	 * @param viewMatrix */
	protected void calculateLighting(SceneLighting lighting, Matrix4f viewMatrix)
	{
		// Calculate lighting
		shader.setUniform("ambientLight", lighting.getAmbientLight());
		shader.setUniform("specularPower", lighting.getSpecularPower());
		// Get a copy of the directional light object and transform its position to view coordinates
		for (int i = 0; i < SceneLighting.MAX_DIR_LIGHTS; i++)
		{
			if (lighting.getDirectionalLights()[i] == null) continue;
			DirectionalLight currDirLight = new DirectionalLight(lighting.getDirectionalLights()[i]);
			Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
			dir.mul(viewMatrix);
			currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
			((PhongLightingShader) shader).setUniform("directionalLights", currDirLight, i);
		}
		// Get a copy of the point light object and transform its position to view coordinates
		for (int i = 0; i < SceneLighting.MAX_POINT_LIGHTS; i++)
		{
			if (lighting.getPointLights()[i] == null) continue;
			PointLight currPointLight = new PointLight(lighting.getPointLights()[i]);
			Vector3f lightPos = currPointLight.getPosition();
			Vector4f aux = new Vector4f(lightPos, 1);
			aux.mul(viewMatrix);
			lightPos.x = aux.x;
			lightPos.y = aux.y;
			lightPos.z = aux.z;
			((PhongLightingShader) shader).setUniform("pointLights", currPointLight, i);
		}
		// Get a copy of the spot light object and transform its position and cone direction to view coordinates
		for (int i = 0; i < SceneLighting.MAX_SPOT_LIGHTS; i++)
		{
			if (lighting.getSpotLights()[i] == null) continue;
			SpotLight currSpotLight = new SpotLight(lighting.getSpotLights()[i]);
			Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
			dir.mul(viewMatrix);
			currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));
			Vector3f spotLightPos = currSpotLight.getPointLight().getPosition();
			Vector4f auxSpot = new Vector4f(spotLightPos, 1);
			auxSpot.mul(viewMatrix);
			spotLightPos.x = auxSpot.x;
			spotLightPos.y = auxSpot.y;
			spotLightPos.z = auxSpot.z;
			((PhongLightingShader) shader).setUniform("spotLights", currSpotLight, i);
		}
	}
}
