package com.bwyap.engine.render3d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import com.bwyap.engine.input.InputHandler;

/** <p>
 * A scene holds scene layers which can be rendered.
 * Each layer has its own shader which is used to
 * shade the objects in that layer.
 * A scene also has a camera which is used to view the layer.
 * </p>
 * <p>
 * Each layer and the camera are automatically updated in the
 * scene's {@code update} method. Use the {@code handleInput}
 * and {@code renderLayers} methods to poll input and render
 * the layers respectively.
 * </p>
 * 
 * @author bwyap */
public abstract class Scene
{
	private List<SceneLayer<?>> layers;
	protected Camera camera;

	public Scene(int width, int height)
	{
		camera = new Camera(Camera.DEFAULT_Z_NEAR, Camera.DEFAULT_Z_FAR, Camera.DEFAULT_FOV, (float) width / (float) height);
		layers = new ArrayList<SceneLayer<?>>();
	}

	/** Get the camera looking at the scene
	 * 
	 * @return */
	public Camera getCamera()
	{ return camera; }

	/** Add a layer to the scene
	 * 
	 * @param element */
	public void addLayer(SceneLayer<?> element)
	{ layers.add(element); }

	/** Remove a layer from the scene
	 * 
	 * @param  element
	 * @return */
	public boolean removeLayer(SceneLayer<?> element)
	{ return layers.remove(element); }

	/** Update each layer in the scene
	 * 
	 * @param timestep */
	public void update(float timestep)
	{
		for (SceneLayer<?> layer : layers)
		{ layer.update(timestep); }
		camera.update(timestep);
	}

	/** Handle the input for each element in the scene
	 * 
	 * @param input
	 * @param timestep */
	public void handleInput(InputHandler input, float timestep)
	{
		for (SceneLayer<?> layer : layers)
		{ layer.handleInput(input, timestep); }
	}

	/** Render each layer in the scene
	 * 
	 * @param projectionMatrix
	 * @param viewMatrix */
	public void renderLayers(Renderer3D renderer, Matrix4f projectionMatrix, Matrix4f viewMatrix)
	{
		for (SceneLayer<?> layer : layers)
		{ layer.render(renderer, projectionMatrix, viewMatrix); }
	}
}
