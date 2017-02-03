package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.PanelWindow;
import com.bwyap.engine.gui.interfaces.IVectorRect;

/**
 * A rectangular panel window.
 * See {@link PanelWindow}.
 * @author bwyap
 *
 */
public abstract class RectangularPanelWindow extends PanelWindow implements IVectorRect {
	
	public RectangularPanelWindow(String title, float x, float y, float width, float height) {
		super(title, x, y, width, height);
	}
	
}
