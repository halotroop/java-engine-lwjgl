package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.BoundsInterface;


/**
 * A scroll area provides a panel which has the ability to scroll
 * to access GUI elements outside the panel bounds.
 * @author bwyap
 *
 */
public abstract class ScrollArea extends Panel {
	
	/**
	 * A enumeration of the directions the scroll area can scroll.
	 * @author bwyap
	 */
	public enum ScrollDirection {
		HORIZONTAL, VERTICAL;
	}
	
	
	static final float TOP_LIMIT = 0;
	static final float PADDING = 2;
	static final float SCROLL_SPEED = 5;
	static final float SCROLL_BAR_RADIUS = 2;
	
	private boolean renderScrollBar;
	private float scrollLength;
	private float scrollPosition;
	private float scrollDelta;
	private float scrollBarLength;
	private float scrollBarPosition;
	
	private final ScrollBar scrollBar;
	private final ScrollDirection scrollDirection;
	
	
	/**
	 * Create a scroll area with the specified scroll length.
	 * The scroll length is the length in pixels which the 
	 * panel can be scrolled past its starting position. 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param scrollLength
	 * @param scrollDirection
	 */
	public ScrollArea(float x, float y, float width, float height, float scrollLength, ScrollDirection scrollDirection) {
		super(x, y, width, height);
		this.scrollPosition = 0;
		this.scrollDirection = scrollDirection;
		this.renderScrollBar = false;
		setScrollLength(scrollLength);
		setSelectable(false);
		setReactToMouseOver(false);
		
		if (scrollDirection == ScrollDirection.VERTICAL) 
			this.scrollBar = new ScrollBar(width - 6, PADDING, 4, getScrollBarLength(), SCROLL_BAR_RADIUS);
		else 
			this.scrollBar = new ScrollBar(PADDING, height - 6, getScrollBarLength(), 4, SCROLL_BAR_RADIUS);		
	}
	
	
	/**
	 * Check whether the scroll bar should be rendered at the moment
	 * @return
	 */
	public boolean renderScrollBar() {
		return renderScrollBar;
	}
	
	
	/**
	 * Get the scroll bar for this scroll area
	 * @return
	 */
	public ScrollBar getScrollBar() {
		return scrollBar;
	}
	
	
	/**
	 * Set the scroll length of the scroll area.
	 * This value should be larger than the height
	 * of the GUI element.
	 * @param scrollLength
	 */
	public void setScrollLength(float scrollLength) {
		this.scrollLength = scrollLength;
		if (scrollDirection == ScrollDirection.VERTICAL) {
			scrollBarLength = (getHeight()/(getHeight() + scrollLength + 2 * PADDING)) * getHeight(); 
		}
		else if (scrollDirection == ScrollDirection.HORIZONTAL) {
			scrollBarLength = (getWidth()/(getWidth() + scrollLength + 2 * PADDING)) * getWidth(); 
		}
	}
	
	
	/**
	 * Get the scroll length of the scroll area
	 * @return
	 */
	public float getScrollLength() {
		return scrollLength;
	}
	
	
	/**
	 * Get the length of the scrollbar for the scroll area
	 * @return
	 */
	public float getScrollBarLength() {
		return scrollBarLength;
	}
	
	
	/**
	 * Get the scroll direction of the scroll area.
	 * @return
	 */
	public ScrollDirection getScrollDirection() {
		return scrollDirection;
	}
	
	
	/**
	 * Get the position of the scroll bar
	 * @return
	 */
	public float getScrollBarPosition() {
		return scrollBarPosition;
	}
	
	
	/**
	 * Set the scroll position of the scroll area
	 * @param position
	 */
	public void setScrollPosition(float newScrollPosition) {
		setScrollDelta(newScrollPosition - scrollPosition);
	}
	
	
	/**
	 * Move the scroll position by the specified amount
	 * @param scrollDelta
	 */
	public void setScrollDelta(float scrollDelta) {
		float position = scrollPosition + scrollDelta;
		if (position < -scrollLength) {
			this.scrollDelta = - scrollPosition - scrollLength;
		}
		else if (position > TOP_LIMIT) {
			this.scrollDelta = - scrollPosition;
		}
		else {
			this.scrollDelta = scrollDelta;
		}

		scrollPosition += this.scrollDelta;	
		scrollBarPosition = PADDING + (scrollPosition/(scrollLength + 2*PADDING)) * (getScrollBarLength() - (scrollDirection == ScrollDirection.VERTICAL ? getHeight() : getWidth()));
	}
	
	
	/**
	 * Get the current scroll position of the scroll area
	 */
	public float getScrollPosition() {
		return scrollPosition;
	}
	
	
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		super.onHandleInput(input, bounds);
		
		// Handle input for the scroll bar and the elements within the scroll area
		if (withinBounds((float)input.getMouseX(), (float)input.getMouseY())) {
			renderScrollBar = true;
			scrollBar.handleInput(input, bounds);
			
			// Position the scroll bar
			if (scrollDirection == ScrollDirection.VERTICAL) setScrollDelta((float)input.consumeMouseScrollY());
			else if (scrollDirection == ScrollDirection.HORIZONTAL) setScrollDelta((float) input.consumeMouseScrollX());
		}
		else renderScrollBar = false;
	}
	
	
	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent) {
		super.updateBounds(window, parent);
		scrollBar.updateBounds(this, parent);
		
		// Set position of GUI elements
		for (GUIElementInterface e : elements) {
			if (scrollDirection == ScrollDirection.VERTICAL) 
				e.setPosition(e.getAbsolutePosition().x, e.getAbsolutePosition().y + scrollDelta * SCROLL_SPEED);
			else if (scrollDirection == ScrollDirection.HORIZONTAL) 
				e.setPosition(e.getAbsolutePosition().x + scrollDelta * SCROLL_SPEED, e.getAbsolutePosition().y);
		}
		
		// Set position of scroll bar
		if (scrollDirection == ScrollDirection.VERTICAL) 
			scrollBar.setPosition(scrollBar.getAbsolutePosition().x, getScrollBarPosition());
		else if (scrollDirection == ScrollDirection.HORIZONTAL) 
			scrollBar.setPosition(getScrollBarPosition(), scrollBar.getAbsolutePosition().y);		
	}

}
