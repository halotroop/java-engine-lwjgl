package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;
import com.bwyap.engine.input.InputHandler;


/**
 * Used in the scroll area to indicate what position the scroll area is in.
 * @author bwyap
 *
 */
class ScrollBar extends ClickableElement implements IColouredVectorShape, IVectorRoundedRect {

	private float radius;
	private final VectorShapeColourProperties colour;
	

	public ScrollBar(float x, float y, float width, float height, float radius) {
		super(x, y, width, height);
		this.radius = radius;
		colour = new VectorShapeColourProperties();
		colour.setColour(0.1f, 0.1f, 0.1f, 0.5f);
	}
	
	
	@Override
	public float getRadius() {
		return radius;
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colour;
	}


	@Override
	public void onMouseClicked(float x, float y, int mouseButton) {
		
	}
	
	
	@Override
	public void onMouseOver(float x, float y) {
		
	}


	@Override
	public void update(float timestep) {
		
	}


	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		
	}
	
}
