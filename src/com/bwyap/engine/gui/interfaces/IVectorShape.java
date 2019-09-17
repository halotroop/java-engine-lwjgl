package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.gui.element.vector.EVectorShape;

public interface IVectorShape
{
	/** Get the shape of this Vector shaped GUI element */
	public EVectorShape getShape();

	/** Check if the shape should be rendered
	 * 
	 * @return */
	default public boolean renderShape()
	{ return true; }
}
