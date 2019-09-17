package com.bwyap.engine.particle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.joml.Quaternionf;
import org.joml.Vector3f;

/** An abstract class that contains the logic behind
 * a particle system. This needs to be extended to
 * implement how the particles in this system should
 * be rendered.
 * 
 * @author     bwyap
 * @param  <T> the particle type */
public abstract class ParticleSystemLogic<T extends Particle>
{
	protected static final Random rand = new Random();
	protected boolean active;
	protected List<T> activeParticles;
	protected List<T> freeParticles;
	protected List<Vector3f> colours;
	protected int maxParticles;
	protected int numberToRelease;
	protected float releaseInterval;
	protected float particleLifetimeMin, particleLifetimeMax;
	protected float particleRadiusMin, particleRadiusMax;
	protected float lastUpdate, currentTime;
	protected float emitRadius;
	protected Vector3f emitBoxBounds;
	protected Vector3f position;
	protected Vector3f baseVelocity;
	protected float velocityDirectionVariance;
	protected float speedVariance;

	public ParticleSystemLogic(int maxParticles, int numberToRelease, float releaseInterval) throws Exception
	{
		this.position = new Vector3f(0, 0, 0);
		this.maxParticles = maxParticles;
		this.numberToRelease = numberToRelease;
		this.releaseInterval = releaseInterval;
		this.baseVelocity = new Vector3f(0, 0, 0);
		colours = new ArrayList<Vector3f>();
		activeParticles = new LinkedList<T>();
		freeParticles = new LinkedList<T>();
	}

	/** Update the particles in this particle system.
	 * 
	 * @param timestep */
	public final void update(float timestep)
	{
		currentTime += timestep;
		// Update existing particles
		for (int i = 0; i < activeParticles.size(); i++)
		{
			updateParticle(activeParticles.get(i), currentTime, timestep);
			// Remove any dead particles
			if (!activeParticles.get(i).isAlive())
			{ freeParticles.add(activeParticles.remove(i)); }
		}
		// Check if it is time to release new particles
		if (active && currentTime - lastUpdate >= releaseInterval)
		{
			lastUpdate = currentTime;
			for (int i = 0; i < numberToRelease; i++)
			{
				// Found a free particle to recycle
				if (freeParticles.size() > 0)
				{
					T p = freeParticles.remove(0);
					p.reset(currentTime,
						(rand.nextFloat() * (particleLifetimeMax - particleLifetimeMin)) + particleLifetimeMin,
						generateStartPosition(),
						generateRadius(),
						generateColour());
					activeParticles.add(p);
				}
				// No free particles - create a new one if maxParticles hasn't been reached
				else if (activeParticles.size() < maxParticles)
				{
					T p = generateNewParticle(currentTime);
					activeParticles.add(p);
				}
			}
		}
	}

	/** Update the state of a particle and removes any particles that have died.
	 * 
	 * @param particle
	 * @param timestep */
	protected void updateParticle(T particle, float currentTime, float timestep)
	{
		particle.update(timestep);
		if (particle.getLifetime() <= currentTime - particle.getTimeCreated())
		{
			// Particle needs to be killed
			particle.kill();
		}
	}

	/** Generate a new particle for the system.
	 * The particle will be assigned with the properties as prescribed by this system.
	 * 
	 * @return */
	protected abstract T generateNewParticle(float now);

	/** Gets a position for a new particle to spawn.
	 * If emit box bounds are set, a random position within the bounding box will be generated.
	 * Otherwise, if an emit radius has been set (a value > 0.0f) a random position within
	 * a bounding sphere with that radius will be generated.
	 * Otherwise, the position of the system will be returned.
	 * 
	 * @return */
	protected Vector3f generateStartPosition()
	{
		if (emitBoxBounds != null)
		{
			//Get a position within the emit box bounds
			//TODO
		}
		else if (emitRadius > 0.0f)
		{
			float xAngle, yAngle, zAngle;
			xAngle = (rand.nextFloat() * 2 * (float) Math.PI);
			yAngle = (rand.nextFloat() * 2 * (float) Math.PI);
			zAngle = (rand.nextFloat() * 2 * (float) Math.PI);
			Vector3f pos = new Vector3f(rand.nextFloat() * (rand.nextInt(3) - 1), rand.nextFloat() * (rand.nextInt(3) - 1), rand.nextFloat() * (rand.nextInt(3) - 1));
			Quaternionf rotation = new Quaternionf();
			rotation.rotateXYZ(xAngle, yAngle, zAngle);
			pos.rotate(rotation);
			pos.normalize().mul(rand.nextFloat() * emitRadius);
			return pos.add(position);
		}
		// No emit bounds set
		return position;
	}

