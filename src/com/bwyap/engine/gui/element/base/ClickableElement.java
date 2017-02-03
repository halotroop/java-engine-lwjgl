package com.bwyap.engine.gui.element.base;

import java.util.ArrayList;
import java.util.List;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.MouseDownInterface;
import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.input.InputMapping;
import com.bwyap.enginedriver.resource.Resource;

/**
 * A GUI element that can be clicked by specified mouse buttons.
 * Valid mouse buttons are in the list {@code acceptedButtons}.
 * 
 * @author bwyap
 *
 */
public abstract class ClickableElement extends GUIElement implements MouseDownInterface {
	
	protected List<Integer> acceptedButtons;
	
	protected boolean mouseOverReact;
	protected boolean mouseOver;
	protected int currentClick;
	

	public ClickableElement(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.currentClick = -1;
		this.acceptedButtons = new ArrayList<Integer>();
		mouseOverReact = true;
		
		// Default accepted buttons are mouse left and mouse right
		acceptedButtons.add(Resource.inputMapping.getBinding(InputMapping.MOUSE_LEFT));
		acceptedButtons.add(Resource.inputMapping.getBinding(InputMapping.MOUSE_RIGHT));
	}
	
	
	@Override
	public final void handleInput(InputHandler input, GUIBoundsInterface bounds) {
		if (enabled) { 
			// Check if the mouse is over the button's bounds
			if (bounds.withinBounds((float)input.getMouseX(), (float)input.getMouseY()) && 
					withinBounds((int)input.getMouseX(), (int)input.getMouseY())) {
				mouseOver = true;
				if (mouseOverReact) onMouseOver((int)input.getMouseX(), (int)input.getMouseY());
			}
			else mouseOver = false;
			
			// Check if the button has been clicked
			if (mouseOver && currentClick == -1) {
				for (int mouseButton : acceptedButtons) {
					if (input.isMouseDown(mouseButton)) {
						currentClick = mouseButton;
						onMouseDown((int)input.getMouseX(), (int)input.getMouseY(), mouseButton);
					}
				}
			}
			
			// Reset the click if the click has been released
			if (currentClick >= 0) {
				if (!input.isMouseDown(currentClick)) {
					if (bounds.withinBounds((float)input.getMouseX(), (float)input.getMouseY()) && 
							withinBounds((int)input.getMouseX(), (int)input.getMouseY())) {
						onMouseClicked((int)input.getMouseX(), (int)input.getMouseY(), currentClick);
					}
					currentClick = -1;
				}
			}
			
			onHandleInput(input, bounds);
		}
	}
	
	
	
	/**
	 * {@inheritDoc}
	 * <br/>
	 * Override this function for custom functionality
	 */
	@Override
	public void onMouseDown(float x, float y, int mouseButton) {
		
	}
	
	
	/**
	 * Handle how the GUI element should respond to input.
	 * This method is called by the parent's {@code handleInput} method.
	 * @param input
	 * @param bounds
	 */
	public abstract void onHandleInput(InputHandler input, GUIBoundsInterface bounds);
	
	
	@Override
	public boolean mouseOverReact() {
		return mouseOverReact;
	}
	
	
	/**
	 * Set whether the element should react when the mouse is over the element.
	 * The {@code onMouseClicked} method should still work when {@code mouseOverReact} is {@code false}.
	 * @param mouseOverReact
	 */
	public void setReactToMouseOver(boolean mouseOverReact) {
		this.mouseOverReact = mouseOverReact;
	}
	
	
	@Override
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	
	@Override
	public boolean isMouseDown() {
		return currentClick >= 0;
	}
	
	
	@Override
	public boolean isMouseDown(int mouseButton) {
		return currentClick == mouseButton;
	}
	
}
