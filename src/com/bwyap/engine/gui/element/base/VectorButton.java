package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;

/**
 * A button that is rendered using the graphics rendering system.
 * It can be one of the shapes as enumerated by {@code ButtonShape}.
 * See {@link Button}.
 * @author bwyap
 *
 */
public abstract class VectorButton extends Button implements IColouredVectorShape {
	
	protected final VectorShapeColourProperties colours;	
	
	/**
	 * A button is given default mouseOver, mouseDown, border and borderMouseover colours.
	 * Initially the value of {@code hasBorder} is false.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param shape
	 */
	public VectorButton(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.colours = new VectorShapeColourProperties();
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}
	
}
