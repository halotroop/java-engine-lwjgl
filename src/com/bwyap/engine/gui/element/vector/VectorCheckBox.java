package com.bwyap.engine.gui.element.vector;

import org.joml.Vector4f;

import com.bwyap.engine.gui.element.base.CheckBox;
import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;

/** A vector drawn check box.
 * See {@link CheckBox}.
 * 
 * @author bwyap */
public abstract class VectorCheckBox extends CheckBox implements IColouredVectorShape
{
	/** Style to use to indicate a checked check box.
	 * 
	 * @author bwyap */
	public enum CheckBoxCheckStyle
	{
		TICK, CROSS, DOT, NONE;
	}

	protected final VectorShapeColourProperties colours;
	private CheckBoxCheckStyle checkStyle;
	private float checkStrokeWidth;
	private float checkPadding;
	private final Vector4f checkColour;

	public VectorCheckBox(float x, float y, float width, float height, CheckBoxCheckStyle style)
	{
		super(x, y, width, height);
		this.colours = new VectorShapeColourProperties();
		this.checkColour = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.checkStyle = style;
		this.checkStrokeWidth = 2f;
		this.checkPadding = 3f;
	}

	public VectorCheckBox(float x, float y, float width, float height)
	{ this(x, y, width, height, CheckBoxCheckStyle.TICK); }

	/** Get the check style of the check box
	 * 
	 * @return */
	public CheckBoxCheckStyle getCheckStyle()
	{ return checkStyle; }

	/** Set the check style of the check box
	 * 
	 * @return */
	public void setCheckStyle(CheckBoxCheckStyle style)
	{ this.checkStyle = style; }

	/** Get the stroke width of the check mark
	 * 
	 * @return */
	public float getCheckStrokeWidth()
	{ return checkStrokeWidth; }

	/** Set the stroke width of the check mark
	 * 
	 * @param checkStrokeWidth */
	public void setCheckStrokeWidth(float checkStrokeWidth)
	{ this.checkStrokeWidth = checkStrokeWidth; }

	/** Get the amount of padding the check mark has from the edge of the check box
	 * 
	 * @return */
	public float getCheckPadding()
	{ return checkPadding; }

	/** Set the amount of padding the check mark has from the edge of the check box
	 * 
	 * @param style */
	public void setCheckPadding(float checkPadding)
	{ this.checkPadding = checkPadding; }

	/** Get the colour of the check mark
	 * 
	 * @return */
	public Vector4f getCheckColour()
	{ return checkColour; }

	/** Set the colour of the check mark
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a */
	public void setCheckColour(float r, float g, float b, float a)
	{
		checkColour.x = r;
		checkColour.y = g;
		checkColour.z = b;
		checkColour.w = a;
	}

	/** Set the colour of the check mark
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a */
	public void setCheckColour(Vector4f colour)
	{ setCheckColour(colour.x, colour.y, colour.z, colour.w); }

	@Override
	public VectorShapeColourProperties colourProperties()
	{ return colours; }
}
