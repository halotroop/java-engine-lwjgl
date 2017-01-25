package com.bwyap.engine.gui;

import org.joml.Vector2f;

import com.bwyap.engine.gui.element.Label;
import com.bwyap.engine.gui.element.base.Button;
import com.bwyap.engine.gui.element.base.Panel;
import com.bwyap.engine.gui.element.base.VectorTextBox;
import com.bwyap.engine.gui.element.base.VectorTextField;
import com.bwyap.engine.window.WindowInterface;


/**
 * An interface for methods that a GUI renderer should provide.
 * @author bwyap
 *
 */
public interface GUIRendererInterface {


	/**
	 * Renders the GUI on the window. 
	 * This step should be done after other elements are rendered.
	 * All preparation for GUI rendering should be handled by the GUI system.
	 * @param window
	 */
	public void render(GUI gui, WindowInterface window);

	
	/*
	 * =================
	 * RENDERING METHODS
	 * =================
	 * The following methods must be implemented to be able 
	 * to render the provided GUI elements in this library.
	 */
	
	
	/**
	 * Render a panel and the elements contained within it
	 * @param panel the panel to render
	 * @param window the bounding window
	 * @param parent the parent of the panel
	 */
	public void renderPanel(Panel panel, WindowInterface window, Vector2f parent);
	
	
	/**
	 * Render a label
	 * @param label
	 */
	public void renderLabel(Label label);
	
	
	/**
	 * Render a text field
	 * @param textfield
	 */
	public void renderVectorTextField(VectorTextField textfield);

	
	/**
	 * Render a text box
	 * @param textBox
	 */
	public void renderTextBox(VectorTextBox textBox);
	
	
	/**
	 * Render a button
	 * @param button
	 */
	public void renderButton(Button button);
	
}
