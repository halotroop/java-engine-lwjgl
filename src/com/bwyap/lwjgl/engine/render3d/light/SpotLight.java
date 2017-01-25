package com.bwyap.lwjgl.engine.render3d.light;

import org.joml.Vector3f;


public class SpotLight extends Light {
	
	
	private PointLight pointLight;
	private Vector3f coneDirection;
	private float cutOff;
	
	
	protected SpotLight(int pos, PointLight pointLight, Vector3f coneDirection, float cutOffAngle) {
		super(pos);
		this.pointLight = pointLight;
		this.coneDirection = coneDirection;
		this.cutOff = (float)Math.cos(Math.toRadians(cutOffAngle));
	}
	

	public SpotLight(SpotLight spotLight) {
		this(spotLight.getAssignedArrayPos(), new PointLight(spotLight.getPointLight()),
				new Vector3f(spotLight.getConeDirection()),
				spotLight.getCutOff());
	}
	
	
	public PointLight getPointLight() {
		return pointLight;
	}
	
	
	public void setPointLight(PointLight pointLight) {
		this.pointLight = pointLight;
	}
	
	
	public Vector3f getConeDirection() {
		return coneDirection;
	}
	
	
	public void setConeDirection(Vector3f coneDirection) {
		this.coneDirection = coneDirection;
	}
	
	
	public float getCutOff() {
		return cutOff;
	}
	
	
	public void setCutOff(float cutOff) {
		this.cutOff = cutOff;
	}
	
	
	@Override
	public Vector3f getColour() {
		return pointLight.colour;
	}

	@Override
	public void setColour(Vector3f colour) {
		pointLight.colour = colour;
	}

	
	public Vector3f getPosition() {
		return pointLight.position;
	}

	
	public void setPosition(Vector3f position) {
		pointLight.position = position;
	}
	
	
	public void movePosition(float xOffset, float yOffset, float zOffset) {
		pointLight.position.x += xOffset;
		pointLight.position.y += yOffset;
		pointLight.position.z += zOffset;
	}
	
	
	@Override
	public float getIntensity() {
		return pointLight.intensity;
	}

	
	@Override
	public void setIntensity(float intensity) {
		pointLight.intensity = intensity;
	}
	
}
