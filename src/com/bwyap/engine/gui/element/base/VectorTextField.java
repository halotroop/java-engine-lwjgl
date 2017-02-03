package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.window.BoundsInterface;

/**
 * A text field with a vector drawn background.
 * See {@link TextField}.
 * @author bwyap
 *
 */
public abstract class VectorTextField extends TextField implements IColouredVectorShape {

	protected final VectorShapeColourProperties colours;		
	
	public VectorTextField(int x, int y, float width, float height) {
		super(x, y, width, height);
		this.colours = new VectorShapeColourProperties();
	}
	
	
	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent) {
		super.updateBounds(window, parent);
		
		if (!scaleAbsolute) {
			text.setTextSize(text.getAbsoluteTextSize ()* ((scaleX + scaleY) / 2));
		}
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}
	
}
