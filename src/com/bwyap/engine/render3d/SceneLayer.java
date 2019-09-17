package com.bwyap.engine.render3d;

import org.joml.Matrix4f;

import com.bwyap.engine.input.InputHandler;

/** <p>
 * A layer within the scene containing renderable entities.
 * Entities within this scene layer should use the same shader.
 * </p>
 * <p>
 * The type of entities to be contained in this layer should
 * be specified in the type parameter.
 * </p>
 * 
 * @author bwyap */
public interface SceneLayer<E>
{
	/** Add an entity to the scene.
	 * 
	 * @param  e
	 * @return */
	public boolean addEntity(E e);

	/** Remove an entity from the scene.
	 * Returns true if the entity was removed.
	 * 
	 * @param  e
	 * @return */
	public boolean removeEntity(E e);

	/** Update the entities contained in the scene layer.
	 * 
	 * @param timestep */
	public void update(float timestep);

	/** Handle the input for the entities contained in the scene layer.
	 * 
	 * @param input
	 * @param timestep */
	public void handleInput(InputHandler input, float timestep);

	/** Render the entities in the scene layer.
	 * 
	 * @param renderer
	 * @param projectionMatrix
	 * @param viewMatrix */
	public void render(Renderer3D renderer, Matrix4f projectionMatrix, Matrix4f viewMatrix);
}
