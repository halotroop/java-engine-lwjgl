package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.gui.element.vector.EVectorShape;

public interface IVectorRoundedRect extends IVectorRect {

	/**
	 * Get the radius of the rounded corners
	 * @return
	 */
	public float getRadius();
	

	@Override
	default public EVectorShape getShape() {
		return EVectorShape.ROUNDED_RECT;
	}
	
}
