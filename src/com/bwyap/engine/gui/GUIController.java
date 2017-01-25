package com.bwyap.engine.gui;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.WindowInterface;


/**
 * A controller class that can render a GUI.
 * It uses a {@code GUIRenderer} to render
 * the current GUI which must be set. If the
 * currentGUI is {@code null}, nothing will
 * be rendered by the renderer.
 * @author bwyap
 *
 */
public abstract class GUIController implements GUIControllerInterface {

	
	protected GUI currentGUI;
	protected final GUIRenderer renderer;
	
	
	public GUIController(GUIRenderer renderer) {
		this.renderer = renderer;
	}
	
	
	/**
	 * Set the GUI to update and render
	 * @param gui
	 */
	public void setGUI(GUI gui) {
		this.currentGUI = gui;
	}
	
	
	@Override
	public final void handleInput(InputHandler input) {
		if (currentGUI != null) currentGUI.handleInput(input);
	}

	
	@Override
	public final void update(float timestep) {
		if (currentGUI != null) currentGUI.update(timestep);
	}
	

	@Override
	public final void render(WindowInterface window) {
		if (currentGUI != null) renderer.render(currentGUI, window);
	}
	
}
