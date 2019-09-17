package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.input.InputHandler;

/** A check box which can be selected. It holds its selected state until
 * it is clicked again. Its state can be checked using the {@code isSelected}
 * method. The methods {@code onSelect} and {@code onDeselect} can be overridden
 * to implement custom functionality.
 * 
 * @author bwyap */
public abstract class CheckBox extends SelectableElement
{
	public CheckBox(float x, float y, float width, float height)
	{ super(x, y, width, height); }

	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds)
	{
		// Override SelectableElement functionality
	}

	@Override
	public void onMouseClicked(float x, float y, int mouseButton)
	{
		if (isSelectable())
		{
			for (int button : acceptedButtons)
			{
				if (button == mouseButton)
				{
					if (!isSelected())
					{
						setSelected(true);
						onSelect();
					}
					else
					{
						setSelected(false);
						onDeselect();
					}
					break;
				}
			}
		}
	}

	/** {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p> */
	public void onSelect()
	{}

	/** {@inheritDoc}
	 * <p>
	 * Override this method to implement custom functionality.
	 * </p> */
	public void onDeselect()
	{}
}
