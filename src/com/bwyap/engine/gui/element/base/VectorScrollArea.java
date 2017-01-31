package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;

public abstract class VectorScrollArea extends ScrollArea implements IColouredVectorShape {
	
	protected final VectorShapeColourProperties colours;	
	
	
	public VectorScrollArea(float x, float y, float width, float height, float scrollLength, ScrollDirection scrollDirection) {
		super(x, y, width, height, scrollLength, scrollDirection);
		this.colours = new VectorShapeColourProperties();
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}

}
