package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.input.InputHandler;

/**
 * A radio button which can be selected. It holds its selected state until
 * it is reset or another radio button within its button group is selected. 
 * Its state can be checked using the {@code isSelected} method. The methods 
 * {@code onSelect} and {@code onDeselect} can be overridden to implement 
 * custom functionality.
 * @author bwyap
 *
 */

public abstract class RadioButton extends SelectableElement {
	
	private String name;
	
	
	public RadioButton(String name, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.name = name;		
	}
	

	public RadioButton(float x, float y, float width, float height) {
		this("null", x, y, width, height);
	}
	
	
	/**
	 * Set the name of the radio button.
	 * The name is used purely for identification purposes within a button group.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Get the name of the radio button.
	 * The name is used purely for identification purposes within a button group.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		// Override SelectableElement functionality
	}
	

	/**
	 * {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p>
	 */
 	public void onSelect() { }

	
 	/**
	 * {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p>
	 */
 	public void onDeselect() { }

}
