package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.window.BoundsInterface;


/**
 * <p>
 * A template that provides core methods for an arbitrary GUI element
 * that can be selected, enabled, scaled, and has a position.
 * </p>
 * <p>
 * Uses the {@code JOML} library for Vector operations.
 * </p>
 * @author bwyap
 *
 */
public abstract class GUIElement extends AbstractGUIElement implements GUIElementInterface {
	
	protected final Vector2f absolutePosition, position;
	protected boolean positionAbsolute;
	protected boolean scaleAbsolute;
	
	protected float scaleX;
	protected float scaleY;

	private float paddingTop = 2.0f;
	private float paddingBottom = 2.0f;
	private float paddingLeft = 2.0f;
	private float paddingRight = 2.0f;

	protected final Vector2f boundsOffset, absoluteBoundsOffset;
	protected final Vector2f bounds, absoluteBounds, originalBounds;
	
	
	/**
	 * Create a new GUIElement at the specified co-ordinates
	 * with bounds given by the width and height.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GUIElement(float x, float y, float width, float height) {
		this.absolutePosition = new Vector2f(x, y);
		this.position = new Vector2f(x, y);
		this.enabled = true;
		this.originalBounds = new Vector2f(width, height);
		this.absoluteBounds = new Vector2f(width, height);
		this.bounds = new Vector2f(width, height);
		
		// default bounds have an offset of 0
		this.boundsOffset = new Vector2f(0, 0);	
		this.absoluteBoundsOffset = new Vector2f(0, 0);	
		
		// by default GUI elements are not absolutely positioned
		// that is, they should be scaled according to the screen AR
		this.positionAbsolute = false;
		this.scaleAbsolute = false;
		this.scaleX = 1.0f;
		this.scaleY = 1.0f;
	}

	
	/**
	 * Create a new GUIElement at the specified co-ordinates.
	 * Bounds initially set to 0.
	 * @param x
	 * @param y
	 */
	public GUIElement(float x, float y) {
		this(x, y, 0, 0);
	}
	

	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent) {
		if (!scaleAbsolute) {			
			scaleX = (float)window.getBounds().x/window.getOriginalBounds().x;
			scaleY = (float)window.getBounds().y/window.getOriginalBounds().y;
			boundsOffset.x = absoluteBoundsOffset.x * scaleX;
			boundsOffset.y = absoluteBoundsOffset.y * scaleY;
			bounds.x = absoluteBounds.x * scaleX;
			bounds.y = absoluteBounds.y * scaleY;
		}
		
		if (!positionAbsolute) {
			position.x = (absolutePosition.x * (float)window.getBounds().x/window.getOriginalBounds().x) + parent.x;
			position.y = (absolutePosition.y * (float)window.getBounds().y/window.getOriginalBounds().y) + parent.y;
		}
		else {
			position.x = (absolutePosition.x) + parent.x;
			position.y = (absolutePosition.y) + parent.y;
		}
	}

	
	@Override
	public final void setPosition(float x, float y) {
		absolutePosition.x = x;
		absolutePosition.y = y;
	}
	
	
	@Override
	public final void setPosition(Vector2f position) {
		setPosition(position.x, position.y);
	}
	

	@Override
	public final Vector2f getPosition() {
		return position;
	}
	

	@Override
	public final Vector2f getAbsolutePosition() {
		return absolutePosition;
	}


	@Override
	public final float getPositionX() {
		return position.x;
	}


	@Override
	public final float getPositionY() {
		return position.y;
	}
	
	
	@Override
	public void setBounds(float xOffset, float yOffset, float width, float height) {
		absoluteBoundsOffset.x = xOffset;
		absoluteBoundsOffset.y = yOffset;
		absoluteBounds.x = width;
		absoluteBounds.y = height;
	}
	
	
	@Override
	public void setBounds(float width, float height) {
		setBounds(0, 0, width, height);
	}
	
	
	@Override
	public Vector2f getBounds() {
		return bounds;
	}
	
	
	@Override
	public float getWidth() {
		return bounds.x;
	}
	
	
	@Override
	public float getHeight() {
		return bounds.y;
	}
	
	
	@Override
	public Vector2f getAbsoluteBounds() {
		return absoluteBounds;
	}
	
	
	@Override
	public Vector2f getOriginalBounds() {
		return originalBounds;
	}
	

	@Override
	public void setPadding(float top, float bottom, float left, float right) {
		paddingTop = top;
		paddingBottom = bottom;
		paddingLeft = left;
		paddingRight = right;
	}
	
	
	@Override
	public float getPaddingTop() {	
		return paddingTop; 		
	}
	
	
	@Override
	public float getPaddingBottom() {	
		return paddingBottom; 	
	}
	
	
	@Override
	public float getPaddingLeft() {	
		return paddingLeft; 	
	}
	
	
	@Override
	public float getPaddingRight() {	
		return paddingRight; 	
	}
	
	
	@Override
	public boolean withinBounds(float x, float y) {
		if((x >= (position.x + boundsOffset.x) && x < (position.x + boundsOffset.x + bounds.x)) && 
				(y >= (position.y + boundsOffset.y) && y < (position.y + boundsOffset.y + bounds.y))) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public void setPositionAbsolute(boolean absolute) {
		this.positionAbsolute = absolute;
	}
	
	
	@Override
	public boolean isPositionAbsolute() {
		return positionAbsolute;
	}
	
	
	@Override
	public void setScaleAbsolute(boolean absolute) {
		this.scaleAbsolute = absolute;
	}
	
	
	@Override
	public boolean isScaleAbsolute() {
		return scaleAbsolute;
	}
	
}
