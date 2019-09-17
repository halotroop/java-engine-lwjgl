package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.element.properties.TextComponent;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.ITextDisplay;
import com.bwyap.engine.input.InputHandler;

/** An abstract model of a button that supports a text component.
 * 
 * @author bwyap */
public abstract class Button extends ClickableElement implements ITextDisplay
{
	protected final TextComponent text;

	public Button(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		text = new TextComponent(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public TextComponent getTextComponent()
	{ return text; }

	/** {@inheritDoc}
	 * <p>Override this method for custom update functionality.</p> */
	@Override
	public void onMouseOver(float x, float y)
	{}

	/** {@inheritDoc}
	 * <p>Override this method for custom update functionality.</p> */
	@Override
	public void update(float timestep)
	{}

	/** {@inheritDoc}
	 * <p>Override this method for custom input handling functionality.</p> */
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds)
	{}
}
