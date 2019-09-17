package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.ProgressBar;
import com.bwyap.engine.gui.element.vector.SolidVectorProgressBar;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.IVectorRect;
import com.bwyap.engine.input.InputHandler;

/** A rectangular progress bar filled with a solid colour.
 * See {@link ProgressBar}.
 * 
 * @author bwyap */
public class RectangularSolidProgressBar extends SolidVectorProgressBar implements IVectorRect
{
	public RectangularSolidProgressBar(float x, float y, float width, float height)
	{ super(x, y, width, height); }

	/** {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p> */
	@Override
	public void update(float timestep)
	{}

	@Override
	public void handleInput(InputHandler input, GUIBoundsInterface bounds)
	{}
}
