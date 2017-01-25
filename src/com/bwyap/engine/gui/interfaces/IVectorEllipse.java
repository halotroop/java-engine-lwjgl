package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.gui.element.base.EVectorShape;

public interface IVectorEllipse extends IVectorShape {
	
	@Override
	default public EVectorShape getShape() {
		return EVectorShape.ELLIPSE;
	}
}
