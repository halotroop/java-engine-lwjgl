package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.ScrollArea;
import com.bwyap.engine.gui.element.vector.VectorScrollArea;
import com.bwyap.engine.gui.interfaces.IVectorRect;


/**
 * A rectangular scroll area. 
 * See {@link ScrollArea}.
 * @author bwyap
 *
 */
public class RectangularScrollArea extends VectorScrollArea implements IVectorRect {

	public RectangularScrollArea(float x, float y, float width, float height, float scrollLength, ScrollDirection scrollDirection) {
		super(x, y, width, height, scrollLength, scrollDirection);
	}

}
