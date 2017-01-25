package com.bwyap.engine.input;

/**
 * Methods for polling a MouseHandler.
 * @author bwyap
 *
 */
public interface MouseHandlerInterface {
	
	/**
	 * Checks if the specified mouse button is pressed.
	 * @param mouseButton
	 * @return
	 */
	public boolean isMouseDown(int mouseButton);
	
	/**
	 * Checks if any mouse button is pressed.
	 * @param mouseButton
	 * @return
	 */
	public boolean isMouseDown();
	
	/**
	 * Gets the x position of the mouse
	 * @return
	 */
	public double getMouseX();
	
	/**
	 * Gets the y position of the mouse
	 * @return
	 */
	public double getMouseY();
	
	/**
	 * Checks if the cursor's position is within the window
	 * @return
	 */
	public boolean isMouseEntered();
	
	/**
	 * Gets the mouse scroll x value
	 * @return
	 */
	public double getMouseScrollX();
	
	/**
	 * Gets the mouse scroll y value
	 * @return
	 */
	public double getMouseScrollY();
	
	/**
	 * Get the last time a mouse scroll was invoked
	 * @return
	 */
	public double getLastMouseScrollTime();
	
}
