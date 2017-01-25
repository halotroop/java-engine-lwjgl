package com.bwyap.engine.gui.interfaces;

import com.bwyap.engine.window.BoundsInterface;

/**
 * Provides methods that a GUI element with bounds should implement.
 * @author bwyap
 *
 */
public interface GUIBoundsInterface extends BoundsInterface {


	/**
	 * Check if a specified co-ordinate is within the bounds of this element
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean withinBounds(float x, float y);
	
	
	/**
	 * Get the width of the element
	 * @return
	 */
	public float getWidth();
	
	
	/**
	 * Get the height of this element
	 * @return
	 */
	public float getHeight();
		

	/**
	 * Get the padding amount for the top of the element
	 * @return
	 */
	public float getPaddingTop();
	
	
	/**
	 * Get the padding amount for the bottom of the element
	 * @return
	 */
	public float getPaddingBottom();
	
	
	/**
	 * Get the padding amount for the left of the element
	 * @return
	 */
	public float getPaddingLeft();
	
	
	/**
	 * Get the padding amount for the right of the element
	 * @return
	 */
	public float getPaddingRight();
	
	
}
