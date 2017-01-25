package com.bwyap.enginedriver;

import com.bwyap.enginedriver.resource.Resource;
import com.bwyap.enginedriver.window.DriverWindow;

public class EngineLauncherTest {
	
	public static void main(String[] args) {
		Resource.load();
		new DriverWindow().start();
		
	}

}
