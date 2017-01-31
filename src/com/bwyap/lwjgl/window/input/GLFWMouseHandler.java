package com.bwyap.lwjgl.window.input;

import static org.lwjgl.glfw.GLFW.*;

import com.bwyap.engine.input.MouseHandlerInterface;

/**
 * Handles mouse input through GLFW Callbacks.
 * @author bwyap
 *
 */
public class GLFWMouseHandler implements MouseHandlerInterface {
	
	private static final boolean DEBUG = false;
	
	private boolean[] buttons = new boolean[1024];
	private double mouseX, mouseY;
	private double scrollX, scrollY;
	private double mouseScrollTime;
	private boolean mouseEntered;
	
	
	/**
	 * For GLFWMouseButtonCallback
	 * @param window
	 * @param button
	 * @param action
	 * @param mods
	 */
	public void invokeMouseButtonCallback(long window, int button, int action, int mods) {
		buttons[button] = action != GLFW_RELEASE;
		
		if (DEBUG) if (action == GLFW_PRESS) System.out.println("Mouse " + button + " pressed.");
		if (DEBUG) if (action == GLFW_REPEAT) System.out.println("Mouse " + button + " holding.");
		if (DEBUG) if (action == GLFW_RELEASE) System.out.println("Mouse " + button + " released.");
	}
	
	
	/**
	 * For GLFWScrollCallback
	 * @param window
	 * @param xoffset
	 * @param yoffset
	 */
	public void invokeScrollCallback(long window, double xoffset, double yoffset) {
		mouseScrollTime = glfwGetTime();
		scrollX = xoffset;
		scrollY = yoffset;
		
		if (DEBUG) System.out.println("Mouse scrolled: " + xoffset + ", " + yoffset);
	}
	

	/**
	 * For GLFWCursorPosCallback
	 * @param window
	 * @param xpos
	 * @param ypos
	 */
	public void invokeCursorPosCallback(long window, double xpos, double ypos) {
		mouseX = xpos;
		mouseY = ypos;
		
		if (DEBUG) System.out.println("Mouse moved to " + xpos + ", " + ypos);
	}
	

	/**
	 * For GLFWCursorEnterCallback
	 * @param window
	 * @param entered
	 */
	public void invokeCursorEnterCallback(long window, boolean entered) {
		mouseEntered = entered;
		
		if (DEBUG) System.out.println("Mouse entered: " + entered);
	}
	
	
	@Override
	public boolean isMouseDown(int mouseButton) {
		return buttons[mouseButton];
	}
	
	@Override
	public boolean isMouseDown() {
		for (boolean down : buttons) {
			if (down) return true;
		}
		return false;
	}

	@Override
	public double getMouseX() {
		return mouseX;
	}

	@Override
	public double getMouseY() {
		return mouseY;
	}

	@Override
	public boolean isMouseEntered() {
		return mouseEntered;
	}

	@Override
	public double getMouseScrollX() {
		return scrollX;
	}
	
	@Override
	public double consumeMouseScrollX() {
		double x = scrollX;
		scrollX = 0;
		return x;
	}

	@Override
	public double getMouseScrollY() {
		return scrollY;
	}
	
	@Override
	public double consumeMouseScrollY() {
		double y = scrollY;
		scrollY = 0;
		return y;
	}

	@Override
	public double getLastMouseScrollTime() {
		return mouseScrollTime;
	}
}
