package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.Panel;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;

/**
 * A rounded rectangular panel.
 * @author bwyap
 *
 */
public class RoundedRectangularPanel extends Panel implements IVectorRoundedRect {
	
	private float radius;

	public RoundedRectangularPanel(float x, float y, float width, float height, float radius) {
		super(x, y, width, height);
		this.radius = radius;
	}
	
	
	@Override
	public float getRadius() {
		return radius;
	}

}
