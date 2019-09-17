package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;

public interface IColouredVectorShape extends IVectorShape
{
	/** Gets the VectorShapeColour object for this GUI element
	 * 
	 * @return */
	public VectorShapeColourProperties colourProperties();
}
