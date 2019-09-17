package com.bwyap.engine.gui.element.base;

import java.util.HashMap;
import java.util.Map;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.input.InputHandler;

/** A RadioButtonGroup stores a collection of radio buttons which are
 * dependent on each other. When a radio button is selected, the
 * previously selected radio button is deselected automatically. Only
 * one radio button can be selected at a time in a radio button group.
 * 
 * @author bwyap */
public class RadioButtonGroup extends AbstractGUIElement
{
	private final Map<String, RadioButton> buttons;
	private String selectedButton;

	/** Create a new radio button group with no button selected by default. */
	public RadioButtonGroup()
	{
		buttons = new HashMap<String, RadioButton>();
		selectedButton = null;
	}

	/** Create a new radio button group with the specified radio
	 * button (if it exists) as the selected option by default.
	 * 
	 * @param defaultSelected */
	public RadioButtonGroup(String defaultSelected)
	{
		buttons = new HashMap<String, RadioButton>();
		selectedButton = defaultSelected;
	}

	/** Get the mapping of the buttons in the button group
	 * 
	 * @return */
	public Map<String, RadioButton> getButtonMap()
	{ return buttons; }

	/** Add a radio button to the group. */
	public void add(RadioButton button)
	{
		button.setButtonGroup(this);
		buttons.put(button.getName(), button);
	}

	/** Get a radio button with the specified name.
	 * If it does not exist, {@code null} will be returned.
	 * 
	 * @param  name
	 * @return */
	public RadioButton get(String name)
	{ return buttons.get(name); }

	/** Remove the radio button with the specified name from the button group.
	 * Returns the removed radio button if it exists.
	 * 
	 * @param  name
	 * @return */
	public RadioButton remove(String name)
	{
		RadioButton button = buttons.remove(name);
		if (button != null) button.setButtonGroup(null);
		return button;
	}

	/** Get the currently selected radio button.
	 * If no button is selected, {@code null} will be returned.
	 * 
	 * @return */
	public RadioButton getSelected()
	{
		if (selectedButton != null)
			return buttons.get(selectedButton);
		else return null;
	}

	/** Signal the button group that a new radioButton with the given name is being selected.
	 * This will deselect the previously selected button, ONLY IF the newly selected button
	 * and the previously selected button actually exists.
	 * 
	 * @param name */
	public void selectNew(String name)
	{
		if (buttons.containsKey(name))
		{
			if (buttons.containsKey(selectedButton))
			{
				RadioButton old = buttons.get(selectedButton);
				old.onDeselect();
				old.setSelected(false);
			}
			selectedButton = name;
		}
	}

	@Override
	public void update(float timestep)
	{}

	@Override
	public void handleInput(InputHandler input, GUIBoundsInterface bounds)
	{
		for (String name : buttons.keySet())
		{ buttons.get(name).handleInput(input, bounds); }
	}
}
