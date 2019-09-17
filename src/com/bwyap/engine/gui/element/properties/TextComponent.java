package com.bwyap.engine.gui.element.properties;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.bwyap.engine.gui.element.base.ETextAlignment;

/** A class to hold information for a text component
 * to be rendered using the graphics rendering system.
 * 
 * @author bwyap */
public class TextComponent implements GUIProperty
{
	private String text = "";
	private float absoluteTextSize, textSize;
	private final Vector2f positionOffset;
	private final Vector4f textColour;
	private String fontName;
	private ETextAlignment alignment;
	private boolean clipText;
	private boolean scale;
	private boolean textBox;
	private float textBoxWidth;
	private float paddingTop, paddingBottom, paddingLeft, paddingRight;

	/** Create a text component with an empty string
	 * and default settings (Font size 20, white colour). */
	public TextComponent()
	{ this(1.0f, 1.0f, 1.0f, 1.0f); }

	/** Create a text component with the given string
	 * and default settings (Font size 20, white colour).
	 * 
	 * @param text */
	public TextComponent(String text)
	{
		this();
		setText(text);
	}

	/** Create a text component with an empty
	 * string and assign it the given colour.
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a */
	public TextComponent(float r, float g, float b, float a)
	{ this("", r, g, b, a); }

	/** Create a text component with the given
	 * string and assign it the given colour.
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a */
	public TextComponent(String text, float r, float g, float b, float a)
	{
		positionOffset = new Vector2f();
		textColour = new Vector4f(r, b, g, a);
		textSize = absoluteTextSize = 20.0f;
		clipText = true;
		scale = true;
		alignment = ETextAlignment.CENTER;
	}

	/** Set the text component's width for rendering as a text box.
	 * Use the {@code setTextBox} to enable rendering as a text box.
	 * 
	 * @param textBoxWidth */
	public void setTextBoxWidth(float textBoxWidth)
	{ this.textBoxWidth = textBoxWidth; }

	/** Set whether this text component should be rendered as a text box
	 * 
	 * @param textBox */
	public void setTextBox(boolean textBox)
	{ this.textBox = textBox; }

	/** Check if this text component should be rendered as a text box
	 * 
	 * @return */
	public boolean isTextBox()
	{ return textBox; }

	/** Get the width of the textbox in which the text should be rendered
	 * 
	 * @return */
	public float getTextBoxWidth()
	{ return textBoxWidth; }

	/** Get the position offset of the text component.
	 * This is relative to its alignment position.
	 * 
	 * @return */
	public Vector2f getOffset()
	{ return positionOffset; }

	/** Set the position offset of the text component.
	 * This is relative to its alignment position.
	 * 
	 * @param x
	 * @param y */
	public void setOffset(float x, float y)
	{
		positionOffset.x = x;
		positionOffset.y = y;
	}

	/** Set the padding for the text component
	 * 
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right */
	public void setPadding(float top, float bottom, float left, float right)
	{
		paddingTop = top;
		paddingBottom = bottom;
		paddingLeft = left;
		paddingRight = right;
	}

	public float getPaddingTop()
	{ return paddingTop; }

	public float getPaddingBottom()
	{ return paddingBottom; }

	public float getPaddingLeft()
	{ return paddingLeft; }

	public float getPaddingRight()
	{ return paddingRight; }

	/** Check if the text will be clipped to the bounds of the component.
	 * By default this is set to true.
	 * 
	 * @return */
	public boolean isClipText()
	{ return clipText; }

	/** Set whether the text will be clipped to the bounds of the component.
	 * 
	 * @param clipText */
	public void setClipText(boolean clipText)
	{ this.clipText = clipText; }

	/** Set the alignment of the text to render.
	 * The alignment tag can be used by the
	 * renderer to specify the text alignment,
	 * but it can be ignored.
	 * 
	 * @param alignment */
	public void setAlignment(ETextAlignment alignment)
	{ this.alignment = alignment; }

	/** Get the alignment of the text.
	 * 
	 * @return */
	public ETextAlignment getAlignment()
	{ return alignment; }

	/** Get the button text
	 * 
	 * @return */
	public String getText()
	{ return text; }

	/** Set the button text
	 * 
	 * @param text */
	public void setText(String text)
	{ this.text = text; }

	/** Append some text to the current text
	 * 
	 * @param text */
	public void appendText(String text)
	{ this.text += text; }

	/** Get the name of the font used to render the text
	 * 
	 * @return */
	public String getFontName()
	{ return fontName; }

	/** Set the name of the font used to render the text
	 * 
	 * @param font */
	public void setFontName(String font)
	{ this.fontName = font; }

	/** Get the colour of the text
	 * 
	 * @return */
	public Vector4f getTextColour()
	{ return textColour; }

	/** Set the colour of the text
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a */
	public void setTextColour(float r, float g, float b, float a)
	{
		this.textColour.x = r;
		this.textColour.y = g;
		this.textColour.z = b;
		this.textColour.w = a;
	}

	/** Get the text size
	 * 
	 * @return */
	public float getTextSize()
	{ return textSize; }

	/** Get the absolute text size (unmodified by screen scaling)
	 * 
	 * @return */
	public float getAbsoluteTextSize()
	{ return absoluteTextSize; }

	/** Set the absolute text size
	 * 
	 * @param size */
	public void setTextSize(float size)
	{ this.absoluteTextSize = size; }

	/** Check if the text component should be scaled according to its parent.
	 * Default is true.
	 * 
	 * @return */
	public boolean scale()
	{ return scale; }
}
