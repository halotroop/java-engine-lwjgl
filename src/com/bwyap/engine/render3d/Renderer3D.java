package com.bwyap.engine.render3d;

import com.bwyap.engine.window.WindowInterface;

/** An abstract 3D renderer class which sets up the view and projection
 * matrices for rendering a 3D scene. The actual methods required to
 * render the scene using a rendering library must be implemented by
 * extending this class.
 * 
 * @author bwyap */
public abstract class Renderer3D
{
	public final Transformation transform;

	public Renderer3D()
	{ this.transform = new Transformation(); }

	/** Set up the view and projection matrices required to render a scene
	 * 
	 * @param window
	 * @param camera */
	public void startRender3D(WindowInterface window, Camera camera)
	{
		if (window.isResized())
		{
			window.setResized(false);
			camera.setDirty(true);
		}
		// Camera's view has changed. Update the view and projection matrices
		if (camera.isDirty())
		{
			camera.setDirty(false);
			// Update the projection matrix
			transform.updateProjectionMatrix(camera.getFOV(), window.getWidth(), window.getHeight(), camera.getZNear(), camera.getZFar());
			// Update view Matrix
			transform.updateViewMatrix(camera);
		}
		startRender3D();
	}

	/** Prepare implementation specific resources to render a 3D scene.
	 * The view and projection matrices are automatically updated. */
	public abstract void startRender3D();

	/** Render a scene to the screen.
	 * 
	 * @param scene */
	public final void renderScene(Scene scene)
	{ scene.renderLayers(this, transform.getProjectionMatrix(), transform.getViewMatrix()); }

	/** Set the rendering system to 2D ortho projection to render a GUI. */
	public abstract void startRenderGUI();

	/** Clean up resources after rendering to the screen. */
	public abstract void stopRender();

	/** Clean up resources used by the renderer.
	 * Override this method to */
	public abstract void cleanup();
}
