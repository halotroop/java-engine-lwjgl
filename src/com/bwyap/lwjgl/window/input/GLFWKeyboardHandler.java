package com.bwyap.lwjgl.window.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

import java.util.HashMap;
import java.util.Map;

import com.bwyap.engine.input.KeyboardHandlerInterface;

/**
 * Handles keyboard input through GLFWKeyCallbacks.
 * @author bwyap
 *
 */
public class GLFWKeyboardHandler implements KeyboardHandlerInterface {
	
	private static final boolean DEBUG = false;

	private boolean[] keys = new boolean[65535];
	private int keyMods = 0x0;
	private int keysDown = 0;
	private char typedChar = 0;
		
	private Map<Integer, Character> acceptedKeys;
	private Map<Integer, Character> acceptedShiftKeys;
	
	
	public GLFWKeyboardHandler() {
		acceptedKeys = new HashMap<Integer, Character>();
		acceptedShiftKeys = new HashMap<Integer, Character>();
		initAcceptedKeys();
	}
	
	
	/**
	 * For GLFWKeyCallback 
	 * @param window
	 * @param key
	 * @param scancode
	 * @param action
	 * @param mods
	 */
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key != GLFW_KEY_UNKNOWN) {
			keys[key] = action != GLFW_RELEASE;
			keyMods = mods;

			if (action != GLFW_RELEASE) typedChar = convertKey(key, mods);
			
			if (action == GLFW_PRESS) keysDown++;
			else if (action == GLFW_RELEASE) keysDown--;
			
			if (DEBUG) if (action == GLFW_PRESS) System.out.println(key + " pressed with mods " + mods);
			if (DEBUG) if (action == GLFW_REPEAT) System.out.println(key + " holding with mods " + mods);
			if (DEBUG) if (action == GLFW_RELEASE) System.out.println(key + " released with mods " + mods);
		}		
	}
	
	
	@Override
	public char consumeLastTypedChar() {
		char c = typedChar;
		typedChar = 0;
		return c;
	}
	
	
	@Override
	public boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
	
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * In the GLFW system, the following bits will be set if
	 * </p>  
	 * <p>
	 * {@code GLFW_MOD_SHIFT: 0x1} <br/>
	 * {@code GLFW_MOD_CONTROL: 0x2} <br/>
	 * {@code GLFW_MOD_ALT:	0x4} <br/>
	 * {@code GLFW_MOD_SUPER: 0x8} (Command on macOS)
	 * </p>
	 */
	@Override
	public int getKeyMods() {
		return keyMods;
	}
	
	
	@Override 
	public int getKeysDown() {
		return keysDown;
	}
	
	
	/**
	 * Converts an accepted GLFW keycode into a character
	 * @param key
	 * @param mods
	 * @return
	 */
	public char convertKey(int key, int mods) {
		if ((mods & 0x1) == 0x1) {
			if (acceptedShiftKeys.get(key) != null) return acceptedShiftKeys.get(key);
		}
		if (acceptedKeys.get(key) == null) return 0;
		else return acceptedKeys.get(key);
	}
	
	
	/**
	 * Initialise the HashMaps holding the mappings from GLFW_KEY codes to characters
	 */
	private void initAcceptedKeys() {
		String lowercase = "abcdefghijklmnopqrstuvwxyz";
		String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		String special1Lower = " ??????'????,-./0123456789?;?=";
		String special1Upper = " ??????\"????<_>?)!@#$%^&*(?:?+";
		
		String special2Lower = "[\\]`";
		String special2Upper = "{|}~";
		
		for (int i = 0; i < 26; i++) {
			acceptedKeys.put(i + GLFW_KEY_A, lowercase.charAt(i));
			acceptedShiftKeys.put(i + GLFW_KEY_A, uppercase.charAt(i));
		}
		
		for (int i = 0; i < special1Lower.length(); i++) {
			acceptedKeys.put(i + GLFW_KEY_SPACE, special1Lower.charAt(i));
			acceptedShiftKeys.put(i + GLFW_KEY_SPACE, special1Upper.charAt(i));
		}

		for (int i = 0; i < special2Lower.length(); i++) {
			acceptedKeys.put(i + GLFW_KEY_LEFT_BRACKET, special2Lower.charAt(i));
			acceptedShiftKeys.put(i + GLFW_KEY_LEFT_BRACKET, special2Upper.charAt(i));
		}
		
		acceptedKeys.put(GLFW_KEY_BACKSPACE, '\b');
		acceptedKeys.put(GLFW_KEY_ENTER, '\n');
		/*
		for (int i : acceptedKeys.keySet()) {
			System.out.println(i + " = " + acceptedKeys.get(i));
		}
		
		for (int i : acceptedShiftKeys.keySet()) {
			System.out.println(i + " = " + acceptedShiftKeys.get(i));
		}
		*/
	}

}
