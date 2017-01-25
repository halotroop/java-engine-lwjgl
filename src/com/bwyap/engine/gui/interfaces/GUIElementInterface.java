package com.bwyap.engine.gui.interfaces;

import org.joml.Vector2f;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.BoundsInterface;


/**
 * <p>
 * Methods that all GUI elements should implement.
 * </p>
 * <p>
 * Uses the {@code JOML} library for Vector operations.
 * </p>
 * @author bwyap
 *
 */
public interface GUIElementInterface extends GUIBoundsInterface {

	
	/**
	 * Update the GUI element
	 * @param timestep
	 */
	public void update(float timestep);
	
	
	/**
	 * Handle how the GUI element should respond to input.
	 * @param window
	 */
	public void handleInput(InputHandler input, GUIBoundsInterface bounds);
	
	
	/**
	 * Update the GUI element's position and bounds according to the bounding window.
	 * This method should be called before the element is rendered.
	 */
	public void updateBounds(BoundsInterface window, Vector2f parent);
	
	
	/**
	 * Set the absolute position of the GUI element.
	 * Co-ordinates are in screen co-ordinates.
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y);
	
	
	/**
	 * Set the absolute position of the GUI element.
	 * Co-ordinates are in screen co-ordinates.
	 * @param x
	 * @param y
	 */
	public void setPosition(Vector2f position);
	

	/**
	 * Set the padding for the panel
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right
	 */
	public void setPadding(float top, float bottom, float left, float right);
	
	
	/**
	 * Set the bounds of this GUI element using a rectangle.
	 * By default a GUI element will use rectangular bounds
	 * unless otherwise specified with the {@code useCircularBounds}
	 * method.
	 * @param x offset from the element's x position
	 * @param y offset from the element's y position
	 * @param width width of the bounds
	 * @param height height of the bounds
	 */
	public void setBounds(float x, float y, float width, float height);
	
	
	/**
	 * Set the bounds of this GUI element using a rectangle.
	 * The offset of the bounds is set to (0, 0).
	 * @param width width of the bounds
	 * @param height height of the bounds
	 */
	public void setBounds(float width, float height);
	
	
	/**
	 * Checks if the given screen co-ordinates are within 
	 * the set bounds of this GUI element. The element could
	 * be using rectangular bounds or circular bounds - which
	 * can be set using the {@code setRectangularBounds} or 
	 * {@code setCircularBounds} method.
	 * @param x
	 * @param y
	 * @return true if the given co-ordinates are within bounds of the GUI element
	 */
	public boolean withinBounds(float x, float y);
	
	
	/**
	 * Set the GUI element to be enabled or disabled.
	 * @param enabled
	 */
	public void setEnabled(boolean enabled);
	
	
	/**
	 * <p>Check if the GUI element is currently enabled.</p>
	 * <p>
	 * A disabled GUI element cannot be activated
	 * (It should have a different texture to indicate its state to the user).
	 * </p>
	 * @return
	 */
	public boolean isEnabled();
	
	
	/**
	 * Set whether the GUI element's position should be absolute.
	 * @param absolute
	 */
	public void setPositionAbsolute(boolean absolute);
	
	
	/**
	 * Check if the GUI element's position is absolute.
	 * Default is false.
	 * @return
	 */
	public boolean isPositionAbsolute();
	
	
	/**
	 * Set whether the GUI element's scale should be absolute.
	 * @param absolute
	 */
	public void setScaleAbsolute(boolean absolute);
	
	
	/**
	 * Check if the GUI element's scale is absolute.
	 * Default is false.
	 * @return
	 */
	public boolean isScaleAbsolute();
	
}
