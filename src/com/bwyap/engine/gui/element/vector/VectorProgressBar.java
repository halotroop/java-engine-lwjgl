package com.bwyap.engine.gui.element.vector;

import org.joml.Vector4f;

import com.bwyap.engine.gui.element.base.ProgressBar;
import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;

/** A Vector drawn progress bar.
 * See {@link ProgressBar}.
 * 
 * @author bwyap */
public abstract class VectorProgressBar extends ProgressBar implements IColouredVectorShape
{
	private final VectorShapeColourProperties colours;

	public VectorProgressBar(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		colours = new VectorShapeColourProperties();
		colours.setColour(new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));
	}

	@Override
	public VectorShapeColourProperties colourProperties()
	{ return colours; }
}
