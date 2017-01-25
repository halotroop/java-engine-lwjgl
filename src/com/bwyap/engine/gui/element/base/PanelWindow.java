package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.element.EllipticalButton;
import com.bwyap.engine.gui.element.properties.Fade;
import com.bwyap.engine.gui.element.properties.TextComponent;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.IFade;
import com.bwyap.engine.gui.interfaces.ITextDisplay;
import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.input.InputMapping;
import com.bwyap.enginedriver.resource.Resource;


/**
 * A extension of the panel class that can be used
 * as an in-screen window, which can be moved, resized
 * and closed. 
 * @author bwyap
 *
 */
public abstract class PanelWindow extends Panel implements IFade, ITextDisplay {

	private float MIN_WIDTH = 25;
	private float MIN_HEIGHT = 25;
	private float MAX_WIDTH;
	private float MAX_HEIGHT;
	private float RESIZE_AREA = 6;
	
	// Window properties
	private boolean resizable;
	private boolean movable;
	private boolean canMove;	// disabled when the mouse button is pressed
	private boolean visible;
	private boolean keepWithinParent;
	
	private boolean fadeOnClose;	
	private final Fade fade;
	
	private EllipticalButton closeButton;
	private float closeButtonRadius = 6, closeButtonPadding = 6;
	
	private boolean moving = false;
	private boolean resizing = false;
	
	private final TextComponent title;
	
	
	/**
	 * Create a panel window with the specified dimensions.
	 * The panel is not resizable but is movable by default.
	 * The maxWidth and maxHeight are set to its width and height.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public PanelWindow(String title, float x, float y, float width, float height) {
		this(title, x, y, width, height, 100000, 100000);
		setResizable(false);
	}
	
	
	/**
	 * Create a panel window with the specified dimensions and specified maxWidth and maxHeight.
	 * This panel is resizable and movable by default.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param maxWidth
	 * @param maxHeight
	 */
	public PanelWindow(String name, float x, float y, float width, float height, float maxWidth, float maxHeight) {
		super(x, y, width, height);
		
		fade = new Fade();
		fadeOnClose = true;
		keepWithinParent = true;
		movable = true;
		resizable = true;
		title = new TextComponent(name);
		title.setFontName(Resource.defaultFont);
		title.setAlignment(ETextAlignment.TOP_CENTER);
		title.setOffset(0, 3);
		title.setTextSize(15.0f);
		
		MAX_WIDTH = maxWidth;
		MAX_HEIGHT = maxHeight;
		
		initButtons();
		initElements();
	}
	
	
	/**
	 * Initialise the close button
	 */
	private void initButtons() {
		closeButton = new EllipticalButton(closeButtonPadding, closeButtonPadding, closeButtonRadius, closeButtonRadius) {
			@Override
			public void onMouseClicked(float x, float y, int mouseButton) {
				if (!resizing) {
					if (fadeOnClose) {
						fade.setFading(true);
						fade.setFade(1.0f);
					}
					else close();
				}
			}
			
			@Override
			public void onMouseDown(float x, float y, int mouseButton) {
				// Disable moving the window when the mouse button is down over the close button
				// re-enabled when the mouse is released (see onHandleInput method)
				canMove = moving || false;
			}
		};
		
		closeButton.setScaleAbsolute(true);
		closeButton.setPositionAbsolute(true);
		closeButton.colourProperties().setColour(1.0f, 0.0f, 0.0f, 1.0f);
		closeButton.colourProperties().setMouseoverColour(1.0f, 0.5f, 0.5f, 1.0f);
		closeButton.colourProperties().setMouseDownColour(1.0f, 0.2f, 0.2f, 1.0f);
		addElement(closeButton);
	}
	
	
	@Override
	public TextComponent getTextComponent() {
		return title;
	}
	
	
	@Override
	public Fade getFade() {
		return fade;
	}
	
	
	/**
	 * Initialise any GUI components that should be displayed in the window
	 */
	protected abstract void initElements();
	
	
	@Override
	public void onUpdate(float timestep) {
		if (fade.isFading()) {
			fade.decreaseFade(timestep * 5.0f);
			if (fade.getFade() == 0) {
				fade.setFading(false);
				close();
			}
		}
	}
	
	
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		super.onHandleInput(input, bounds);
		
		// Resize window
		if (resizable) resizeWindow(input, bounds);
		
		// Move window
		if (movable && canMove && !resizing) moveWindow(input, bounds);
		
