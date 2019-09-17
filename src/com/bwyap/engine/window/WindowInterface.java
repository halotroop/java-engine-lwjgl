package com.bwyap.engine.window;

import com.bwyap.engine.input.InputHandler;

/** An interface that specifies methods an engine
 * using a window should be able to access.
 * 
 * @author bwyap */
public interface WindowInterface extends BoundsInterface
{
	/** Check if the window should close
	 * 
	 * @return */
	public boolean shouldClose();

	/** Process events that should be handled
	 * by the window before the engine is updated. */
	public void processEvents();

	/** Swap the display buffers for rendering the window */
	public void swapDisplayBuffers();

	/** Get the input handler for the window
	 * 
	 * @return */
	public InputHandler getInputHandler();

	/** Get the width of the window
	 * 
	 * @return */
	public int getWidth();

	/** Get the height of the window
	 * 
	 * @return */
	public int getHeight();

	/** Get the current aspect ratio of the screen.
	 * 
	 * @return */
	public float getAspectRatio();

	/** Get the default title of the window.
	 * The currently displayed title of the window might be different.
	 * 
	 * @return */
	public String getDefaultTitle();

	/** Set the default title of the window.
	 * The currently displayed title of the window might be different.
	 * 
	 * @param title */
	public void setDefaultTitle(String title);

	/** Set the title of the window.
	 * This method will change what is actually
	 * displayed on the window's title bar.
	 * This does not change the default title of the window,
	 * which can be retrieved using {@code getDefaultTitle()}.
	 * 
	 * @param title */
	public void setTitle(String title);

	/** Set whether the window has been resized
	 * 
	 * @param resized */
	public void setResized(boolean resized);

	/** Check if the window has been resized
	 * 
	 * @return */
	public boolean isResized();

	/** Get the last measured FPS of the game
	 * 
	 * @return */
	public float getFPS();

	/** Set the title of the window to show the window's
	 * default title followed by the last measured FPS rate.
	 * This will only work if showFps has been enabled.
	 * 
	 * @param fps */
	public void setFpsDisplay(float fps);
}
