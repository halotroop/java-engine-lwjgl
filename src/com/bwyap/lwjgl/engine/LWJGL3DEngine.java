package com.bwyap.lwjgl.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import com.bwyap.engine.FixedTimestepEngine;
import com.bwyap.engine.gui.GUIControllerInterface;
import com.bwyap.engine.render3d.Scene;
import com.bwyap.engine.window.WindowInterface;
import com.bwyap.lwjgl.engine.render3d.LWJGLRenderer;

/** <p>
 * A fixed timestep engine that uses LWJGL to render a 3D scene.
 * If no scene is set, the scene rendering call will be skipped.
 * This engine also supports a GUI which will be updated and
 * rendered if set using the {@code initGUI()} method.
 * </p>
 * <p>
 * This rendering engine makes use of {@code Scene} objects.
 * Each tick, the current scene will be updated and rendered.
 * The {@code currentScene} is initially null and must be set
 * using the {@code setScene} method.
 * </p>
 * 
 * @author bwyap */
public abstract class LWJGL3DEngine extends FixedTimestepEngine
{
	private final LWJGLRenderer renderer;
	private final GUIControllerInterface guiController;
	private float fpsPrintTimer = 0;
	private Scene currentScene;

	public LWJGL3DEngine(WindowInterface window, int targetFPS) throws Exception
	{
		super(window, targetFPS);
		this.renderer = new LWJGLRenderer();
		this.guiController = initGUI();
	}

	/** Initialise the GUI system used by this engine.
	 * (Return {@code null} if the engine does not require a GUI)
	 * 
	 * @throws Exception */
	public abstract GUIControllerInterface initGUI() throws Exception;

	@Override
	public float getCurrentTime()
	{ return (float) glfwGetTime(); }

	/** Set the scene to be rendered.
	 * 
	 * @param scene */
	public void setScene(Scene scene)
	{ currentScene = scene; }

	@Override
	public final void update(float timestep)
	{
		if (currentScene != null)
		{
			currentScene.handleInput(window.getInputHandler(), timestep);
			currentScene.update(timestep);
		}
		if (guiController != null)
		{
			guiController.handleInput(window.getInputHandler());
			guiController.update(timestep);
		}
		//Print the FPS to the console.
		//TODO: remove this later.
		fpsPrintTimer += timestep;
		if (fpsPrintTimer >= 1)
		{
			fpsPrintTimer -= 1;
			//System.out.println((int)getFPS() + " fps");
		}
	}

	@Override
	public final void render(float timestep)
	{
		if (currentScene != null)
		{
			renderer.startRender3D(window, currentScene.getCamera());
			renderer.renderScene(currentScene);
		}
		if (guiController != null) guiController.render(window);
		renderer.stopRender();
	}

	@Override
	public void cleanup()
	{ renderer.cleanup(); }
}
