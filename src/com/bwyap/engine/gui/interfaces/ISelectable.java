package com.bwyap.engine.gui.interfaces;

public interface ISelectable {
	
	/**
	 * Check whether the GUI element has been selected
	 * @return
	 */
	public boolean isSelected();
	
	
	/**
	 * Set whether the GUI element is currently selectable
	 * @param selectable
	 */
	public void setSelectable(boolean selectable);
	
	
	/**
	 * Check whether he GUI element is currently selectable
	 * @return
	 */
	public boolean isSelectable();
}
