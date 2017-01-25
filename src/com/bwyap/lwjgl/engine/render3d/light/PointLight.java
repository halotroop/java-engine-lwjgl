package com.bwyap.lwjgl.engine.render3d.light;

import org.joml.Vector3f;


public class PointLight extends Light {
	
	
	protected Attenuation attenuation;
	protected Vector3f position;
	
	
	protected PointLight(int pos, Vector3f color, Vector3f position, float intensity) {
		super(pos);
		this.colour = color;
		this.position = position;
		this.intensity = intensity;
		attenuation = new Attenuation(1, 0, 0);
	}
	
	
	protected PointLight(int pos, Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
		this(pos, color, position, intensity);
		this.attenuation = attenuation;
	}
	

	public PointLight(PointLight pointLight) {
		this(pointLight.getAssignedArrayPos(), new Vector3f(pointLight.getColour()), new Vector3f(pointLight.getPosition()), pointLight.getIntensity(), pointLight.getAttenuation());
	}
	

	/**
	 * Get the position of the light
	 * @return
	 */
	public Vector3f getPosition() {
		return position;
	}

	
	/**
	 * Set the position of the light
	 * @param position
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	
	/**
	 * move the position of the light relative to its current location
	 * @param xOffset
	 * @param yOffset
	 * @param zOffset
	 */
	public void movePosition(float xOffset, float yOffset, float zOffset) {
		this.position.x += xOffset;
		this.position.y += yOffset;
		this.position.z += zOffset;
	}
	
	
	public Attenuation getAttenuation() {
		return attenuation;
	}

	
	public void setAttenuation(Attenuation attenuation) {
		this.attenuation = attenuation;
	}
	

	public void setAttenuation(float constant, float linear, float exponent) {
		this.attenuation = new Attenuation(constant, linear, exponent);
	}

}
