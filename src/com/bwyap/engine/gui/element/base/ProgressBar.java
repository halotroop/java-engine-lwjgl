package com.bwyap.engine.gui.element.base;

/** A progress bar that can be used to show the progress of a process.
 * 
 * @author bwyap */
public abstract class ProgressBar extends GUIElement
{
	/** Fill style (direction) of a progress bar.
	 * 
	 * @author bwyap */
	public enum ProgressFillStyle
	{
		UP, DOWN, LEFT, RIGHT;
	}

	private float progress;
	private ProgressFillStyle fillStyle;

	public ProgressBar(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.progress = 0.0f;
		this.fillStyle = ProgressFillStyle.RIGHT;
	}

	/** Get the fill style (direction) of the progress bar
	 * 
	 * @return */
	public ProgressFillStyle getFillStyle()
	{ return fillStyle; }

	/** Get the fill style (direction of the progress bar)
	 * 
	 * @param fillStyle */
	public void setFillStyle(ProgressFillStyle fillStyle)
	{ this.fillStyle = fillStyle; }

	/** Set the progress value of the bar.
	 * Values range between {@code 0.0f} to {@code 1.0f}.
	 * 
	 * @param progress */
	public void setProgress(float progress)
	{ this.progress = progress > 1.0f ? 1.0f : progress < 0.0f ? 0.0f : progress; }

	/** Add to the progress value of the bar.
	 * Values range between {@code 0.0f} to {@code 1.0f}.
	 * 
	 * @param progress */
	public void addProgress(float progress)
	{
		this.progress += progress;
		if (progress > 1.0f)
			progress = 1.0f;
		else if (progress < 0.0f) progress = 0.0f;
	}

	/** Get the progress value of the bar.
	 * Values range between {@code 0.0f} to {@code 1.0f}.
	 * 
	 * @param progress */
	public float getProgress()
	{ return progress; }
}
