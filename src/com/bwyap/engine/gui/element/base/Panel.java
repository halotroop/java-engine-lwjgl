package com.bwyap.engine.gui.element.base;

import java.util.ArrayList;
import java.util.List;

import com.bwyap.engine.gui.element.properties.VectorShapeColourProperties;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.input.InputHandler;


/**
 * A panel is a renderable GUI element which can
 * contain other GUI elements. The updating and rendering
 * of the GUIPanel should also take care of updating
 * and rendering all child elements.
 * @author bwyap
 *
 */
public abstract class Panel extends SelectableElement implements IColouredVectorShape {

	protected List<GUIElementInterface> elements;
	protected final VectorShapeColourProperties colours;
	
	private boolean renderShape;
	private boolean clip;

	
	public Panel(float x, float y, float width, float height) {
		super(x, y, width, height);
		colours = new VectorShapeColourProperties();
		elements = new ArrayList<GUIElementInterface>();
		renderShape = true;
		clip = true;
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}
	
	
	/**
	 * Add an element to the panel at the given co-ordinates.
	 * Co-ordinates are relative to the panel.
	 * @param e
	 */
	public void addElement(GUIElementInterface e, float x, float y) {
		e.setPosition(x, y);
		elements.add(e);
	}
	
	
	/**
	 * Add an element to the panel.
	 * @param e
	 */
	public void addElement(GUIElementInterface e) {
		elements.add(e);
	}
	
	
	/**
	 * Removes an element from the panel.
	 * @param e
	 * @return
	 */
	public boolean removeElement(GUIElementInterface e) {
		return elements.remove(e);
	}
	
	
	/**
	 * Get the GUI elements in this panel
	 * @return
	 */
	public List<GUIElementInterface> getElements() {
		return elements;
	}

	
	/**
	 * Set whether the panel should clip all GUI 
	 * elements rendered outside its bounds
	 * @param clip
	 */
	public void setClip(boolean clip) {
		this.clip = clip;
	}
	
	
	/**
	 * Check if the panel clips all GUI 
	 * elements rendered outside its bounds
	 * @return
	 */
	public boolean isClip() {
		return clip;
	}
	
	
	/**
	 * Set whether the panel should render the vector
	 * shape that represents the panel
	 * @param renderColour
	 */
	public void setRenderShape(boolean renderShape) {
		this.renderShape = renderShape;
	}
	
	
	@Override
	public boolean renderShape() {
		return renderShape;
	}
	
	
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		if (!mouseOver && input.isMouseDown()) {
			setSelected(false);
		}
		
		for (GUIElementInterface e : elements) {
			e.handleInput(input, this);
		}		
	}
	
	
	@Override
	public final void update(float timestep) {
		for (GUIElementInterface e : elements) {
			e.update(timestep);
		}
		onUpdate(timestep);
	}
	

	/**
	 * Override this method for custom update functionality
	 * @param timestep
	 */
	public void onUpdate(float timestep) {
		
	}
	

	/**
	 * Override this method for custom onSelect functionality
	 * @param timestep
	 */
	@Override
	public void onSelect() {
		
	}

	
	/**
	 * Override this method for custom onDeselect functionality
	 * @param timestep
	 */
	@Override
	public void onDeselect() {
		
	}
	
}
