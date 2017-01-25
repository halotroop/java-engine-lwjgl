package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.VectorTextField;
import com.bwyap.engine.gui.interfaces.IVectorRect;

/**
 * A vector drawn rectangular text field.
 * @author bwyap
 *
 */
public abstract class RectangularTextfield extends VectorTextField implements IVectorRect {

	public RectangularTextfield(int x, int y, float width, float height) {
		super(x, y, width, height);
		
	}

}
