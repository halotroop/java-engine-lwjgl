package com.bwyap.engine.window;

import org.joml.Vector2f;

public interface BoundsInterface
{
	/** Get the position of the element as a vector,
	 * relative to screen space.
	 * 
	 * @return */
	public Vector2f getPosition();

	/** Get the absolute position of the element as a vector.
	 * The absolute position of the element is its assigned position
	 * before any scaling due to screen resizing.
	 * 
	 * @return */
	public Vector2f getAbsolutePosition();

	/** Get the X position of the element,
	 * relative to screen space.
	 * 
	 * @return */
	public float getPositionX();

	/** Get the Y position of the element,
	 * relative to screen space.
	 * 
	 * @return */
	public float getPositionY();

	/** Get the bounds of the element
	 * 
	 * @return */
	public Vector2f getBounds();

	/** Get the absolute bounds of the element
	 * 
	 * @return */
	public Vector2f getAbsoluteBounds();

	/** Get the original bounds of the element
	 * (when the element was first created)
	 * 
	 * @return */
	public Vector2f getOriginalBounds();
}
