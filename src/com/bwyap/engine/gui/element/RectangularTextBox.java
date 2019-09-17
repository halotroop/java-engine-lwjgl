package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.TextBox;
import com.bwyap.engine.gui.element.vector.VectorTextBox;
import com.bwyap.engine.gui.interfaces.IVectorRect;

/** A rectangular text box.
 * See {@link TextBox}.
 * 
 * @author bwyap */
public abstract class RectangularTextBox extends VectorTextBox implements IVectorRect
{
	public RectangularTextBox(float x, float y, float width, float height, float padding)
	{ super(x, y, width, height, padding); }
}
