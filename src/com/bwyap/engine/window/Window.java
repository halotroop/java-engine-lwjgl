package com.bwyap.engine.window;

import org.joml.Vector2f;

import com.bwyap.engine.EngineInterface;
import com.bwyap.engine.input.InputHandler;

/** An abstract class that specifies an actual window
 * that runs an engine. The concrete implementation
 * must handle the intialisation and rendering of the
 * window, as well as setting up the input handler and
 * managing the engine itself.
 * 
 * @author bwyap */
public abstract class Window implements WindowInterface
{
	protected final int absoluteWidth, absoluteHeight;
	protected int width, height;
	protected String title;
	protected boolean resized;
	protected boolean showFps;
	protected int targetFPS = 60;
	protected EngineInterface engine;
	protected InputHandler input;

	public Window(int width, int height, String title, boolean showFps)
	{
		this.width = this.absoluteWidth = width;
		this.height = this.absoluteHeight = height;
		this.title = title;
		this.showFps = showFps;
		this.resized = false;
	}

	/** Creates a new EngineInterface object to be run by in the window.
	 * 
	 * @return
	 * @throws Exception */
	public abstract EngineInterface createEngine() throws Exception;

	/** Initialise the window */
	public abstract void init();

	/** Create the window and start the main loop */
	public abstract void start();

	/** Clean up resources created and used by the window */
	public abstract void dispose();

	@Override
	public final InputHandler getInputHandler()
	{ return input; }

	@Override
	public final int getWidth()
	{ return width; }

	@Override
	public final int getHeight()
	{ return height; }

	@Override
	public final float getAspectRatio()
	{ return (float) width / (float) height; }

	@Override
	public final String getDefaultTitle()
	{ return title; }

	@Override
	public final void setDefaultTitle(String title)
	{ this.title = title; }

	@Override
	public final void setTitle(String title)
	{ this.setWindowTitle(title); }

	/** Implementation specific method to set the
	 * title displayed on the window.
	 * 
	 * @param title */
	protected abstract void setWindowTitle(String title);

	@Override
	public void setResized(boolean resized)
	{ this.resized = resized; }

	@Override
	public boolean isResized()
	{ return resized; }

	@Override
	public final float getFPS()
	{ return engine.getFPS(); }

	@Override
	public final void setFpsDisplay(float fps)
	{
		if (showFps)
		{ this.setTitle(title + " (" + (int) fps + " fps)"); }
	}

	@Override
	public Vector2f getPosition()
	{ return new Vector2f(0.0f, 0.0f); }

	@Override
	public Vector2f getAbsolutePosition()
	{ return getPosition(); }

	@Override
	public float getPositionX()
	{ return 0.0f; }

	@Override
	public float getPositionY()
	{ return 0.0f; }

	@Override
	public Vector2f getBounds()
	{ return new Vector2f(width, height); }

	@Override
	public Vector2f getAbsoluteBounds()
	{ return new Vector2f(absoluteWidth, absoluteHeight); }

	@Override
	public Vector2f getOriginalBounds()
	{ return getAbsoluteBounds(); }
}
