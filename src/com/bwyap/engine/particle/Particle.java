package com.bwyap.engine.particle;

import org.joml.Vector3f;

import com.bwyap.engine.render3d.entity.Entity;

/** Holds information about the state of a single particle.
 * 
 * @author bwyap */
public abstract class Particle extends Entity
{
	protected boolean alive;
	protected Vector3f colour;
	protected float radius;
	protected float timeCreated;
	protected float lifetime;

	protected Particle(float timeCreated, float lifetime)
	{
		this.timeCreated = timeCreated;
		this.lifetime = lifetime;
		this.alive = true;
	}

	public Particle(Particle particle)
	{
		this(particle.timeCreated, particle.lifetime);
		setPosition(particle.getPosition());
		setVelocity(particle.getVelocity());
		this.colour = particle.colour;
		this.radius = particle.radius;
		this.alive = true;
	}

	/** Kill the particle */
	public void kill()
	{ alive = false; }

	/** Check if the particle is alive
	 * 
	 * @return */
	public boolean isAlive()
	{ return alive; }

	/** Update the state of the particle.
	 * Any physics that should be applied to the particle should be implemented here.
	 * 
	 * @param timestep */
	public abstract void update(float timestep);

	/** Get the texture position of the particle, which should always be 0.
	 * 
	 * @return */
	@Deprecated
	public int getTexturePosition()
	{ return 0; }

	/** Set the colour of the particle
	 * 
	 * @param colour */
	public void setColour(Vector3f colour)
	{ this.colour = colour; }

	/** Get the colour of the particle
	 * 
	 * @return */
	public Vector3f getColour()
	{ return colour; }

	/** Set the radius of the particle
	 * 
	 * @param radius */
	public void setRadius(float radius)
	{
		this.radius = radius;
		this.setScale(radius);
	}

	/** Get the radius of the particle
	 * 
	 * @return */
	public float getRadius()
	{ return radius; }

	/** Get the time at which the particle was created
	 * 
	 * @return */
	public float getTimeCreated()
	{ return timeCreated; }

	/** Get the lifetime of the particle
	 * 
	 * @return */
	public float getLifetime()
	{ return lifetime; }

	/** Reset the particle to be reused.
	 * Assigns a new time of creation and lifetime to the particle.
	 * 
	 * @param timeCreated
	 * @param lifetime */
	public void reset(float timeCreated, float lifetime, Vector3f position, float radius, Vector3f colour)
	{
		this.timeCreated = timeCreated;
		this.lifetime = lifetime;
		this.alive = true;
		this.setPosition(position);
		this.setRadius(radius);
		this.setColour(colour);
	}
}
