package com.bwyap.lwjgl.engine.render3d;

import org.joml.Vector3f;


/**
 * Specifies the colour and reflectance of a material type.
 * @author bwyap
 *
 */
public class Material {
	
	private static final Vector3f DEFAULT_COLOUR = new Vector3f(0.5f, 0.5f, 0.5f);
	
	private Vector3f colour;
	private float reflectance;
	
	
	public Material() {
		this.colour = DEFAULT_COLOUR;
		this.reflectance = 0;
	}
	
	
	public Material(Vector3f colour, float reflectance) {
		this.colour = colour;
		this.reflectance = reflectance;
	}
	

	public Material(float reflectance) {
		this.colour = DEFAULT_COLOUR;
		this.reflectance = reflectance;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public float getReflectance() {
		return reflectance;
	}

	public void setReflectance(float reflectance) {
		this.reflectance = reflectance;
	}
	
}
