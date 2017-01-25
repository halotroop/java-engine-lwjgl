package com.bwyap.engine.gui.interfaces;

public interface MouseOverInterface {

	
	/**
	 * An action to perform when the mouse is over the GUI element
	 * @param x
	 * @param y
	 */
	public void onMouseOver(float x, float y);
	
	
	/**
	 * Checks if the mouse is over the GUI element
	 * @return
	 */
	public boolean isMouseOver();
	
}
