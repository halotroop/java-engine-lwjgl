package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;

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
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}
	
}
