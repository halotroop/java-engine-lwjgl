package com.bwyap.enginedriver.window;

import com.bwyap.engine.EngineInterface;
import com.bwyap.enginedriver.GameEngine;
import com.bwyap.enginedriver.resource.Resource;
import com.bwyap.lwjgl.window.GLFWWindow;

 
/**
 * A concrete implementation of the GLFWWindow class for Domination.
 * @author bwyap
 *
 */
public class DriverWindow extends GLFWWindow {
	
	
	public static final String VERSION = "0.3";
	
	
	public DriverWindow(int width, int height, String title, boolean vSync, boolean polygonMode, boolean showFps) {
		super(width, height, title, vSync, polygonMode, showFps);
	}
	
	
	public DriverWindow() {
		super(Resource.Settings.getWidth(), 
				Resource.Settings.getHeight(), 
				"EngineDriver v" + VERSION, 
				Resource.Settings.isVSync(), 
				Resource.Settings.usePolygonMode(), 
				Resource.Settings.showFpsWindow());
	}
	
	
	@Override
	public void loadGLRequiredResources() {
		Resource.loadLWJGLTextures();
		Resource.loadMeshes();
	}

	
	@Override
	public EngineInterface createEngine() throws Exception {
		return new GameEngine(this, Resource.Settings.getFPS());
	}
	
}
