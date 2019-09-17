package com.bwyap.engine.gui.element.vector;

import com.bwyap.engine.gui.element.base.ScrollArea;
import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;

/** A scroll area with a vector drawn background.
 * See {@link ScrollArea}.
 * 
 * @author bwyap */
public abstract class VectorScrollArea extends ScrollArea implements IColouredVectorShape
{
	protected final VectorShapeColourProperties colours;

	public VectorScrollArea(float x, float y, float width, float height, float scrollLength, ScrollDirection scrollDirection)
	{
		super(x, y, width, height, scrollLength, scrollDirection);
		this.colours = new VectorShapeColourProperties();
	}

	@Override
	public VectorShapeColourProperties colourProperties()
	{ return colours; }
}
