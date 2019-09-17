package com.bwyap.engine.input;

/** Methods for polling a KeyboardHandler.
 * 
 * @author bwyap */
public interface KeyboardHandlerInterface
{
	/** Check if a key is down
	 * 
	 * @param  keycode
	 * @return */
	public boolean isKeyDown(int keycode);

	/** Get the integer representing which mod keys are active.
	 * 
	 * @return */
	public int getKeyMods();

	/** Gets the number of keys that are down (pressed) at the moment.
	 * 
	 * @return */
	public int getKeysDown();

	/** Gets the last typed character from the keyboard
	 * 
	 * @return */
	public char consumeLastTypedChar();
}
