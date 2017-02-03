package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.Button;
import com.bwyap.engine.gui.element.base.VectorButton;
import com.bwyap.engine.gui.interfaces.IVectorRect;

/**
 * A rectangular button that is drawn by the graphics rendering system.
 * See {@link Button}.
 * @author bwyap
 *
 */
public abstract class RectangularButton extends VectorButton implements IVectorRect {

	
	public RectangularButton(int x, int y, float width, float height) {
		super(x, y, width, height);	
	}
	
}
