package com.bwyap.lwjgl.engine.render3d.scene;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.render3d.Renderer3D;
import com.bwyap.engine.render3d.SceneLayer;
import com.bwyap.lwjgl.engine.particle.ParticleSystem;
import com.bwyap.lwjgl.engine.render3d.LWJGLRenderer;
import com.bwyap.lwjgl.engine.render3d.shader.ParticleShader;

/** A scene layer which holds particle systems.
 * All systems within this layer should use the
 * same shader.
 * 
 * @author bwyap */
public class ParticleSystemLayer implements SceneLayer<ParticleSystem<?>>
{
	protected List<ParticleSystem<?>> particleSystems;
	protected ParticleShader shader;

	public ParticleSystemLayer() throws Exception
	{
		particleSystems = new ArrayList<ParticleSystem<?>>();
		shader = new ParticleShader();
		shader.init();
	}

	/** Add a particle system to the scene.
	 * 
	 * @param system */
	public boolean addEntity(ParticleSystem<?> system)
	{ return particleSystems.add(system); }

	/** Remove a particle system from the scene.
	 * Returns true if the system was removed.
	 * 
	 * @param  system
	 * @return */
	@Override
	public boolean removeEntity(ParticleSystem<?> system)
	{ return particleSystems.remove(system); }

	/** Update the particle systems in this scene layer.
	 * 
	 * @param timestep */
	@Override
	public void update(float timestep)
	{
		for (ParticleSystem<?> system : particleSystems)
		{ system.update(timestep); }
	}

	@Override
	public void handleInput(InputHandler input, float timestep)
	{}

	/** {@inheritDoc}
	 * <p>
	 * Requires {@code LWJGLRenderer}.
	 * </p> */
	@Override
	public void render(Renderer3D renderer, Matrix4f projectionMatrix, Matrix4f viewMatrix)
	{
		shader.bind();
		shader.setUniform("textureSampler", 0);
		shader.setUniform("projectionMatrix", projectionMatrix);
		for (ParticleSystem<?> system : particleSystems)
		{ system.render((LWJGLRenderer) renderer, shader, viewMatrix); }
		shader.unbind();
	}
}
