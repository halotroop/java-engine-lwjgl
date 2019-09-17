package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.BoundsInterface;

/** An image holder represents a set of bounds in which to render an image.
 * The image to render is specified by name and its ID retrieved from the
 * {@code NVGTexture} class' static map when rendering.
 * 
 * @author bwyap */
public class ImageHolder extends GUIElement
{
	private String textureName;
	private float imageX, imageY, absoluteImageX, absoluteImageY;
	private float imageWidth, imageHeight, absoluteImageWidth, absoluteImageHeight;

	/** Create an image holder with the specified image, with custom
	 * parameters for the image's position and dimensions.
	 * 
	 * @param textureName
	 * @param x
	 * @param y
	 * @param imageX
	 * @param imageY
	 * @param width
	 * @param height
	 * @param imageWidth
	 * @param imageHeight */
	public ImageHolder(String textureName, float x, float y, float imageX, float imageY, float width, float height, float imageWidth, float imageHeight)
	{
		super(x, y, width, height);
		this.imageX = this.absoluteImageX = imageX;
		this.imageY = this.absoluteImageY = imageY;
		this.imageWidth = this.absoluteImageWidth = imageWidth;
		this.imageHeight = this.absoluteImageHeight = imageHeight;
		this.textureName = textureName;
	}

	/** Create an image holder with the specified image, with
	 * its position and dimensions the same as the image holder.
	 * 
	 * @param textureName
	 * @param x
	 * @param y
	 * @param width
	 * @param height */
	public ImageHolder(String textureName, float x, float y, float width, float height)
	{ this(textureName, x, y, x, y, width, height, width, height); }

	/** Set the absolute x-position of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @param imageX */
	public void setImageX(float imageX)
	{ this.absoluteImageX = imageX; }

	/** Get the x-position of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @return */
	public float getImageX()
	{ return imageX; }

	/** Set the absolute y-position of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @param imageY */
	public void setImageY(float imageY)
	{ this.absoluteImageY = imageY; }

	/** Get the y-position of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @return */
	public float getImageY()
	{ return imageY; }

	/** Set the absolute width of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @param imageWidth */
	public void setImageWidth(float imageWidth)
	{ this.absoluteImageWidth = imageWidth; }

	/** Get the width of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @return */
	public float getImageWidth()
	{ return imageWidth; }

	/** Set the absolute height of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @param imageHeight */
	public void setImageHeight(float imageHeight)
	{ this.absoluteImageHeight = imageHeight; }

	/** Get the height of the image.
	 * The image is clipped by the bounds of the image holder.
	 * 
	 * @return */
	public float getImageHeight()
	{ return imageHeight; }

	/** Get the name of the texture
	 * 
	 * @return */
	public String getTextureName()
	{ return textureName; }

	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent)
	{
		super.updateBounds(window, parent);
		imageX = scaleX * absoluteImageX;
		imageY = scaleY * absoluteImageY;
		imageWidth = scaleX * absoluteImageWidth;
		imageHeight = scaleY * absoluteImageHeight;
	}

	@Override
	public void update(float timestep)
	{}

	@Override
	public void handleInput(InputHandler input, GUIBoundsInterface bounds)
	{}
}
