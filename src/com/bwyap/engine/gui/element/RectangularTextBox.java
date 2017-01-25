package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.VectorTextBox;
import com.bwyap.engine.gui.interfaces.IVectorRect;


/**
 * A rectangular text box.
 * @author bwyap
 *
 */
public abstract class RectangularTextBox extends VectorTextBox implements IVectorRect {

	public RectangularTextBox(float x, float y, float width, float height, float padding) {
		super(x, y, width, height, padding);
	}

}
