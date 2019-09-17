package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.Button;

/** A button that is rendered with a texture.
 * See {@link Button}.
 * 
 * @author bwyap */
public abstract class TexturedButton extends Button
{
	protected String texture, mouseoverTexture, pressedTexture;

	public TexturedButton(int x, int y, int width, int height)
	{ super(x, y, width, height); }

	/** Set the normal texture and mouseover texture to use by its name
	 * 
	 * @param texture */
	public void setTextures(String texture, String mouseoverTexture)
	{
		this.texture = texture;
		this.mouseoverTexture = mouseoverTexture;
	}

	/** Set the normal texture, mouseover and pressed texture to use by its name
	 * 
	 * @param texture */
	public void setTextures(String texture, String mouseoverTexture, String pressedTexture)
	{
		this.texture = texture;
		this.mouseoverTexture = mouseoverTexture;
		this.pressedTexture = pressedTexture;
	}

	/** Set the button texture to use by its name
	 * 
	 * @param texture */
	public void setTexture(String texture)
	{ this.texture = texture; }

	/** Get the name of the button texture
	 * 
	 * @return */
	public String getTexture()
	{ return texture; }

	/** Set the mouseover texture to use by its name
	 * 
	 * @param texture */
	public void setMouseoverTexture(String texture)
	{ this.mouseoverTexture = texture; }

	/** Get the name of the mouseover texture
	 * 
	 * @return */
	public String getMouseoverTexture()
	{ return mouseoverTexture; }

	/** Set the button pressed texture to use by its name
	 * 
	 * @param texture */
	public void setPressedTexture(String pressedTexture)
	{ this.pressedTexture = pressedTexture; }

	/** Get the name of the button pressed texture
	 * 
	 * @return */
	public String getPressedTexture()
	{ return pressedTexture; }
}
