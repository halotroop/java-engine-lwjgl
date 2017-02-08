package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.CheckBox;
import com.bwyap.engine.gui.element.vector.VectorCheckBox;
import com.bwyap.engine.gui.interfaces.IVectorRect;

/**
 * A rectangular check box.
 * See {@link CheckBox}.
 * @author bwyap
 *
 */
public class RectangularCheckBox extends VectorCheckBox implements IVectorRect {

	public RectangularCheckBox(float x, float y, float width, CheckBoxCheckStyle style) {
		super(x, y, width, width, style);
	}
	
	
	public RectangularCheckBox(float x, float y, float width) {
		super(x, y, width, width);
	}

	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p>
	 */
	public void update(float timestep) { }

}
