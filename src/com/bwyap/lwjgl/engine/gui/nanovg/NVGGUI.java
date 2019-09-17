package com.bwyap.lwjgl.engine.gui.nanovg;

import com.bwyap.engine.gui.GUI;

/** A GUI that uses NanoVG for rendering.
 * 
 * @author bwyap */
public abstract class NVGGUI extends GUI
{
	public NVGGUI(float width, float height)
	{ super(width, height); }

	/** Initialize NVG required objects */
	public abstract void init(NVGRenderer renderer);
}
