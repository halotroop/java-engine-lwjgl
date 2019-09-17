package com.bwyap.lwjgl.engine.render3d.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import com.bwyap.engine.render3d.Renderer3D;
import com.bwyap.engine.render3d.SceneLayer;
import com.bwyap.lwjgl.engine.entity.RenderableEntity;
import com.bwyap.lwjgl.engine.render3d.LWJGLRenderer;
import com.bwyap.lwjgl.engine.render3d.shader.Shader;
import com.bwyap.lwjgl.mesh.InstancedMesh;
import com.bwyap.lwjgl.mesh.Mesh;

/** <p>
 * A scene layer that holds {@code RenderableEntity} objects.
 * All objects in the layer are updated and rendered through
 * the {@code update} and {@code render} methods.
 * </p>
 * <p>
 * The {@code RenderableEntity} objects are stored in two {@code Maps},
 * one which holds objects with non-instanced meshes one which
 * holds instanced meshes.
 * </p>
 * 
 * @author bwyap */
public abstract class EntityLayer implements SceneLayer<RenderableEntity>
{
	protected final Map<String, List<RenderableEntity>> meshMap;
	protected final Map<String, List<RenderableEntity>> instancedMeshMap;
	protected Shader shader;

	public EntityLayer()
	{
		meshMap = new HashMap<String, List<RenderableEntity>>();
		instancedMeshMap = new HashMap<String, List<RenderableEntity>>();
	}

	/** Get the map of meshes in this Scene layer and
	 * all associated renderable objects.
	 * 
	 * @return */
	public Map<String, List<RenderableEntity>> getGameMeshes()
	{ return meshMap; }

	/** Get the map of Instanced meshes in this Scene layer and
	 * all associated renderable objects.
	 * 
	 * @return */
	public Map<String, List<RenderableEntity>> getGameInstancedMeshes()
	{ return instancedMeshMap; }

	/** Add an object to the scene layer.
	 * Returns true if the object was added to the scene successfully.
	 * 
	 * @param object */
	@Override
	public boolean addEntity(RenderableEntity object)
	{
		// Check if the mesh exists
		Mesh mesh = Mesh.getMesh(object.getMeshName());
		if (mesh != null)
		{
			if (mesh instanceof InstancedMesh)
			{
				// Instanced mesh found in mesh map
				if (instancedMeshMap.containsKey(object.getMeshName()))
				{
					instancedMeshMap.get(object.getMeshName()).add(object);
				}
				// New instanced mesh for this layer
				else
				{
					ArrayList<RenderableEntity> list = new ArrayList<RenderableEntity>();
					list.add(object);
					instancedMeshMap.put(object.getMeshName(), list);
				}
			}
			else
			{
				if (meshMap.containsKey(object.getMeshName()))
				{
					// Mesh found in mesh map
					meshMap.get(object.getMeshName()).add(object);
				}
				else
				{
					// New mesh for this layer
					ArrayList<RenderableEntity> list = new ArrayList<RenderableEntity>();
					list.add(object);
					meshMap.put(object.getMeshName(), list);
				}
			}
			return true;
		}
		// Mesh doesn't exist
		else return false;
	}

	/** Remove an entity from the scene layer.
	 * Returns true if the entity was found and removed from the scene successfully. */
	@Override
	public boolean removeEntity(RenderableEntity object)
	{
		// Look through mesh map
		for (String m : meshMap.keySet())
		{
			if (meshMap.get(m).contains(object))
			{
				// Object found in one of the lists within the mesh map
				meshMap.get(m).remove(object);
				if (meshMap.get(m).size() == 0)
				{ meshMap.remove(m); }
				return true;
			}
		}
		// Look through instanced mesh map
		for (String m : instancedMeshMap.keySet())
		{
			if (instancedMeshMap.get(m).contains(object))
			{
				// Object found in one of the lists within the instanced mesh map
				instancedMeshMap.get(m).remove(object);
				if (instancedMeshMap.get(m).size() == 0)
				{ instancedMeshMap.remove(m); }
				return true;
			}
		}
		return false;
	}

	/** Update all objects contained in this layer */
	@Override
	public void update(float timestep)
	{
		for (String m : meshMap.keySet())
		{
			for (RenderableEntity o : meshMap.get(m))
			{ o.update(timestep); }
		}
		for (String m : instancedMeshMap.keySet())
		{
			for (RenderableEntity o : instancedMeshMap.get(m))
			{ o.update(timestep); }
		}
	}

	/** {@inheritDoc}
	 * <p>
	 * Requires {@code LWJGLRenderer}.
	 * </p> */
	@Override
	public final void render(Renderer3D renderer, Matrix4f projectionMatrix, Matrix4f viewMatrix)
	{
		beginRender(projectionMatrix, viewMatrix);
		renderObjects((LWJGLRenderer) renderer, viewMatrix);
		endRender();
	}

	/** Prepare the shader for rendering the entities
	 * 
	 * @param projectionMatrix
	 * @param viewMatrix */
	protected abstract void beginRender(Matrix4f projectionMatrix, Matrix4f viewMatrix);

	/** Render the entities in scene layer
	 * 
	 * @param renderer
	 * @param viewMatrix */
	protected abstract void renderObjects(LWJGLRenderer renderer, Matrix4f viewMatrix);

	/** Clean up the shader resources after rendering the entities */
	protected abstract void endRender();
}
