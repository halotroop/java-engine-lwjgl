package com.bwyap.engine.render3d.entity;

import org.joml.Vector3f;

/**
 * An entity that has a position, velocity and scale.
 * <p>
 * Implements the following interfaces: 
 * <ul>
 * 	<li>{@code EntityInterface} to provide methods for positioning the entity</li>
 * 	<li>{@code ScalableInterface} to provide methods for scaling the entity</li>
 * 	<li>{@code MovableInterface} to provide methods for getting and setting the entity's velocity</li>
 * </ul>
 * </p>
 * @author bwyap
 *
 */
public class Entity implements EntityInterface, ScalableInterface, MovableInterface {

	private final Vector3f velocity;
	private final Vector3f position;
	private float scaleX, scaleY, scaleZ;
	
	
	public Entity() {
		velocity = new Vector3f(0, 0, 0);
		position = new Vector3f(0, 0, 0);
		scaleX = scaleY = scaleZ = 1;
	}
	
	@Override
	public Vector3f getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
	}
	
	@Override
	public void movePosition(Vector3f displacement) {
		this.position.x += displacement.x;
		this.position.y += displacement.y;
		this.position.z += displacement.z;
	}
	
	@Override
	public void movePosition(float xOffset, float yOffset, float zOffset) {
		this.position.x += xOffset;
		this.position.y += yOffset;
		this.position.z += zOffset;
	}
	
	@Override
	public void setVelocity(float x, float y, float z) {
		this.velocity.x = x;
		this.velocity.y = y;
		this.velocity.z = z;
	}
	
	@Override
	public void setVelocity(Vector3f velocity) {
		this.velocity.x = velocity.x;
		this.velocity.y = velocity.y;
		this.velocity.z = velocity.z;
	}
	
	@Override
	public Vector3f getVelocity() {
		return velocity;
	}
	
	@Override
	public float getScaleX() {
		return scaleX;
	}
	
	@Override
	public float getScaleY() {
		return scaleY;
	}
	
	@Override
	public float getScaleZ() {
		return scaleZ;
	}
	
	@Override
	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		this.scaleZ = scale;
	}
	
	@Override
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	
	@Override
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	
	@Override
	public void setScaleZ(float scaleZ) {
		this.scaleZ = scaleZ;
	}
	
	
}
