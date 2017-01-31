package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.input.InputHandler;

public abstract class ScrollArea extends Panel {

	public enum ScrollDirection {
		HORIZONTAL, VERTICAL;
	}
	
	private static final float TOP_LIMIT = 0;
	private static final float SCROLL_SPEED = 5;
	
	private float scrollLength;
	private float scrollPosition;
	private float prevScrollPosition;
	private float scrollDelta;
	
	private final ScrollDirection scrollDirection;
	
	
	public ScrollArea(float x, float y, float width, float height, float scrollLength, ScrollDirection scrollDirection) {
		super(x, y, width, height);
		this.scrollLength = scrollLength;
		this.scrollPosition = 0;
		this.scrollDirection = scrollDirection;
		setSelectable(false);
	}
	
	
	/**
	 * Set the scroll length of the scroll area.
	 * This value should be larger than the height
	 * of the GUI element.
	 * @param scrollLength
	 */
	public void setScrollLength(float scrollLength) {
		this.scrollLength = scrollLength;
	}
	
	
	/**
	 * Get the scroll length of the scroll area
	 * @return
	 */
	public float getScrollLength() {
		return scrollLength;
	}
	
	
	/**
	 * Get the scroll direction of the scroll area.
	 * @return
	 */
	public ScrollDirection getScrollDirection() {
		return scrollDirection;
	}
	
	
	/**
	 * Set the scroll position of the scroll area
	 * @param position
	 */
	public void setScrollPosition(float newScrollPosition) {
		prevScrollPosition = scrollPosition;
		scrollPosition = newScrollPosition;
		scrollDelta = scrollPosition - prevScrollPosition;
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

		prevScrollPosition = scrollPosition;
		scrollPosition += this.scrollDelta;
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
		
		if (withinBounds((float)input.getMouseX(), (float)input.getMouseY())) {
			if (scrollDirection == ScrollDirection.VERTICAL) setScrollDelta((float)input.consumeMouseScrollY());
			else if (scrollDirection == ScrollDirection.HORIZONTAL) setScrollDelta((float) input.consumeMouseScrollX());
			
			for (GUIElementInterface e : elements) {
				if (scrollDirection == ScrollDirection.VERTICAL) 
					e.setPosition(e.getAbsolutePosition().x, e.getAbsolutePosition().y + scrollDelta * SCROLL_SPEED);
				else if (scrollDirection == ScrollDirection.HORIZONTAL) 
					e.setPosition(e.getAbsolutePosition().x + scrollDelta * SCROLL_SPEED, e.getAbsolutePosition().y);
			}
		}
	}

}
