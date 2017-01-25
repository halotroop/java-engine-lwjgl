package com.bwyap.lwjgl.engine.render3d.light;

import org.joml.Vector3f;


/**
 * Represents a light with a position, colour and intensity. 
 * @author bwyap
 *
 */
public abstract class Light {
	
	private int pos;
	
	protected Vector3f colour;
	protected float intensity;

	
	protected Light(int pos) {
		this.pos = pos;
	}
	
	
	protected Light() {
		this(0);
	}
	
	
	/**
	 * Get array position of the light as assigned by SceneLighting (default value is 0).
	 * @return
	 */
	public int getAssignedArrayPos() {
		return pos;
	}
	
	
	/**
	 * Get the colour of the light
	 * @return
	 */
	public Vector3f getColour() {
		return colour;
	}

	
	/**
	 * Set the colour of the light
	 * @param colour
	 */
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	
	/**
	 * Get the intensity of the light
	 * @return
	 */
	public float getIntensity() {
		return intensity;
	}

	
	/**
	 * Set the intensity of the light
	 * @return
	 */
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
	
	public static class Attenuation {

		private float constant;
		private float linear;
		private float exponent;

		public Attenuation(float constant, float linear, float exponent) {
			this.constant = constant;
			this.linear = linear;
			this.exponent = exponent;
		}

		public float getConstant() {
			return constant;
		}

		public void setConstant(float constant) {
			this.constant = constant;
		}

		public float getLinear() {
			return linear;
		}

		public void setLinear(float linear) {
			this.linear = linear;
		}

		public float getExponent() {
			return exponent;
		}

		public void setExponent(float exponent) {
			this.exponent = exponent;
		}
	}

	
}
