package com.bwyap.engine.gui.element.properties;

import org.joml.Vector4f;


/**
 * A class that holds the colours that 
 * should be used to render a GUI element. 
 * @author bwyap
 *
 */
public class VectorShapeColourProperties implements GUIProperty {
	
	public final Vector4f colour, mouseoverColour, mouseDownColour, selectedColour;
	public final Vector4f borderColour, borderMouseoverColour;
	public boolean hasBorder;
	public float borderWidth;

	
	public VectorShapeColourProperties() {
		colour = new Vector4f(0.4f, 0.4f, 0.4f, 1.0f);
		mouseoverColour = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f);
		mouseDownColour = new Vector4f(0.6f, 0.6f, 0.6f, 0.95f);
		selectedColour = new Vector4f(0.48f, 0.55f, 0.53f, 1.0f);
		borderColour = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f);
		borderMouseoverColour = new Vector4f(0.9f, 0.9f, 0.9f, 1.0f);
		borderWidth = 3.0f;
	}
	
	
	
	/**
	 * Set the colour of the shape
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColour(float r, float g, float b, float a) {
		this.colour.x = r;
		this.colour.y = g;
		this.colour.z = b;
		this.colour.w = a;
	}
	
	/**
	 * Get the colour of the shape
	 * @return
	 */
	public Vector4f getColour() {
		return colour;
	}
	
	/**
	 * Set the colour of the shape when it is selected
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setSelectedColour(float r, float g, float b, float a) {
		this.selectedColour.x = r;
		this.selectedColour.y = g;
		this.selectedColour.z = b;
		this.selectedColour.w = a;
	}
	
	/**
	 * Get the colour of the shape
	 * @return
	 */
	public Vector4f getSelectedColour() {
		return selectedColour;
	}
	
	/**
	 * Set the colour of the shape when the mouse is over it
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setMouseoverColour(float r, float g, float b, float a) {
		this.mouseoverColour.x = r;
		this.mouseoverColour.y = g;
		this.mouseoverColour.z = b;
		this.mouseoverColour.w = a;
	}	
	
	/**
	 * Get the colour of the shape when the mouse is over it
	 * @return
	 */
	public Vector4f getMouseoverColour() {
		return mouseoverColour;
	}
	
	/**
	 * Set the colour of the shape when the mouse is pressing it
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setMouseDownColour(float r, float g, float b, float a) {
		this.mouseDownColour.x = r;
		this.mouseDownColour.y = g;
		this.mouseDownColour.z = b;
		this.mouseDownColour.w = a;
	}
	
	/**
	 * Get the colour of the shape when the mouse is pressing it
	 * @return
	 */
	public Vector4f getMouseDownColour() {
		return mouseDownColour;
	}
	
	/**
	 * Set the colour of the border
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBorderColour(float r, float g, float b, float a) {
		this.borderColour.x = r;
		this.borderColour.y = g;
		this.borderColour.z = b;
		this.borderColour.w = a;
	}
	
	/**
	 * Get the colour of the border
	 * @return
	 */
	public Vector4f getBorderColour() {
		return borderColour;
	}
	
	/**
	 * Set the colour of the border when the mouse is over the shape
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBorderMouseoverColour(float r, float g, float b, float a) {
		this.borderMouseoverColour.x = r;
		this.borderMouseoverColour.y = g;
		this.borderMouseoverColour.z = b;
		this.borderMouseoverColour.w = a;
	}
	
	/**
	 * Get the colour of the border when the mouse is over the shape
	 * @return
	 */
	public Vector4f getBorderMouseoverColour() {
		return borderMouseoverColour;
	}
	
	/**
	 * Set whether the shape should be rendered with a border
	 * @param hasBorder
	 */
	public void setHasBorder(boolean hasBorder) {
		this.hasBorder = hasBorder;
	}
	
	/**
	 * Check if the shape should be rendered with a border
	 * @return
	 */
	public boolean hasBorder() {
		return hasBorder;
	}
	
	/**
	 * Set the width of the border
	 * @param width
	 */
	public void setBorderWidth(float width) {
		this.borderWidth = width;
	}
	
	/**
	 * Get the width of the border
	 * @return
	 */
	public float getBorderWidth() {
		return borderWidth;
	}
	
}
