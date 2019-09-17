package com.bwyap.lwjgl.engine.particle;

import com.bwyap.engine.particle.Particle;
import com.bwyap.lwjgl.engine.entity.AnimatedTexture;

/** A particle which supports an animated texture.
 * 
 * @author bwyap */
public abstract class AnimatedParticle extends Particle implements AnimatedTexture
{
	private int texturePosition;
	private float textureChangeSpeed;
	private float currentFrameTime;

	protected AnimatedParticle(float timeCreated, float lifetime, float textureChangeSpeed)
	{
		super(timeCreated, lifetime);
		this.texturePosition = 0;
		this.textureChangeSpeed = textureChangeSpeed;
		this.currentFrameTime = 0;
	}

	protected AnimatedParticle(AnimatedParticle particle)
	{
		super(particle);
		this.texturePosition = particle.getTexturePosition();
		this.textureChangeSpeed = particle.getTextureChangeSpeed();
		this.currentFrameTime = particle.currentFrameTime;
	}

	/** {@inheritDoc}
	 * <p>
	 * <b>IMPORTANT:</b>
	 * When overriding this method to implement particle physics
	 * <b>ensure you call</b> {@code super.update(timestep)} to animate the particle.
	 * </p>
	 * 
	 * @param timestep */
	@Override
	public void update(float timestep)
	{
		currentFrameTime += timestep;
		if (currentFrameTime >= textureChangeSpeed)
		{
			currentFrameTime = 0;
			texturePosition++;
		}
	}

	@Override
	public int getTexturePosition()
	{ return texturePosition; }

	@Override
	public void setTextureChangeSpeed(float speed)
	{ this.textureChangeSpeed = speed; }

	@Override
	public float getTextureChangeSpeed()
	{ return textureChangeSpeed; }
}
