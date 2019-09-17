package com.bwyap.engine.render3d.entity;

import org.joml.Vector3f;

/** Contains methods that an entity that can move
 * over time with some velocity must implement.
 * 
 * @author bwyap */
public interface MovableInterface
{
	/** Set the velocity of the entity
	 * 
	 * @param x
	 * @param y
	 * @param z */
	public void setVelocity(float x, float y, float z);

	/** Set the velocity of the entity
	 * 
	 * @param velocity */
	public void setVelocity(Vector3f velocity);

	/** Get the velocity of the entity
	 * 
	 * @return */
	public Vector3f getVelocity();
}
