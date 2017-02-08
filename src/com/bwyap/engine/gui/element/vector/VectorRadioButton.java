package com.bwyap.engine.gui.element.vector;

import org.joml.Vector4f;

import com.bwyap.engine.gui.element.base.RadioButton;
import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;


/**
 * A vector drawn radio button.
 * See {@link RadioButton}.
 * @author bwyap
 *
 */
public abstract class VectorRadioButton extends RadioButton implements IColouredVectorShape {
	
	protected final VectorShapeColourProperties colours;
	
	private final Vector4f checkColour;
	private float checkPadding;

	public VectorRadioButton(String name, float x, float y, float width, float height) {
		super(name, x, y, width, height);
		this.colours = new VectorShapeColourProperties();
		this.checkColour = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.checkPadding = 4f;
	}
	
	
	public VectorRadioButton(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.colours = new VectorShapeColourProperties();
		this.checkColour = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.checkPadding = 4f;
	}
	

	/** 
	 * Get the amount of padding between the check mark and the edge of the button
	 * @return
	 */
	public float getCheckPadding() {
		return checkPadding;
	}
	
	
	/** 
	 * Set the amount of padding between the check mark and the edge of the button
	 * @return
	 */
	public void setCheckPadding(float checkPadding) {
		this.checkPadding = checkPadding;
	}
	
	
	/** 
	 * Get the colour of the check mark 
	 * @return
	 */
	public Vector4f getCheckColour() {
		return checkColour;
	}
	
	
	/**
	 * Set the colour of the check mark 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setCheckColour(float r, float g, float b, float a) {
		checkColour.x = r;
		checkColour.y = g;
		checkColour.z = b;
		checkColour.w = a;
	}
	
	
	/**
	 * Set the colour of the check mark 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setCheckColour(Vector4f colour) {
		setCheckColour(colour.x, colour.y, colour.z, colour.w);
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}
	
}
