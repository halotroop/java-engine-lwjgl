package com.bwyap.engine.gui;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.WindowInterface;

/**
 * Provides methods that a GUI controller should implement.
 * @author bwyap
 *
 */
public interface GUIControllerInterface  {
	
	/**
	 * Polls input for the current GUI
	 * @param input
	 */
	public void handleInput(InputHandler input);
	
	
	/**
	 * Updates the current GUI
	 * @param timestep
	 */
	public void update(float timestep);
	
	
	/**
	 * Renders the current GUI
	 * @param window
	 */
	public void render(WindowInterface window);
	
}
