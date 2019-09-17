package com.bwyap.lwjgl.engine.render3d;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.function.Consumer;

import com.bwyap.engine.render3d.Renderer3D;
import com.bwyap.engine.render3d.entity.RotatableEntity;
import com.bwyap.lwjgl.engine.entity.RenderableEntity;
import com.bwyap.lwjgl.mesh.InstancedMesh;
import com.bwyap.lwjgl.mesh.Mesh;

/** Provides the methods for rendering
 * graphics to the screen using LWJGL.
 * 
 * @author bwyap */
public class LWJGLRenderer extends Renderer3D
{
	public LWJGLRenderer()
	{}

	@Override
	public void startRender3D()
	{
		// Clear OpenGL's buffers
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}

	@Override
	public void startRenderGUI()
	{}

	@Override
	public void stopRender()
	{}

	@Override
	public void cleanup()
	{}

	/* ===========================
	 *   LWJGL RENDERING METHODS
	 * ===========================
	 */
	/** Render a single mesh to the screen.
	 * All shader uniforms must have been set previously.
	 * 
	 * @param mesh */
	@Deprecated
	public void renderMesh(Mesh mesh)
	{
		beginMeshRender(mesh, false, false);
		glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		endMeshRender();
	}

	/** Render a list of entities using the provided mesh.
	 * All entities in the list must use the specified mesh
	 * but may have their own material and texture types.
	 * Each entities' uniforms and necessary buffer data
	 * must be set using the given {@code Consumer} object.
	 * 
	 * @param mesh
	 * @param entities
	 * @param consumer */
	public void renderMeshList(Mesh mesh, List<RenderableEntity> entities, Consumer<RenderableEntity> consumer)
	{
		boolean billboard = !(entities.get(0) instanceof RotatableEntity);
		beginMeshRender(mesh, billboard, false);
		for (RenderableEntity entity : entities)
		{
			consumer.accept(entity);
			glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
		}
		endMeshRender();
	}

	/** Render a list of entities using the provided
	 * {@code InstancedMesh} and instance data in the
	 * provided buffer. It is assumed that all required
	 * uniforms and instance data have been previously set.
	 * 
	 * @param mesh        the instanced mesh to render
	 * @param instances   the number of instances to render
	 * @param billboard   flag to indicate a billboard entity (depth mask is disabled)
	 * @param transparent flag to indicate transparent texture (alpha blending is enabled)
	 * @param buffer      the buffer containing the instance data */
	public void renderMeshListInstanced(InstancedMesh mesh, int instances, boolean billboard, boolean transparent, FloatBuffer buffer)
	{
		beginMeshRender(mesh, billboard, transparent);
		for (int i = 3; i < 8; i++)
		{ glEnableVertexAttribArray(i); }
		renderChunkInstanced(mesh, instances, buffer);
		for (int i = 3; i < 8; i++)
		{ glDisableVertexAttribArray(i); }
		endMeshRender();
	}

	/** Set the texture that the GPU should use for rendering a mesh
	 * 
	 * @param texture */
	public void setTexture(LWJGLTexture texture)
	{
		if (texture != null)
		{
			// Activate the first texture unit
			glActiveTexture(GL_TEXTURE0);
			// Bind the texture
			glBindTexture(GL_TEXTURE_2D, texture.getID());
		}
	}

	/** Render a chunk of instanced meshes.
	 * All instance data must have been previously set up properly.
	 * 
	 * @param mesh      the instanced mesh to render
	 * @param instances the number of instances to render
	 * @param buffer    the buffer containing the instance data */
	private void renderChunkInstanced(InstancedMesh mesh, int instances, FloatBuffer buffer)
	{
		glBindBuffer(GL_ARRAY_BUFFER, mesh.getInstanceDataVboID());
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		glDrawElementsInstanced(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0, instances);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/** Set up the resources for rendering the mesh
	 * 
	 * @param mesh */
	private void beginMeshRender(Mesh mesh, boolean transparent, boolean billboard)
	{
		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		if (billboard) glDepthMask(false);
		setTransparent(transparent);
	}

	/** Set the renderer to use transparent blending while rendering
	 * 
	 * @param transparent */
	public void setTransparent(boolean transparent)
	{
		if (transparent)
		{
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		}
		else
		{
			glDisable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	/** Clean up the resources after rendering the mesh
	 * 
	 * @param billboard */
	private void endMeshRender()
	{
		glDepthMask(true);
		glDisable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// Restore state
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
