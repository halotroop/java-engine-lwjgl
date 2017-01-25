package com.bwyap.enginedriver.game.test;

import com.bwyap.engine.particle.Particle;

public class TestParticle extends Particle {

	
	TestParticle(float timeCreated, float lifetime) {
		super(timeCreated, lifetime);
	}

	
	TestParticle(TestParticle particle) {
		super(particle);
	}
	
	
	@Override
	public void update(float timestep) {
		movePosition(getVelocity().x() * timestep, getVelocity().y() * timestep, getVelocity().z() * timestep);
		
	}

}
