package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.PanelWindow;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;

/** A rounded rectangular panel window.
 * See {@link PanelWindow}.
 * 
 * @author bwyap */
public abstract class RoundedRectangularPanelWindow extends PanelWindow implements IVectorRoundedRect
{
	private float radius;

	public RoundedRectangularPanelWindow(String title, float x, float y, float width, float height, float radius)
	{
		super(title, x, y, width, height);
		this.radius = radius;
	}

	@Override
	public float getRadius()
	{ return radius; }
}
