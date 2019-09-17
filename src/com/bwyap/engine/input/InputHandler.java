package com.bwyap.engine.input;

/** Provides methods to poll for input through the
 * keyboard and mouse. The actual keyboard and mouse
 * handlers must be updated manually and it is not
 * a responsibility of this class.
 * 
 * @author bwyap */
public class InputHandler implements KeyboardHandlerInterface, MouseHandlerInterface
{
	private KeyboardHandlerInterface keyboardHandler;
	private MouseHandlerInterface mouseHandler;

	public InputHandler(KeyboardHandlerInterface keyboardHandler, MouseHandlerInterface mouseHandler)
	{
		this.keyboardHandler = keyboardHandler;
		this.mouseHandler = mouseHandler;
	}

	@Override
	public boolean isKeyDown(int keycode)
	{ return keyboardHandler.isKeyDown(keycode); }

	@Override
	public char consumeLastTypedChar()
	{ return keyboardHandler.consumeLastTypedChar(); }

	@Override
	public boolean isMouseDown(int mouseButton)
	{ return mouseHandler.isMouseDown(mouseButton); }

	@Override
	public boolean isMouseDown()
	{ return mouseHandler.isMouseDown(); }

	@Override
	public double getMouseX()
	{ return mouseHandler.getMouseX(); }

	@Override
	public double getMouseY()
	{ return mouseHandler.getMouseY(); }

	@Override
	public boolean isMouseEntered()
	{ return mouseHandler.isMouseEntered(); }

	@Override
	public double getMouseScrollX()
	{ return mouseHandler.getMouseScrollX(); }

	@Override
	public double consumeMouseScrollX()
	{ return mouseHandler.consumeMouseScrollX(); }

	@Override
	public double getMouseScrollY()
	{ return mouseHandler.getMouseScrollY(); }

	@Override
	public double consumeMouseScrollY()
	{ return mouseHandler.consumeMouseScrollY(); }

	@Override
	public double getLastMouseScrollTime()
	{ return mouseHandler.getLastMouseScrollTime(); }

	@Override
	public int getKeyMods()
	{ return keyboardHandler.getKeyMods(); }

	@Override
	public int getKeysDown()
	{ return keyboardHandler.getKeysDown(); }
}