		if (!input.isMouseDown()) canMove = true;
	}
	

	private Vector2f mouseDisplacement;
	private Vector2f original;
	
	/**
	 * Resize the window according to the mouse movement
	 * @param input
	 * @param bounds
	 */
	private void resizeWindow(InputHandler input, GUIBoundsInterface bounds) {
		// Check if the mouse is within the resize area
		if ((input.getMouseX() > getPositionX() + (getWidth() - RESIZE_AREA) &&
				input.getMouseX() < getPositionX() + getWidth() &&
				input.getMouseY() > getPositionY() + (getHeight() - RESIZE_AREA) &&
				input.getMouseY() < getPositionY() + getHeight())
				|| resizing) {
			// Check if the mouse is down
			if (input.isMouseDown(Resource.inputMapping.getBinding(InputMapping.MOUSE_LEFT))) {
				mouseOver = true;
				if (!resizing) {
					// Set resize origin
					resizing = true;
					mouseDisplacement = new Vector2f((float)input.getMouseX(), (float)input.getMouseY());
					original = new Vector2f(getWidth(), getHeight());
				}
				
				// Calculate displacement
				float newWidth = ((float)input.getMouseX() - mouseDisplacement.x + original.x)/scaleX;
				float newHeight = ((float)input.getMouseY() - mouseDisplacement.y + original.y)/scaleY;
				
				// Cut resizing if the panel must stay within its parent
				if (keepWithinParent) {
					if (newWidth + getAbsolutePosition().x > bounds.getBounds().x/scaleX) newWidth = bounds.getBounds().x/scaleX - getAbsolutePosition().x;
					if (newHeight + getAbsolutePosition().y > bounds.getBounds().y/scaleY) newHeight = bounds.getBounds().y/scaleY - getAbsolutePosition().y;
				}
				
				// Resize within minimum and maximum size
				setBounds(newWidth < MIN_WIDTH ? MIN_WIDTH : (newWidth > MAX_WIDTH ? MAX_WIDTH : newWidth), 
						newHeight < MIN_HEIGHT ? MIN_HEIGHT : (newHeight > MAX_HEIGHT ? MAX_HEIGHT : newHeight));
			}
			else resizing = false;
		}
	}
	
	
	/**
	 * Move the window according to the input handler
	 * @param input
	 * @param bounds
	 */
	protected void moveWindow(InputHandler input, GUIBoundsInterface bounds) {
		// Check if the window should be moved
		if (withinBounds((float)input.getMouseX(), (float)input.getMouseY()) || moving) {
			// Check if the mouse is down
			if (input.isMouseDown(Resource.inputMapping.getBinding(InputMapping.MOUSE_LEFT))) {
				mouseOver = true;
				if (!moving) {
					// Set movement origin
					moving = true;
					mouseDisplacement = new Vector2f((float)input.getMouseX(), (float)input.getMouseY()).sub(new Vector2f(getPosition()));
				}
				
				// Calculate displacement
				float xOffset = ((float)input.getMouseX() - mouseDisplacement.x - bounds.getPositionX())/scaleX;
				float yOffset = ((float)input.getMouseY() - mouseDisplacement.y - bounds.getPositionY())/scaleY;
				
				// Cut movement if the panel must stay within its parent
				if (keepWithinParent) {
					if (getWidth()/scaleX + xOffset > bounds.getBounds().x/scaleX) xOffset = (bounds.getBounds().x - getWidth())/scaleX;
					else if (xOffset < 0) xOffset = 0;
					if (getHeight()/scaleY + yOffset > bounds.getBounds().y/scaleY) yOffset = (bounds.getBounds().y - getHeight())/scaleY;
					else if (yOffset < 0) yOffset = 0;
				}
				
				// Set the position
				setPosition(xOffset, yOffset);
			}
			else moving = false;
		}
	}
	

	/**
	 * Close the window.
	 * Override this method to implement custom functionality 
	 * (ensure you call {@code super.close()}).
	 */
	public void close() {
		setVisible(false);
	}
	
	
	/**
	 * Set whether the window should fade out on close
	 * @param fadeOnClose
	 */
	public void setFadeOnClose(boolean fadeOnClose) {
		this.fadeOnClose = fadeOnClose;
	}

	
	/**
	 * Checks if the window will fade out on close
	 * @return
	 */
	public boolean fadeOnClose() {
		return fadeOnClose;
	}
	
	
	/**
	 * Check if the panel window is visible
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	
	
	/**
	 * Set whether the panel window is visible
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	/**
	 * Check if the panel window is movable
	 * @return
	 */
	public boolean isMovable() {
		return movable;
	}
	
	
	/**
	 * Set whether the panel window is movable
	 * @param movable
	 */
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	

	/**
	 * Check if the panel window is resizable
	 * @return
	 */
	public boolean isResizable() {
		return resizable;
	}
	
	
	/**
	 * Set whether the panel window is resizable
	 * @param resizable
	 */
	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}
	
	
	/**
	 * Set the maximum dimensions for this panel window.
	 * These should be greater than the minimum dimensions.
	 * @param maxWidth
	 * @param maxHeight
	 */
	public void setMaxDimensions(float maxWidth, float maxHeight) {
		this.MAX_WIDTH = maxWidth;
		this.MAX_HEIGHT = maxHeight;
	}
	
	
	/**
	 * Set the minimum dimensions for this panel window.
	 * These should be less than the maximum dimensions.
	 * @param minWidth
	 * @param minHeight
	 */
	public void setMinDimensions(float minWidth, float minHeight) {
		this.MIN_WIDTH = minWidth;
		this.MIN_HEIGHT = minHeight;
	}
	
	
	/**
	 * Set whether this panel window must 
	 * be kept within its parent when moved.
	 * @param keepWithinParent
	 */
	public void setKeepWithinParent(boolean keepWithinParent) {
		this.keepWithinParent = keepWithinParent;
	}
	
}
