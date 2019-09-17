package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.gui.element.vector.EVectorShape;

public interface IVectorEllipse extends IVectorShape
{
	@Override
	default public EVectorShape getShape()
	{ return EVectorShape.ELLIPSE; }
}
