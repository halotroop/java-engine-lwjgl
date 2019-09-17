package com.bwyap.lwjgl.engine.render3d.light;

import org.joml.Vector3f;

public class DirectionalLight extends Light
{
	protected Vector3f direction;

	protected DirectionalLight(int pos, Vector3f colour, Vector3f direction, float intensity)
	{
		super(pos);
		this.colour = colour;
		this.direction = direction;
		this.intensity = intensity;
	}

	public DirectionalLight(DirectionalLight light)
	{ this(light.getAssignedArrayPos(), new Vector3f(light.getColour()), new Vector3f(light.getDirection()), light.getIntensity()); }

	public Vector3f getDirection()
	{ return direction; }

	/** Set the direction of the light
	 * 
	 * @param direction */
	public void setDirection(Vector3f direction)
	{ this.direction = direction; }
}
