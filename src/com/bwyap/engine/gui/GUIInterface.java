package com.bwyap.engine.gui;

import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.input.InputHandler;

/** An interface for a GUI that can hold GUI elements.
 * 
 * @author bwyap */
public interface GUIInterface
{
	/** Update elements in the GUI
	 * 
	 * @param timestep */
	public void update(float timestep);

	/** Poll input for the elements in the GUI
	 * 
	 * @param input
	 * @param timetep */
	public void handleInput(InputHandler input);

	/** Add a new element to the GUI
	 * 
	 * @param element */
	public void addElement(GUIElementInterface e, float x, float y);

	/** Remove an element from the GUI.
	 * Returns true if the element was
	 * found and was successfully removed.
	 * 
	 * @param  element
	 * @return */
	public boolean removeElement(GUIElementInterface e);
}
