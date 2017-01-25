package com.bwyap.lwjgl.engine.entity;

public interface AnimatedTexture {
		

	/**
	 * Set the speed at which the texture changes.
	 * @param speed
	 */
	public void setTextureChangeSpeed(float speed);
	
	
	/**
	 * Get the speed at which the texture changes.
	 * @return
	 */
	public float getTextureChangeSpeed();
	
	
	/**
	 * Get the texture position at which the entity should be using 
	 * @return
	 */
	public int getTexturePosition();
}
