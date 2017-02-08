package com.bwyap.engine.gui.element.vector;

import com.bwyap.engine.gui.element.base.TextBox;
import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;


/**
 * A text box with a vector drawn background.
 * See {@link TextBox}.
 * @author bwyap
 *
 */
public abstract class VectorTextBox extends TextBox implements IColouredVectorShape {
		
	protected final VectorShapeColourProperties colours;
	
	public VectorTextBox(float x, float y, float width, float height, float padding) {
		super(x, y, width, height, padding);
		this.colours = new VectorShapeColourProperties();
	}


	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}

}
