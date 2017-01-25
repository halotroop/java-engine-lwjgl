package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.VectorTextBox;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;


/**
 * A rounded rectangular text box.
 * @author bwyap
 *
 */
public abstract class RoundedRectangularTextBox extends VectorTextBox implements IVectorRoundedRect{
	
	private float radius;
	
	public RoundedRectangularTextBox(float x, float y, float width, float height, float radius, float padding) {
		super(x, y, width, height, padding);
		this.radius = radius;
	}
	
	
	/**
	 * Set the radius of the rounded rectangle
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
