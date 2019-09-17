package com.bwyap.engine.render3d;

import org.joml.Vector3f;

/** <p>
 * The camera is responsible for holding information which determines the point of view
 * from which the user will see the world and the objects within it.
 * </p>
 * <p>
 * It contains the position, rotation and information about the view frustrum
 * which will be used to create the appropriate matrices to transform the objects
 * in the world.
 * </p>
 * 
 * @author bwyap */
public class Camera
{
	public static final float DEFAULT_FOV = (float) Math.toRadians(60.0f);
	public static final float DEFAULT_Z_NEAR = 0.01f;
	public static final float DEFAULT_Z_FAR = 1000.f;
	private boolean dirty;
	private final Vector3f position;
	private final Vector3f rotation;
	private float zNear, zFar, FOV, aspectRatio;

	public Camera(float zNear, float zFar, float FOV, float aspectRatio)
	{
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		this.zNear = zNear;
		this.zFar = zFar;
		this.FOV = FOV;
		this.aspectRatio = aspectRatio;
		this.dirty = true;
	}

	public Camera(Vector3f position, Vector3f rotation, float zNear, float zFar, float FOV, float aspectRatio)
	{
		this.position = position;
		this.rotation = rotation;
		this.zNear = zNear;
		this.zFar = zFar;
		this.FOV = FOV;
		this.aspectRatio = aspectRatio;
		this.dirty = true;
	}

	public void update(float timestep)
	{}

	/** Check if the camera's view has changed since its last update.
	 * 
	 * @return */
	public boolean isDirty()
	{ return dirty; }

	/** Set the camera's dirty setting. This should be set to true if the camera's view has changed.
	 * 
	 * @param dirty */
	public void setDirty(boolean dirty)
	{ this.dirty = dirty; }

	/** Get the position of the camera
	 * 
	 * @return */
	public Vector3f getPosition()
	{ return position; }

	/** Set the camera's position
	 * 
	 * @param x
	 * @param y
	 * @param z */
	public void setPosition(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
		dirty = true;
	}

	/** Move the camera's position in the direction it is pointed
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param zOffset */
	public void movePosition(float xOffset, float yOffset, float zOffset)
	{
		if (zOffset != 0)
		{
			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * zOffset;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) * zOffset;
		}
		if (xOffset != 0)
		{
			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * xOffset;
			position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * xOffset;
		}
		position.y += yOffset;
		dirty = true;
	}

	/** Get the rotation of the camera
	 * 
	 * @return */
	public Vector3f getRotation()
	{ return rotation; }

	/** Set the rotation of the camera
	 * 
	 * @param x
	 * @param y
	 * @param z */
	public void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
		dirty = true;
	}

	/** Move the rotation of the camera
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param zOffset */
	public void moveRotation(float xOffset, float yOffset, float zOffset)
	{
		rotation.x += xOffset;
		rotation.y += yOffset;
		rotation.z += zOffset;
		dirty = true;
	}

	public float getFOV()
	{ return FOV; }

	public float getZNear()
	{ return zNear; }

	public float getZFar()
	{ return zFar; }

	public float getAspectRatio()
	{ return aspectRatio; }
}