	/** Generate a velocity for a new particle to have.
	 * <p>
	 * If a velocity variance has been set (a value > 0.0f) a
	 * slightly varied baseVelocity vector will be returned.
	 * The direction of the returned vector will depend on
	 * the magnitude of the velocityDirectionVariance.
	 * </p>
	 * <p>
	 * The speed of the particle will depend on the value of
	 * {@code speedVariance}. The value by default is 0 which
	 * will result in no speed variation. A value less than 1.0f
	 * will generate a speed slower than the base velocity,
	 * while values higher than 1.0f will generate a speed
	 * that is between {@code speedVariance/2} to {@code speedVariance}
	 * times faster than the base velocity speed.
	 * </p>
	 * 
	 * @return */
	protected Vector3f generateStartVelocity()
	{
		if (velocityDirectionVariance > 0.0f && baseVelocity.length() > 0)
		{
			float xAngle, yAngle, zAngle;
			xAngle = (rand.nextFloat() * velocityDirectionVariance) - (velocityDirectionVariance / 2);
			yAngle = (rand.nextFloat() * velocityDirectionVariance) - (velocityDirectionVariance / 2);
			zAngle = (rand.nextFloat() * velocityDirectionVariance) - (velocityDirectionVariance / 2);
			Vector3f velocity = new Vector3f(baseVelocity);
			Quaternionf rotation = new Quaternionf();
			rotation.rotateXYZ(xAngle, yAngle, zAngle);
			velocity.rotate(rotation);
			float speed = baseVelocity.length() * (speedVariance > 0 ? ((speedVariance / 2) * rand.nextFloat()) + (speedVariance / 2) : 1);
			return velocity.normalize().mul(speed);
		}
		else return baseVelocity;
	}

	/** Generate a colour for a new particle.
	 * If no colours have been added, a default colour will be returned.
	 * 
	 * @return */
	protected Vector3f generateColour()
	{
		if (colours.size() == 0)
			return new Vector3f(0.8f, 0.8f, 0.8f);
		else
			return colours.get(rand.nextInt(colours.size()));
	}

	/** Generates a radius for a new particle.
	 * 
	 * @return */
	protected float generateRadius()
	{ return (rand.nextFloat() * (particleRadiusMax - particleRadiusMin)) + particleRadiusMin; }

	/** Specify a bounding box within which to emit new particles.
	 * Centered at the particle system's position.
	 * (EmitBoxBounds take precedence)
	 * 
	 * @param boxBounds */
	public void setEmitBoxBounds(float width, float height)
	{
		//TODO
		//
	}

	/** Set the radius of the sphere within which to emit new particles.
	 * Centered at the particle system's position.
	 * (EmitBoxBounds take precedence)
	 * 
	 * @param radius */
	public void setEmitRadius(float radius)
	{ this.emitRadius = radius; }

	/** Check if the particle system is active and releasing particles
	 * 
	 * @return */
	public boolean isActive()
	{ return active; }

	/** Set whether the particle system is active
	 * 
	 * @param active */
	public void setActive(boolean active)
	{
		this.active = active;
		if (!active) currentTime = 0;
	}

	/** Set the position of the particle system
	 * 
	 * @param position */
	public void setPosition(Vector3f position)
	{ this.position = position; }

	/** Move the position of the particle system
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param zOffset */
	public void movePosition(float xOffset, float yOffset, float zOffset)
	{
		this.position.x += xOffset;
		this.position.y += yOffset;
		this.position.z += zOffset;
	}

	/** Get the position of the particle system
	 * 
	 * @return */
	public Vector3f getPosition()
	{ return position; }

	/** Set the base velocity of particles emitted from this system.
	 * 
	 * @param velocity */
	public void setBaseVelocity(Vector3f velocity)
	{ this.baseVelocity = velocity; }

	/** Set the particle radius bounds.
	 * 
	 * @param radius
	 * @param variance */
	public void setParticleRadius(float radius, float variance)
	{
		particleRadiusMin = radius - variance / 2;
		particleRadiusMax = radius + variance / 2;
	}

	/** Set the minimum particle radius.
	 * 
	 * @param min */
	public void setParticleRadiusMin(float min)
	{ particleRadiusMin = min; }

	/** Set the maximum particle radius.
	 * 
	 * @param max */
	public void setParticleRadiusMax(float max)
	{ particleRadiusMax = max; }

	/** Set the particle lifetime bounds.
	 * 
	 * @param lifetime
	 * @param variance */
	public void setParticleLifetime(float lifetime, float variance)
	{
		particleLifetimeMin = lifetime - variance / 2;
		particleLifetimeMax = lifetime + variance / 2;
	}

	/** Set the minimum particle lifetime.
	 * 
	 * @param min */
	public void setParticleLifetimeMin(float min)
	{ particleLifetimeMin = min; }

	/** Set the maximum particle lifetime.
	 * 
	 * @param max */
	public void setParticleLifetimeMax(float max)
	{ particleLifetimeMax = max; }

	/** Set the velocity direction variance when generating a new particle.
	 * 
	 * @param variance */
	public void setVelocityDirectionVariance(float variance)
	{ this.velocityDirectionVariance = variance; }

	/** Set the speed variance when generating a new particle.
	 * 
	 * @param speed */
	public void setSpeedVariance(float speed)
	{ this.speedVariance = speed; }
}
