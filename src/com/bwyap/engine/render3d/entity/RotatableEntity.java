package com.bwyap.engine.render3d.entity;

import org.joml.Quaternionf;
import org.joml.Vector3f;

/** A movable and scalable entity which has the ability to be rotated.
 * Rotation is provided through {@code RotatableInterface}
 * (rotation is stored as a Quaternion).
 * 
 * @author bwyap */
public class RotatableEntity extends Entity implements RotatableInterface
{
	private final Quaternionf rotation;

	public RotatableEntity()
	{ rotation = new Quaternionf(0, 0, 0, 1); }

	@Override
	public Quaternionf getRotation()
	{ return rotation; }

	@Override
	public void setRotation(float x, float y, float z, float w)
	{
		this.rotation.x = x * (float) Math.sin(w / 2);
		this.rotation.y = y * (float) Math.sin(w / 2);
		this.rotation.z = z * (float) Math.sin(w / 2);
		this.rotation.w = (float) Math.cos(w / 2);
	}

	@Override
	public float getRotationAmount()
	{ return 2 * (float) Math.acos(rotation.w); }

	@Override
	public void setRotationAmount(float w)
	{
		Vector3f rotationAxis = getRotationAxis();
		setRotation(rotationAxis.x, rotationAxis.y, rotationAxis.z, w % (float) (2 * Math.PI));
	}

	@Override
	public void setRotationAxisNormalize(float x, float y, float z)
	{
		Vector3f axis = new Vector3f(x, y, z);
		axis.normalize();
		setRotation(axis.x, axis.y, axis.z, getRotationAmount());
	}

	@Override
	public void setRotationAxis(float x, float y, float z)
	{ setRotation(x, y, z, getRotationAmount()); }

	@Override
	public Vector3f getRotationAxis()
	{ return new Vector3f(
		rotation.x / (float) Math.sin(getRotationAmount() / 2),
		rotation.y / (float) Math.sin(getRotationAmount() / 2),
		rotation.z / (float) Math.sin(getRotationAmount() / 2)); }
}
