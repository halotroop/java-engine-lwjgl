package com.bwyap.engine.gui.element.base;

import org.joml.Vector2f;

import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.window.BoundsInterface;
import com.bwyap.enginedriver.resource.Resource;


/**
 * A text box which wraps text at the specified width.
 * @author bwyap
 *
 */
public abstract class TextBox extends TextField implements IColouredVectorShape {
	
	
	private float absoluteTextBoxWidth;
	private float absolutePadding;
	
	
	public TextBox(float x, float y, float width, float height, float padding) {
		super(x, y, width, height);
		this.absolutePadding = padding;
		this.absoluteTextBoxWidth = width;
		
		text.setTextBox(true);
		text.setAlignment(ETextAlignment.TOP_LEFT);
		text.setFontName(Resource.defaultFont);
		text.setPadding(absolutePadding, absolutePadding, absolutePadding, absolutePadding);
		text.setTextBoxWidth(absoluteTextBoxWidth - 2 * absolutePadding);
	}
	
	
	@Override
	public void updateBounds(BoundsInterface window, Vector2f parent) {
		super.updateBounds(window, parent);
		if (!scaleAbsolute) {
			text.setTextBoxWidth(absoluteTextBoxWidth * scaleX - 2 * absolutePadding * scaleX);
			text.setPadding(absolutePadding * scaleY, absolutePadding * scaleY, absolutePadding * scaleX, absolutePadding * scaleX);
		}
	}
	
}
