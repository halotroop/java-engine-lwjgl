package com.bwyap.engine.gui.element.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public abstract class Panel extends SelectableElement implements IColouredVectorShape, Iterable<GUIElementInterface>, Iterator<GUIElementInterface> {

	protected static final int DEFAULT_PRIORITY = 100;

	protected final VectorShapeColourProperties colours;
		
	private boolean renderShape;
	private boolean clip;

	// Use a hash map to keep track of GUI elements with given priorities
	private final Map<Integer, List<GUIElementInterface>> elements;

	// Auxiliary variables for keeping track of GUI elements with given priorities
	private int elementCount = 0;
	private int iteratorNext = 0;
	private boolean elementsModified = false;
	private List<GUIElementInterface> sortedElements;
	
	
	public Panel(float x, float y, float width, float height) {
		super(x, y, width, height);
		colours = new VectorShapeColourProperties();
		renderShape = true;
		clip = true;
		
		elements = new HashMap<Integer, List<GUIElementInterface>>();
		sortedElements = new ArrayList<GUIElementInterface>();
	}
	
	
	@Override
	public VectorShapeColourProperties colourProperties() {
		return colours;
	}
	
	
	/**
	 * Add an element to the panel at the given co-ordinates
	 * with the default priority. Co-ordinates are relative to the panel.
	 * @param e
	 */
	public void addElement(GUIElementInterface e, float x, float y) {
		e.setPosition(x, y);
		addElement(DEFAULT_PRIORITY, e);
	}
	
	/**
	 * Add an element to the panel at the given co-ordinates
	 * with the specified priority. Co-ordinates are relative to the panel.
	 * @param e
	 */
	public void addElement(int priority, GUIElementInterface e, float x, float y) {
		e.setPosition(x, y);
		addElement(priority, e);
	}
	
	
	/**
	 * Add an element to the panel with the default priority (100).
	 * @param e
	 */
	public void addElement(GUIElementInterface e) {
		addElement(DEFAULT_PRIORITY, e);
	}
	
	
	/**
	 * Add an element to the panel with the specified priority.
	 * @param priority
	 * @param e
	 */
	public void addElement(int priority, GUIElementInterface e) {
		elementsModified = true;
		elementCount++;
		if (elements.containsKey(priority)) {
			elements.get(priority).add(e);
		}
		else {
			List<GUIElementInterface> list = new ArrayList<GUIElementInterface>();
			list.add(e);
			elements.put(priority, list);
		}
	}
	
	
	
	/**
	 * Removes an element from the panel.
	 * @param e
	 * @return
	 */
	public boolean removeElement(GUIElementInterface e) {
		elementsModified = true;
		elementCount--;
		
		// Find the element and remove it
		for (int k : elements.keySet()) {
			if (elements.get(k).contains(e)) {
				return elements.get(k).remove(e);
			}
		}
		return false;
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
		
		Iterator<GUIElementInterface> it = iterator();
		while (it.hasNext()) {
			it.next().handleInput(input, this);
		}
	}
	
	
	@Override
	public final void update(float timestep) {
		Iterator<GUIElementInterface> it = iterator();
		while(it.hasNext()) {
			it.next().update(timestep);
		}
		onUpdate(timestep);
	}
	
	
	@Override
	public Iterator<GUIElementInterface> iterator() {
		iteratorNext = 0;
		
		// Sort element order if new element has been added
		if (elementsModified) {
			elementsModified = false;
			List<Integer> keys = new ArrayList<Integer>(elements.keySet());
			
			Collections.sort(keys, Collections.reverseOrder());
			
			sortedElements.clear();
			for (int k : keys) {
				sortedElements.addAll(elements.get(k));
			}
		}
		return this;
	}
	
	
	@Override
	public boolean hasNext() {
		return iteratorNext < elementCount;
	}
	
	
	@Override
	public GUIElementInterface next() {
		return sortedElements.get(iteratorNext++);
	}
	

	/**
	 * Override this method for custom update functionality
	 * @param timestep
	 */
	public void onUpdate(float timestep) { }
	

	/**
	 * Override this method for custom onSelect functionality
	 * @param timestep
	 */
	@Override
	public void onSelect() { }

	
	/**
	 * Override this method for custom onDeselect functionality
	 * @param timestep
	 */
	@Override
	public void onDeselect() { }
	
}
