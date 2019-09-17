package com.bwyap.enginedriver.window;

import com.bwyap.engine.EngineInterface;
import com.bwyap.enginedriver.GameEngine;
import com.bwyap.lwjgl.engine.resource.LWJGLResourceManager;
import com.bwyap.lwjgl.window.GLFWWindow;

/** A concrete implementation of the GLFWWindow class for Domination.
 * 
 * @author bwyap */
public class DriverWindow extends GLFWWindow
{
	public static final String VERSION = "0.3";

	public DriverWindow(int width, int height, String title, boolean vSync, boolean polygonMode, boolean showFps)
	{ super(width, height, title, vSync, polygonMode, showFps); }

	public DriverWindow()
	{
		super(LWJGLResourceManager.instance().settings().getWidth(),
			LWJGLResourceManager.instance().settings().getHeight(),
			"EngineDriver v" + VERSION,
			LWJGLResourceManager.instance().settings().isVSync(),
			LWJGLResourceManager.instance().settings().usePolygonMode(),
			LWJGLResourceManager.instance().settings().showFpsWindow());
	}

	@Override
	public void loadGLRequiredResources()
	{
		try
		{
			LWJGLResourceManager.instance().loadDependentResources();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public EngineInterface createEngine() throws Exception
	{ return new GameEngine(this, LWJGLResourceManager.instance().settings().getFPS()); }
}
