package com.bwyap.enginedriver.game.test;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector3f;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.render3d.Scene;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;
import com.bwyap.lwjgl.engine.render3d.light.SpotLight;
import com.bwyap.lwjgl.engine.render3d.light.Light.Attenuation;
import com.bwyap.lwjgl.engine.render3d.scene.LightedObjectLayer;
import com.bwyap.lwjgl.engine.render3d.scene.ParticleSystemLayer;

public class TestScene extends Scene
{
	public TestScene(int width, int height)
	{
		super(width, height);
		LightedObjectLayer element = null;
		ParticleSystemLayer particles = null;
		try
		{
			element = new TestLitSceneObjects();
			element.getLighting().createPointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, 5), 1f).setAttenuation(new Attenuation(1, 0.05f, 0));
			//element.getLighting().createDirectionalLight(new Vector3f(1, 1, 1), new Vector3f(0, moveStep, 0), 0f, new Vector3f(1, 1, 1));
			SpotLight light = element.getLighting().createSpotLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, 10), 3f, new Vector3f(0, 0, -1), 0);
			light.setPosition(new Vector3f(5, 5, 0));
			light.getPointLight().setAttenuation(new Attenuation(1.0f, 0.1f, 0.01f));
			light.setCutOff(4);
			//element.getLighting().createSpotLight(new Vector3f(1, 1, 1), new Vector3f(1, 0, moveStep), 1f, new Vector3f(0, 0, -1), 1rotateStep).getPointLight().setAttenuation(new Attenuation(1.0f, 0.1f, 0.0f));
			TestParticleSystem system = new TestParticleSystem(LWJGLTexture.getTexture("test_particle"));
			TestAnimatedParticleSystem animatedSystem = new TestAnimatedParticleSystem(LWJGLTexture.getTexture("test_animated_particle"));
			particles = new ParticleSystemLayer();
			particles.addEntity(animatedSystem);
			particles.addEntity(system);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		element.getLighting().setAmbient(new Vector3f(0.05f, 0.1f, 0.05f));
		element.getLighting().setSpecularPower(10f);
		this.addLayer(element);
		this.addLayer(particles);
		camera.setPosition(0, 0, 5);
	}

	@Override
	public void handleInput(InputHandler input, float timestep)
	{
		super.handleInput(input, timestep);
		float moveStep = 10;
		float rotateStep = 50;
		if (input.isKeyDown(GLFW_KEY_W))
		{
			if ((input.getKeyMods() & 0x1) == 0x1)
			{
				camera.moveRotation(rotateStep * -timestep, 0, 0);
			}
			else camera.movePosition(0, moveStep * timestep, 0);
		}
		if (input.isKeyDown(GLFW_KEY_S))
		{
			if ((input.getKeyMods() & 0x1) == 0x1)
			{
				camera.moveRotation(rotateStep * timestep, 0, 0);
			}
			else camera.movePosition(0, moveStep * -timestep, 0);
		}
		if (input.isKeyDown(GLFW_KEY_A))
		{
			if ((input.getKeyMods() & 0x1) == 0x1)
			{
				camera.moveRotation(0, rotateStep * -timestep, 0);
			}
			else camera.movePosition(moveStep * -timestep, 0, 0);
		}
		if (input.isKeyDown(GLFW_KEY_D))
		{
			if ((input.getKeyMods() & 0x1) == 0x1)
			{
				camera.moveRotation(0, rotateStep * timestep, 0);
			}
			else camera.movePosition(moveStep * timestep, 0, 0);
		}
		if (input.isKeyDown(GLFW_KEY_E))
		{
			if ((input.getKeyMods() & 0x1) == 0x1)
			{
				camera.moveRotation(0, 0, rotateStep * timestep);
			}
			else camera.movePosition(0, 0, moveStep * timestep);
		}
		if (input.isKeyDown(GLFW_KEY_Q))
		{
			if ((input.getKeyMods() & 0x1) == 0x1)
			{
				camera.moveRotation(0, 0, rotateStep * -timestep);
			}
			else camera.movePosition(0, 0, moveStep * -timestep);
		}
	}
}
