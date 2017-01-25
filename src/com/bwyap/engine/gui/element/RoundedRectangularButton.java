package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.VectorButton;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;

/**
 * A rounded rectangular button that is drawn by the graphics rendering system.
 * @author bwyap
 *
 */
public abstract class RoundedRectangularButton extends VectorButton implements IVectorRoundedRect {

	protected float radius;
	
	public RoundedRectangularButton(int x, int y, float width, float height, float radius) {
		super(x, y, width, height);
		this.radius = radius;
	}
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	/**
	 * Set the radius of the corners
	 * @param radius
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
		
}
