package com.bwyap.engine.render3d.entity;

import org.joml.Quaternionf;
import org.joml.Vector3f;


/**
 * Contains methods that an entity that can be rotated must implement.
 * The rotation of the entity should be stored as a Quaternion.
 * @author bwyap
 *
 */
public interface RotatableInterface {
	
	/**
	 * Get the rotation of the entity's model.
	 * Rotation is represented as a quaternion:
	 * where x,y,z represent the rotation axis and 
	 * w represents the rotation angle (in radians).
	 * @return
	 */
	public Quaternionf getRotation();
	
	/**
	 * Set the rotation of the object,
	 * where x,y,z represent the rotation axis (not normalised) 
	 * and w represents the rotation amount (in radians).
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void setRotation(float x, float y, float z, float w);
	
	/**
	 * Gets the rotation angle in radians.
	 * @return
	 */
	public float getRotationAmount();
	
	/**
	 * Set the amount of rotation around the rotation axis in radians.
	 * @param w
	 */
	public void setRotationAmount(float w);
	
	/**
	 * Set the rotation axis.
	 * The vector is normalised to ensure there is no distortion.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setRotationAxisNormalize(float x, float y, float z);
	
	/**
	 * Set the rotation axis (without normalisation).
	 * If the provided values do not form a normalised vector,
	 * there may be distortion when the object is rotated.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setRotationAxis(float x, float y, float z);
	
	/**
	 * Get the rotation axis as a vector.
	 * @return
	 */
	public Vector3f getRotationAxis();
	
}
