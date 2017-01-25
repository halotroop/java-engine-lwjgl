package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.Panel;
import com.bwyap.engine.gui.interfaces.IVectorRect;


/**
 * A rectangular panel.
 * @author bwyap
 *
 */
public class RectangularPanel extends Panel implements IVectorRect {
	

	public RectangularPanel(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	
	@Override
	public float getWidth() {
		return bounds.x;
	}
	
	
	@Override
	public float getHeight() {
		return bounds.y;
	}
	
}
