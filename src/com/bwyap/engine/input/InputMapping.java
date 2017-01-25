package com.bwyap.engine.input;

import java.util.HashMap;
import java.util.Map;

public final class InputMapping {
	
	
	private final Map<String, Integer> inputMapping;
	
	
	/*
	 * Keys that the mapping can hold
	 */
	public static final String MOUSE_LEFT = "mouse_left";
	public static final String MOUSE_RIGHT = "mouse_right";
	
	
	public InputMapping() {
		inputMapping = new HashMap<String, Integer>();
	}
	
	
	public int getBinding(String name) {
		return inputMapping.get(name);
	}
	
	
	public void setBinding(String bindingName, int value) {
		inputMapping.put(bindingName, value);
	}
	
}
