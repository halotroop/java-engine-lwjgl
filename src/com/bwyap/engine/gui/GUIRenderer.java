package com.bwyap.engine.gui;

import com.bwyap.engine.gui.element.TexturedButton;
import com.bwyap.engine.gui.element.base.Panel;
import com.bwyap.engine.gui.element.properties.TextComponent;
import com.bwyap.engine.gui.element.vector.VectorButton;
import com.bwyap.engine.gui.element.vector.VectorRadioButton;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.window.WindowInterface;

/**
 * <p>
 * A renderer that can render GUI elements to the screen.
 * The concrete implementation of how the elements should be
 * rendered is library specific and must be provided in a
 * subclass.
 * </p>
 * <p>
 * Supported GUI elements:
 * <ul>
 * 	<li>GUI (panel)</li>
 * 	<li>Vector button</li>
 * 	<li>Textured button</li>
 * 	<li>Vector Textfield</li>
 * 	<li>Panel</li>
 * 	<li>Window panel</li>
 * 	<li>Label</li>
 * 	<li>Text box</li>
 * 	<li>Scroll area</li>
 * 	<li>Check boxes</li>
 * 	<li>Radio buttons</li>
 * </ul>
 * 
 * TODO:
 * <ul>
 * 	<li>Drop down list</li>
 * 	<li>List/table</li>
 * 	<li>Tabbed panels</li>
 * 	<li>Progress bar</li>
 * 	<li>Image</li>
 * </ul>
 * </p>
 * 
 * @author bwyap
 * 
 *
 */
public abstract class GUIRenderer implements GUIRendererInterface {

	
	@Override
	public final void render(GUI gui, WindowInterface window) {
		beginRenderGUI(window);
		renderGUI(gui, window);
		endRenderGUI();
	}
	
	
	/**
	 * Set up the resources to render the GUI
	 * @param window
	 */
	protected abstract void beginRenderGUI(WindowInterface window);
	
	
	/**
	 * <p>Render the GUI elements.</p>
	 * <p>
	 * Calls to draw all GUI elements should be made in this method.
	 * This method is called automatically.
	 * </p>
	 * @param gui
	 * @param window
	 */
	protected abstract void renderGUI(Panel panel, WindowInterface window);
	
	
	/**
	 * CLean up the resources after rendering the GUI
	 */
	protected abstract void endRenderGUI();
	
	

	/* ===================
	 *  AUXILIARY METHODS 
	 * =================== 
	 */
	
	
	/**
	 * Render text onto the screen. If the given font has not yet been loaded, it will be loaded 
	 * via the {@code NVGFont.acquireFont} method. This method should handle cases where the text
	 * is null appropriately.
	 * @param text the text component
	 * @param element the bounds the text component is attached to
	 */
	public abstract void renderText(TextComponent text, GUIBoundsInterface element);
	
	
	/**
	 * Render a vector drawn button
	 * @param button
	 */
	protected abstract void renderVectorButton(VectorButton button);
	
	
	/**
	 * Render a textured button
	 * @param button
	 */
	protected abstract void renderTexturedButton(TexturedButton button);
	
	
	/**
	 * Render a vector drawn radio button
	 * @param radio
	 */
	protected abstract void renderVectorRadioButton(VectorRadioButton radio);
	
	
	/**
	 * Render a coloured vector shape to the screen
	 * @param shape
	 * @param e
	 */
	protected abstract void renderColouredVectorShape(IColouredVectorShape shape, GUIBoundsInterface e, float offsetX, float offsetY);

	
}
