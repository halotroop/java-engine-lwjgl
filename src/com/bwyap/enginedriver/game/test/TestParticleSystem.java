package com.bwyap.enginedriver.game.test;

import org.joml.Vector3f;

import com.bwyap.lwjgl.engine.particle.ParticleSystem;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;

public class TestParticleSystem extends ParticleSystem<TestParticle>
{
	public TestParticleSystem(LWJGLTexture texture) throws Exception
	{
		super(texture, 2000, 10, 0.01f);
		this.setActive(true);
		this.setParticleLifetime(2f, 0);
		this.setBaseVelocity(new Vector3f(0, 1f, 0));
		//this.setVelocityVariance(2 * (float)Math.PI);
		this.setVelocityDirectionVariance(10f);
		this.setSpeedVariance(10f);
		//this.setPosition(new Vector3f(3f, 0, 0));
		this.setPosition(new Vector3f(0, 0, 3));
		this.setParticleRadius(0.1f, 0.1f);
		this.setEmitRadius(1f);
	}

	@Override
	protected void setMesh() throws Exception
	{ this.setMesh("particle"); }

	@Override
	protected TestParticle generateNewParticle(float now)
	{
		TestParticle p = new TestParticle(now, (rand.nextFloat() * (particleLifetimeMax - particleLifetimeMin)) + particleLifetimeMin);
		p.setColour(generateColour());
		p.setRadius(generateRadius());
		p.setPosition(generateStartPosition());
		p.setVelocity(generateStartVelocity());
		return p;
	}
}
