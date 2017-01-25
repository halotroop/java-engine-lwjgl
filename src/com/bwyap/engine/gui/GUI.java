package com.bwyap.engine.gui;

import com.bwyap.engine.gui.element.RectangularPanel;
import com.bwyap.engine.input.InputHandler;

/**
 * A GUI Panel which is intended to span the whole screen.
 * Its origin is always at (0,0).
 * @author bwyap
 *
 */
public abstract class GUI extends RectangularPanel implements GUIInterface {
	
	public GUI(float width, float height) {
		super(0, 0, width, height);
		setRenderShape(false);
	}
	
	 
	@Override
	public final void handleInput(InputHandler input) {
		// Handle input with this panel as the bounds
		super.handleInput(input, this);
	}
	
	
}
