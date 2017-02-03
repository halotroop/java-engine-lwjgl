package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.ScrollArea;
import com.bwyap.engine.gui.element.base.VectorScrollArea;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;


/**
 * A rounded rectangular scroll area. 
 * See {@link ScrollArea}.
 * @author bwyap
 *
 */
public class RoundedRectangularScrollArea extends VectorScrollArea implements IVectorRoundedRect {
	
	private float radius;
	
	public RoundedRectangularScrollArea(float x, float y, float width, float height, float scrollLength, ScrollDirection scrollDirection, float radius) {
		super(x, y, width, height, scrollLength, scrollDirection);
		this.radius = radius;
	}
	
	
	/**
	 * Set the radius of the corners
	 * @param radius
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	
	@Override
	public float getRadius() {
		return radius;
	}

}
