package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.ProgressBar;
import com.bwyap.engine.gui.element.vector.SolidVectorProgressBar;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;
import com.bwyap.engine.input.InputHandler;


/**
 * A rounded rectangular progress bar filled with a solid colour.
 * See {@link ProgressBar}.
 * @author bwyap
 *
 */
public class RoundedRectangularSolidProgressBar extends SolidVectorProgressBar implements IVectorRoundedRect {
	
	private float radius;
	
	
	public RoundedRectangularSolidProgressBar(float x, float y, float width, float height, float radius) {
		super(x, y, width, height);
		this.radius = radius;
	}
	
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p>
	 */
	@Override
	public void update(float timestep) { }

	
	@Override
	public void handleInput(InputHandler input, GUIBoundsInterface bounds) { }

	
}
