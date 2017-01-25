package com.bwyap.enginedriver.game.test;

import com.bwyap.lwjgl.engine.particle.AnimatedParticle;

public class TestAnimatedParticle extends AnimatedParticle {

	TestAnimatedParticle(AnimatedParticle particle) {
		super(particle);
	}
	
	
	TestAnimatedParticle(float timeCreated, float lifetime, float textureChangeSpeed) {
		super(timeCreated, lifetime, textureChangeSpeed);
	}
	
	
	@Override
	public void update(float timestep) {
		super.update(timestep);
		movePosition(getVelocity().x() * timestep, getVelocity().y() * timestep, getVelocity().z() * timestep);
	}

}
