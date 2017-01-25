package com.bwyap.lwjgl.engine.entity;


/**
 * A renderable object which supports an animated texture.
 * @author bwyap
 *
 */
public class AnimatedRenderableObject extends RenderableEntity implements AnimatedTexture {

	private int texturePosition;
	private float textureChangeSpeed;
	private float currentFrameTime;

	
	public AnimatedRenderableObject(String meshName) {
		super(meshName);
		this.texturePosition = 0;
		this.textureChangeSpeed = 0;
		this.currentFrameTime = 0;
	}

	
	/**
	 * {@inheritDoc}
	 * <p>
	 * <b>IMPORTANT:</b>
	 * When overriding this method to implement particle physics
	 * <b>ensure you call</b> {@code super.update(timestep)} to animate the particle.
	 * </p>
	 * @param timestep 
	 */
	@Override
	public void update(float timestep) {
		currentFrameTime += timestep;
		if (currentFrameTime >= textureChangeSpeed) {
			currentFrameTime = 0;
			texturePosition++;
		}
	}
	
	
	@Override
	public int getTexturePosition() {
		return texturePosition;
	}
	
	@Override
	public void setTextureChangeSpeed(float speed) {
		this.textureChangeSpeed = speed;
	}
	
	@Override
	public float getTextureChangeSpeed() {
		return textureChangeSpeed;
	}

}
