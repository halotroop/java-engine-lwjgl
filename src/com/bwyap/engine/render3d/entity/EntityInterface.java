package com.bwyap.engine.render3d.entity;

import org.joml.Vector3f;

/** Contains methods that an entity with
 * a position in a 3D world must implement.
 * 
 * @author bwyap */
public interface EntityInterface
{
	/** Get the position at which the entity is located in the world.
	 * 
	 * @return */
	public Vector3f getPosition();

	/** Set the position of the entity in the world.
	 * 
	 * @param x
	 * @param y
	 * @param z */
	public void setPosition(float x, float y, float z);

	/** Set the position of the entity in the world.
	 * 
	 * @param position */
	public void setPosition(Vector3f position);

	/** Move the position of the entity by the given displacement
	 * 
	 * @param displacement */
	public void movePosition(Vector3f displacement);

	/** Move the position of the particle
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param zOffset */
	public void movePosition(float xOffset, float yOffset, float zOffset);
}
