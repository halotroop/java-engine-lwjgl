package com.bwyap.enginedriver;

import com.bwyap.engine.gui.GUIControllerInterface;
import com.bwyap.enginedriver.game.test.TestGUIController;
import com.bwyap.enginedriver.game.test.TestScene;
import com.bwyap.lwjgl.engine.LWJGL3DEngine;
import com.bwyap.lwjgl.window.GLFWWindow;


public class GameEngine extends LWJGL3DEngine {
	
	
	public GameEngine(GLFWWindow window, int targetFPS) throws Exception {
		super(window, targetFPS);
		
	}
	
	
	@Override
	public void init() {
		setScene(new TestScene(window.getWidth(), window.getHeight()));
	}

	
	@Override
	public GUIControllerInterface initGUI() throws Exception {
		return new TestGUIController();
	}
	
}
