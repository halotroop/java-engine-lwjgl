package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.window.BoundsInterface;


/**
 * A text box which wraps text at the specified width.
 * @author bwyap
 *
 */
public abstract class TextBox extends TextField implements IColouredVectorShape {
	
	
	private float absoluteTextBoxWidth;
	private float absolutePadding;
	private boolean submittable;
	
	
	public TextBox(float x, float y, float width, float height, float padding) {
		super(x, y, width, height);
		this.absolutePadding = padding;
		this.absoluteTextBoxWidth = width;
		this.submittable = false;
		
		text.setTextBox(true);
		text.setAlignment(ETextAlignment.TOP_LEFT);
		text.setPadding(absolutePadding, absolutePadding, absolutePadding, absolutePadding);
		text.setTextBoxWidth(absoluteTextBoxWidth - 2 * absolutePadding);
	}
	
	
	/**
	 * Check whether the contents of the text box are sent to the {@code onSubmit} method
	 * @return
	 */
	public boolean isSubmittable() {
		return submittable;
	}
	
	
	/**
	 * Set whether the contents of the text box are sent to the {@code onSubmit} method
	 * @param submittable
	 */
	public void setSubmittable(boolean submittable) {
		this.submittable = submittable;
	}
	
	
	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent) {
		super.updateBounds(window, parent);
		if (!scaleAbsolute) {
			text.setTextBoxWidth(absoluteTextBoxWidth * scaleX - 2 * absolutePadding * scaleX);
			text.setPadding(absolutePadding * scaleY, absolutePadding * scaleY, absolutePadding * scaleX, absolutePadding * scaleX);
		}
	}
	
	
	@Override
	public void enterPressed() {
		if (isSubmittable()) {
			super.enterPressed();
		}
		else {
			putChar('\n');
		}
	}
	
}
