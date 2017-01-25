package com.bwyap.enginedriver.game.test;

import java.util.Random;

import org.joml.Vector3f;

import com.bwyap.lwjgl.engine.particle.AnimatedParticle;
import com.bwyap.lwjgl.engine.particle.ParticleSystem;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;

public class TestAnimatedParticleSystem extends ParticleSystem<AnimatedParticle> {

	public TestAnimatedParticleSystem(LWJGLTexture texture) throws Exception {
		super(texture, 1000, 10, 0.1f);

		this.setActive(true);
		this.setParticleLifetime(3f, 0);
		this.setBaseVelocity(new Vector3f(0, 3, 0));
		this.setVelocityDirectionVariance(0.1f);
		this.setPosition(new Vector3f(-3f, 0, 3f));
		this.setParticleRadius(0.2f, 0.5f);
		this.setVelocityDirectionVariance(0.5f);
		this.setEmitRadius(0.5f);
	}
	

	@Override
	protected void setMesh() throws Exception {
		this.setMesh("particle");
	}

	
	@Override
	protected AnimatedParticle generateNewParticle(float now) {
		TestAnimatedParticle p = new TestAnimatedParticle(now, (new Random().nextFloat() * (particleLifetimeMax - particleLifetimeMin)) + particleLifetimeMin, 0.03f);
		p.setColour(generateColour());
		p.setRadius(generateRadius());
		p.setPosition(generateStartPosition());
		p.setVelocity(generateStartVelocity());
		return p;
	}

}
