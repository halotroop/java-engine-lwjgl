package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.TextField;
import com.bwyap.engine.gui.element.base.VectorTextField;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;


/**
 * A vector drawn rounded rectangular text field.
 * See {@link TextField}.
 * @author bwyap
 *
 */
public abstract class RoundedRectangularTextfield extends VectorTextField implements IVectorRoundedRect {

	protected float radius;

	public RoundedRectangularTextfield(int x, int y, float width, float height, float radius) {
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
