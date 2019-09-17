package com.bwyap.engine.gui.element.properties;

/** A class to hold the fade amount of a rendered element.
 * 
 * @author bwyap */
public class Fade implements GUIProperty
{
	private float alpha;
	private boolean fading;

	/** Set whether the vector shape is fading
	 * 
	 * @return */
	public boolean isFading()
	{ return fading; }

	/** Set whether the vector shape is fading
	 * 
	 * @param fading */
	public void setFading(boolean fading)
	{ this.fading = fading; }

	/** Get the fade value
	 * 
	 * @return */
	public float getFade()
	{ return alpha; }

	/** Set the fade value.
	 * Clamped between 0 and 1.
	 * 
	 * @param fade */
	public void setFade(float fade)
	{
		this.alpha = fade;
		// Clamp between 0 and 1
		decreaseFade(0);
		increaseFade(0);
	}

	/** Decrease the fade by the specified amount
	 * 
	 * @param amount */
	public void decreaseFade(float amount)
	{
		alpha -= amount;
		if (alpha < 0) alpha = 0;
	}

	/** Increase the fade by the specified amount
	 * 
	 * @param amount */
	public void increaseFade(float amount)
	{
		alpha += amount;
		if (alpha > 1) alpha = 1;
	}
}
