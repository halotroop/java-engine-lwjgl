package com.bwyap.enginedriver.game.test;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import org.joml.Vector3f;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.render3d.entity.RotatableEntity;
import com.bwyap.lwjgl.engine.entity.AnimatedRenderableObject;
import com.bwyap.lwjgl.engine.entity.RenderableEntity;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;
import com.bwyap.lwjgl.engine.render3d.Material;
import com.bwyap.lwjgl.engine.render3d.scene.LightedObjectLayer;

public class TestLitSceneObjects extends LightedObjectLayer {
	
	
	public TestLitSceneObjects() throws Exception {
		super();
		int sqrt;
		
		sqrt = 3;
		for(int i = 0; i < sqrt*sqrt; i++) {
			RenderableEntity cube = new RenderableEntity("cube");
			cube.setTexture(LWJGLTexture.getTexture("test"));
			cube.setMaterial(new Material(new Vector3f(0.7f, 1.0f, 0.7f), 0.1f));
			cube.setPosition(new Vector3f((i % sqrt) * 3 , ((i / sqrt) * -3) - 3, i/5));
			this.addEntity(cube);
		}
		
		AnimatedRenderableObject acube = new AnimatedRenderableObject("cube");
		LWJGLTexture.getTexture("test_animated_particle").setAlpha(true);
		acube.setTexture(LWJGLTexture.getTexture("test_animated_particle"));
		acube.setMaterial(new Material(new Vector3f(0.7f, 1.0f, 0.7f), 0.1f));
		acube.setPosition(new Vector3f(-3, 0, 0));
		this.addEntity(acube);		
		
		sqrt = 16;
		for(int i = 0; i < sqrt*sqrt; i++) {
			RenderableEntity icube = new RenderableEntity("icube");
			icube.setTexture(LWJGLTexture.getTexture("test"));
			icube.setMaterial(new Material(new Vector3f(0.7f, 1.0f, 0.7f), 0.1f));
			icube.setPosition(new Vector3f(i % sqrt, i / sqrt, 0));
			icube.setScale(0.4f);
			this.addEntity(icube);
		}
	}
	
	
	float cutOff = 0;
	
	@Override
	public void update(float timestep) {
		super.update(timestep);
		
		for (RotatableEntity cube : meshMap.get("cube")) {
			cube.setRotationAxisNormalize(0, 1, 1);
			cube.setRotationAmount(cube.getRotationAmount() + 3 * timestep);
		}
		
		//cube.setRotationAmount(1.5f);
		cutOff += 0.01f;
		lighting.getSpotLights()[0].setCutOff(cutOff);
		
		for (RotatableEntity cube : instancedMeshMap.get("icube")) {
			cube.setRotationAxisNormalize(0, 1, 1);
			cube.setRotationAmount(cube.getRotationAmount() + 1 * timestep);
		}

	}
	
	
	@Override
	public void handleInput(InputHandler input, float timestep) {
		float speed = 8;
		
		if (input.isKeyDown(GLFW_KEY_UP) && input.getKeyMods() != 0x1) {
			lighting.getSpotLights()[0].movePosition(0, speed * timestep, 0);
		}

		if (input.isKeyDown(GLFW_KEY_DOWN) && input.getKeyMods() != 0x1) {
			lighting.getSpotLights()[0].movePosition(0, speed * -timestep, 0);
		}
		
		if (input.isKeyDown(GLFW_KEY_LEFT)) {
			lighting.getSpotLights()[0].movePosition(speed * -timestep, 0, 0);
		}
		
		if (input.isKeyDown(GLFW_KEY_RIGHT)) {
			lighting.getSpotLights()[0].movePosition(speed * timestep, 0, 0);
		}

		if (input.isKeyDown(GLFW_KEY_DOWN) && input.getKeyMods() == 0x1) {
			lighting.getSpotLights()[0].movePosition(0, 0, speed * timestep);
		}

		if (input.isKeyDown(GLFW_KEY_UP) && input.getKeyMods() == 0x1) {
			lighting.getSpotLights()[0].movePosition(0, 0, speed * -timestep);
		}
	}

}
