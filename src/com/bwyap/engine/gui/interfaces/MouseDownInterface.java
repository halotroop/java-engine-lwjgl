package com.bwyap.engine.gui.interfaces;

public interface MouseDownInterface extends MouseOverInterface {
	
	
	/**
	 * An action to perform when a specific mouse button is down over the GUI element
	 * @param x
	 * @param y
	 * @param mouseButton
	 */
	public void onMouseDown(float x, float y, int mouseButton);
	
	
	/**
	 * An action to perform when the GUI element is clicked by a specific mouse button
	 * (mouse down then released)
	 * @param x
	 * @param y
	 * @param mouseButton
	 */
	public void onMouseClicked(float x, float y, int mouseButton);
	
	
	/**
	 * Checks if ANY accepted mouse button is down on the GUI element
	 */
	public boolean isMouseDown();
	
	
	/**
	 * Checks if a specified mouse button is down on the GUI element
	 * @param mouseButton
	 */
	public boolean isMouseDown(int mouseButton);
}
