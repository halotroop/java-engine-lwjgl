package com.bwyap.engine.gui.element.vector;

import org.joml.Vector4f;

import com.bwyap.engine.gui.element.base.ProgressBar;


/**
 * A vector drawn progress bar that is filled with a solid colour.
 * See {@link ProgressBar}.
 * @author bwyap
 *
 */
public abstract class SolidVectorProgressBar extends VectorProgressBar {
	
	private final Vector4f fillColour;

	
	public SolidVectorProgressBar(float x, float y, float width, float height) {
		super(x, y, width, height);
		fillColour = new Vector4f(0.0f, 0.8f, 0.1f, 1.0f);
	}
	
	
	/**
	 * Set the fill colour of the progress bar
	 * @param fillColour
	 */
	public void setFillColour(Vector4f fillColour) {
		this.fillColour.x = fillColour.x; 
		this.fillColour.y = fillColour.y; 
		this.fillColour.z = fillColour.z; 
		this.fillColour.w = fillColour.w; 
	}
	
	
	/**
	 * Get the fill colour of the progress bar
	 * @return
	 */
	public Vector4f getFillColour() {
		return fillColour;
	}
}
