package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.gui.element.base.EVectorShape;

public interface IVectorRect extends IVectorShape {

	@Override
	default public EVectorShape getShape() {
		return EVectorShape.RECTANGLE;
	}
}
