package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.RadioButton;
import com.bwyap.engine.gui.element.vector.VectorRadioButton;
import com.bwyap.engine.gui.interfaces.IVectorEllipse;


/**
 * A circular radio button.
 * See {@link RadioButton}.
 * @author bwyap
 *
 */
public class CircularRadioButton extends VectorRadioButton implements IVectorEllipse{

	
	public CircularRadioButton(String name, float x, float y, float radius) {
		super(name, x, y, radius, radius);
	}
	
	
	public CircularRadioButton(float x, float y, float radius) {
		super(x, y, radius, radius);
	}
	
	
	/**
	 * Set the radius and positional offset of the elliptical bounds.
	 * @param x offset
	 * @param y offset
	 * @param rx semi-major radius
	 * @param ry semi-major radius
	 */
	@Override
	public void setBounds(float xOffset, float yOffset, float rx, float ry) {
		super.setBounds(xOffset, yOffset, rx, ry);
	}
	
	
	/**
	 * Set the radius of the elliptical bounds.
	 * The offset of the bounding ellipse is set
	 * to (0, 0).
	 * @param rx semi-major radius
	 * @param ry semi-major radius
	 */
	@Override
	public void setBounds(float rx, float ry) {
		super.setBounds(rx, ry);
	}
	

	/**
	 * Set the radius of the circular bounds.
	 * The offset of the bounding circle is set
	 * to (0, 0).
	 * @param r radius for semi-major and semi-minor axes
	 */
	public void setBoundsRadius(float r) {
		setBounds(0, 0, r, r);
	}
	
	
	@Override
	public boolean withinBounds(float x, float y) {
		/*
		 * Formula:
		 * (x-h)^2/(rx)^2 + (y-k)^2/(ry)^2 <= 1
		 */
		float xtop = (float)Math.pow(x - (position.x + bounds.x + boundsOffset.x), 2);
		float xbot = (float)Math.pow(bounds.x, 2);
		float ytop = (float)Math.pow(y - (position.y + bounds.y + boundsOffset.y), 2);
		float ybot = (float)Math.pow(bounds.y, 2);
		
		return (xtop/xbot) + (ytop/ybot) <= 1.0f;
	}
	
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p>
	 */
	public void update(float timestep) { }
	
}
