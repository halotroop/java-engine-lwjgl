package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.CheckBox;
import com.bwyap.engine.gui.element.vector.VectorCheckBox;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;

/** A rounded rectangular check box.
 * See {@link CheckBox}.
 * 
 * @author bwyap */
public class RoundedRectangularCheckBox extends VectorCheckBox implements IVectorRoundedRect
{
	private float radius;

	public RoundedRectangularCheckBox(float x, float y, float width, float radius, CheckBoxCheckStyle style)
	{
		super(x, y, width, width, style);
		this.radius = radius;
	}

	public RoundedRectangularCheckBox(float x, float y, float width, float radius)
	{
		super(x, y, width, width);
		this.radius = radius;
	}

	@Override
	public float getRadius()
	{ return radius; }

	/** {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p> */
	public void update(float timestep)
	{}
}
