package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.input.InputHandler;

/** A radio button which can be selected. It holds its selected state until
 * it is reset or another radio button within its button group is selected.
 * Its state can be checked using the {@code isSelected} method. The methods
 * {@code onSelect} and {@code onDeselect} can be overridden to implement
 * custom functionality.
 * 
 * @author bwyap */
public abstract class RadioButton extends SelectableElement
{
	private String name;
	private RadioButtonGroup buttonGroup;

	/** Create a radio button with the specified name.
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height */
	public RadioButton(String name, float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.name = name;
	}

	/** Create a radio button with a default name of "unamed".
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height */
	public RadioButton(float x, float y, float width, float height)
	{ this("unamed", x, y, width, height); }

	/** Set the button group this radio button is attached to.
	 * 
	 * @param buttonGroup */
	protected void setButtonGroup(RadioButtonGroup buttonGroup)
	{ this.buttonGroup = buttonGroup; }

	/** Set the name of the radio button.
	 * The name is used purely for identification purposes within a button group.
	 * 
	 * @param name */
	public void setName(String name)
	{ this.name = name; }

	/** Get the name of the radio button.
	 * The name is used purely for identification purposes within a button group.
	 * 
	 * @return */
	public String getName()
	{ return name; }

	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds)
	{
		// Override SelectableElement functionality
	}

	/** {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * Ensure you also call {@code super.onSelected()} if overriding.
	 * </p> */
	public void onSelect()
	{ if (buttonGroup != null) buttonGroup.selectNew(this.getName()); }

	/** {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p> */
	public void onDeselect()
	{}
}
