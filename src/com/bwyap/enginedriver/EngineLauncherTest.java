package com.bwyap.enginedriver;

import java.io.IOException;

import com.bwyap.enginedriver.window.DriverWindow;
import com.bwyap.lwjgl.engine.resource.LWJGLResourceManager;

public class EngineLauncherTest {
	
	public static void main(String[] args) {
		LWJGLResourceManager.initInstance(LWJGLResourceManager.RESOURCE_JSON);

		try {
			LWJGLResourceManager.instance().loadIndepedentResources();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new DriverWindow().start();
	}

}
