package com.bwyap.lwjgl.engine.particle;

import static com.bwyap.lwjgl.mesh.InstancedMesh.INSTANCE_SIZE_FLOATS;
import static com.bwyap.lwjgl.mesh.InstancedMesh.MATRIX_SIZE_FLOATS;

import org.joml.Matrix4f;

import com.bwyap.engine.particle.Particle;
import com.bwyap.engine.particle.ParticleSystemLogic;
import com.bwyap.lwjgl.engine.entity.AnimatedTexture;
import com.bwyap.lwjgl.engine.render3d.LWJGLRenderer;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;
import com.bwyap.lwjgl.engine.render3d.shader.Shader;
import com.bwyap.lwjgl.mesh.InstancedMesh;
import com.bwyap.lwjgl.mesh.Mesh;

/**
 * An abstract particle system that renders its particles
 * using the {@code LWJGLRenderer}. Particles can be assigned
 * a mesh and texture. (The mesh should be a quad or it may 
 * cause undesirable results)
 * system uses a 
 * @author bwyap
 *
 * @param <T> the particle type
 */
public abstract class ParticleSystem<T extends Particle> extends ParticleSystemLogic<T> {
	
	
	private String meshName;
	protected LWJGLTexture texture;
	
	
	public ParticleSystem(LWJGLTexture texture, int maxParticles, int numberToRelease, float releaseInterval) throws Exception {
		super(maxParticles, numberToRelease, releaseInterval);
		this.texture = texture;
		setMesh();
	}
	
	
	/**
	 * The mesh used by this particle system must be set in this method. 
	 * Otherwise, the Particle System will fail to render particles.
	 * This method is automatically called in the Particle System's constructor.
	 * @throws Exception throws an exception if the given mesh is not an instanced mesh.
	 */
	protected abstract void setMesh() throws Exception;
	
	
	/**
	 * Set the name of the mesh used by this particle system.
	 * The mesh should be an instanced mesh.
	 * @param meshName
	 * @throws Exception if the given mesh is not an instanced mesh.
	 */
	public void setMesh(String meshName) throws Exception {
		this.meshName = meshName;
		if (!(Mesh.getMesh(meshName) instanceof InstancedMesh)) {
			throw new Exception("Given mesh " + meshName + " is not an instanced mesh.");
		}
	}
	
	
	/**
	 * Render the particles in the system
	 * @param renderer
	 * @param shader
	 * @param viewMatrix
	 */
	public final void render(LWJGLRenderer renderer, Shader shader, Matrix4f viewMatrix) {
		shader.setUniform("numRows", texture.getRows());
		shader.setUniform("numCols", texture.getCols());
		shader.setUniform("textureSampler", 0);
		renderer.setTexture(texture);
		
		InstancedMesh mesh = (InstancedMesh) Mesh.getMesh(meshName);
		
		// Set up the instance data in the buffer
        int chunkSize = mesh.getInstances();
		int cycles = activeParticles.size() / chunkSize + (activeParticles.size() % chunkSize == 0 ? 0 : 1);
		
		for (int c = 0; c < cycles; c++) {
			// Set the instance data buffer for a chunk of entities
			for (int i = 0; i < chunkSize && (c * chunkSize + i < activeParticles.size()); i++) {
				int pos = i + (c * chunkSize);
				Particle p = activeParticles.get(pos);
				
				Matrix4f modelViewMatrix = renderer.transform.buildModelViewBillBoardMatrix(p, viewMatrix);
				modelViewMatrix.get(INSTANCE_SIZE_FLOATS * i, mesh.getInstanceDataBuffer());
				
				int buffPos = INSTANCE_SIZE_FLOATS * i + MATRIX_SIZE_FLOATS * 1;
				if (p instanceof AnimatedTexture) {
					// If the texture is animated, set the texture co-ordinates
					AnimatedTexture a = (AnimatedTexture) p;
					int col = a.getTexturePosition() % texture.getCols();
					int row = a.getTexturePosition() / texture.getCols();
					float textXOffset = (float) col / texture.getCols();
					float textYOffset = (float) row / texture.getRows();
					mesh.getInstanceDataBuffer().put(buffPos, textXOffset);
					mesh.getInstanceDataBuffer().put(buffPos + 1, textYOffset);
				}
				else {
					mesh.getInstanceDataBuffer().put(buffPos, 0);
					mesh.getInstanceDataBuffer().put(buffPos + 1, 0);
				}
			}
			
			// Render the instances
			int end = Math.min(activeParticles.size(), (c+1) * chunkSize);
			renderer.renderMeshListInstanced(mesh, (end - c * chunkSize), true, true, mesh.getInstanceDataBuffer());
			mesh.getInstanceDataBuffer().clear();
		}
	}

}
