package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.BoundsInterface;


/**
 * A dummy class that can be extended by GUI elements 
 * that should not be rendered themselves.
 * @author bwyap
 *
 */
public class AbstractGUIElement implements GUIElementInterface {
	
	/**
	 * Unused method in this class.
	 */
	@Override
	public float getWidth() {return 0;}
	
	/**
	 * Unused method in this class.
	 */
	@Override
	public float getHeight() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public float getPaddingTop() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public float getPaddingBottom() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public float getPaddingLeft() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public float getPaddingRight() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public Vector2f getPosition() {return null;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public Vector2f getAbsolutePosition() {return null;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public float getPositionX() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public float getPositionY() {return 0;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public Vector2f getBounds() {return null;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public Vector2f getAbsoluteBounds() {return null;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public Vector2f getOriginalBounds() {return null;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void update(float timestep) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void handleInput(InputHandler input, GUIBoundsInterface bounds) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setPosition(float x, float y) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setPosition(Vector2f position) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setPadding(float top, float bottom, float left, float right) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setBounds(float x, float y, float width, float height) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setBounds(float width, float height) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public boolean withinBounds(float x, float y) {return false;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setEnabled(boolean enabled) {}
	
	/**
	 * Unused method in this class.
	 */
	@Override
	public boolean isEnabled() {return false;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setPositionAbsolute(boolean absolute) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public boolean isPositionAbsolute() {return false;}

	/**
	 * Unused method in this class.
	 */
	@Override
	public void setScaleAbsolute(boolean absolute) {}

	/**
	 * Unused method in this class.
	 */
	@Override
	public boolean isScaleAbsolute() {return false;}
	
}
