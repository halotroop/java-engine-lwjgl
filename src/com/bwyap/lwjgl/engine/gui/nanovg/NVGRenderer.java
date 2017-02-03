package com.bwyap.lwjgl.engine.gui.nanovg;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgEllipse;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgGlobalAlpha;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgResetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgStroke;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeColor;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeWidth;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextBox;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreateGL3;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import com.bwyap.engine.gui.GUIRenderer;
import com.bwyap.engine.gui.element.Label;
import com.bwyap.engine.gui.element.TexturedButton;
import com.bwyap.engine.gui.element.base.Button;
import com.bwyap.engine.gui.element.base.Panel;
import com.bwyap.engine.gui.element.base.PanelWindow;
import com.bwyap.engine.gui.element.base.VectorButton;
import com.bwyap.engine.gui.element.base.VectorCheckBox;
import com.bwyap.engine.gui.element.base.VectorCheckBox.CheckBoxCheckStyle;
import com.bwyap.engine.gui.element.base.VectorScrollArea;
import com.bwyap.engine.gui.element.base.VectorTextBox;
import com.bwyap.engine.gui.element.base.VectorTextField;
import com.bwyap.engine.gui.element.properties.TextComponent;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.gui.interfaces.IColouredVectorShape;
import com.bwyap.engine.gui.interfaces.IFade;
import com.bwyap.engine.gui.interfaces.ISelectable;
import com.bwyap.engine.gui.interfaces.ITextDisplay;
import com.bwyap.engine.gui.interfaces.IVectorRoundedRect;
import com.bwyap.engine.gui.interfaces.MouseDownInterface;
import com.bwyap.engine.gui.interfaces.MouseOverInterface;
import com.bwyap.engine.window.WindowInterface;
import com.bwyap.enginedriver.resource.Resource;


/**
 * A GUI renderer that uses LWJGL's included NanoVG library
 * to render 2D vector graphics to the screen.
 * @author bwyap
 *
 */
public class NVGRenderer extends GUIRenderer {

	protected final long vgID;
	protected final Set<String> loadedFonts;
	
	private static NVGColor colour = NVGColor.create();
	
	private boolean resetAlpha = true;
	
	
	public NVGRenderer() throws Exception {
		// Initialise the GUI
		if (Resource.Settings.isAntiAlias())
			vgID = nvgCreateGL3(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
		else 
			vgID = nvgCreateGL3(NVG_STENCIL_STROKES);
		
		if (vgID == NULL) 
			throw new Exception("Could not initialise NanoVG");
		
		// TODO clean this up
		Resource.loadNVGTextures(this);
		
		loadedFonts = new HashSet<String>();		
	}
	
	
	/**
	 * Get the ID of the NanoVG context.
	 * @return
	 */
	public long getContext() {
		return vgID;
	}

	
	@Override
	protected final void beginRenderGUI(WindowInterface window) {
        nvgBeginFrame(vgID, window.getWidth(), window.getHeight(), 2);
	}
	
	
	@Override
	protected void renderGUI(Panel panel, WindowInterface window) {
		renderPanel(panel, window, panel.getPosition());
	}
	
	
	@Override
	protected final void endRenderGUI() {
        nvgEndFrame(vgID);
        
        // Restore state
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	
	/* =================
	 * RENDERING METHODS
	 * =================
	 */
	
	
	@Override
	public void renderPanel(Panel panel, WindowInterface window, Vector2f parent) {
		if (panel instanceof PanelWindow) {
			if (!((PanelWindow)panel).isVisible()) return;
		}
		
		if (panel instanceof IFade) {
			if (((IFade)panel).getFade().isFading()) {
				nvgGlobalAlpha(getContext(), ((IFade)panel).getFade().getFade());
			}
		}
		
		panel.updateBounds(window, parent);
		renderColouredVectorShape(panel, panel);
		
		if (panel.isClip()) {
			pushScissor(getContext(), 
					panel.getPositionX() + panel.getPaddingLeft(), 
					panel.getPositionY() + panel.getPaddingTop(), 
					panel.getBounds().x - panel.getPaddingRight(), 
					panel.getBounds().y - panel.getPaddingBottom());
		}
		
		for (GUIElementInterface e : panel.getElements()) {
			// Update the element's bounds
			e.updateBounds(panel, panel.getPosition());
			
			// Render element
			if (e instanceof Button) renderButton((Button)e);
			else if (e instanceof VectorScrollArea) renderScrollArea((VectorScrollArea)e, window);
			else if (e instanceof VectorCheckBox) renderVectorCheckBox((VectorCheckBox)e);
			else if (e instanceof Label) renderLabel((Label)e);
			else if (e instanceof VectorTextBox) renderTextBox((VectorTextBox)e);
			else if (e instanceof VectorTextField) renderVectorTextField((VectorTextField)e);
			else if (e instanceof Panel) {
				if (e instanceof PanelWindow) resetAlpha = false;
				renderPanel((Panel)e, window, panel.getPosition());
				resetAlpha = true;
			}
		}
		
		// Render any text it may have
		if (panel instanceof ITextDisplay) {
			renderText(((ITextDisplay)panel).getTextComponent(), panel);
		}
		
		popScissor(getContext());
		if (resetAlpha) nvgGlobalAlpha(getContext(), 1.0f);
	}
	
	
	@Override
	public void renderVectorTextField(VectorTextField textfield) {
		renderColouredVectorShape(textfield, textfield);
		renderText(textfield.getTextComponent(), textfield);
	}

	
	@Override
	public void renderTextBox(VectorTextBox textBox) {
		renderColouredVectorShape(textBox, textBox);
		renderText(textBox.getTextComponent(), textBox);
	}
	
	
	@Override
	public void renderLabel(Label label) {
		renderText(label.getTextComponent(), label);
	}
	
	
	@Override
	public void renderScrollArea(VectorScrollArea scrollArea, WindowInterface window) {
		// Render the panel contents
		renderPanel(scrollArea, window, scrollArea.getPosition());	
		
		// Render the scroll bar
		if (scrollArea.renderScrollBar()) renderColouredVectorShape(scrollArea.getScrollBar(), scrollArea.getScrollBar());
	}
	
	
	@Override
	public void renderButton(Button button) {		
		// Render the button 
		if (button instanceof TexturedButton) renderTexturedButton((TexturedButton) button);
		else if (button instanceof VectorButton) renderVectorButton((VectorButton) button);
		
		// Render any text it may have
		renderText(button.getTextComponent(), button);
	}
	
	
	@Override
	public void renderVectorCheckBox(VectorCheckBox checkbox) {
		// Render the check box
		renderColouredVectorShape(checkbox, checkbox);
				
		if (!checkbox.isSelected() || checkbox.getCheckStyle() == CheckBoxCheckStyle.NONE) return;
		
		float ox = checkbox.getPositionX();
		float oy = checkbox.getPositionY();
		float w = checkbox.getWidth();
		float h = checkbox.getHeight();
		float padding = checkbox.getCheckPadding();

		// Render the check mark
		switch (checkbox.getCheckStyle()) {
		case CROSS:
			nvgBeginPath(getContext());
			nvgMoveTo(getContext(), ox + padding, oy + padding);
			nvgLineTo(getContext(), ox + w - padding, oy + h - padding);
			nvgMoveTo(getContext(), ox + w - padding, oy + padding);
			nvgLineTo(getContext(), ox + padding, oy + h - padding);
			nvgStrokeWidth(getContext(), checkbox.getCheckStrokeWidth());
			nvgStrokeColor(getContext(), rgba(checkbox.getCheckColour())); 
			nvgStroke(getContext());
			break;
		case DOT:
			nvgBeginPath(getContext());
			nvgCircle(getContext(), ox + w/2, oy + h/2, w/2 - 4);
			nvgFillColor(getContext(), rgba(checkbox.getCheckColour())); 
			nvgFill(getContext());
			break;
		case TICK:
			nvgBeginPath(getContext());
			nvgMoveTo(getContext(), ox + padding, oy + h/2);
			nvgLineTo(getContext(), ox + padding + w/4, oy + h - padding);
			nvgLineTo(getContext(), ox + w - padding, oy + padding);
			nvgStrokeWidth(getContext(), checkbox.getCheckStrokeWidth());
			nvgStrokeColor(getContext(), rgba(checkbox.getCheckColour())); 
			nvgStroke(getContext());
			break;
			
		// This should never be called
		case NONE: break;
		}
		
	}
	

	@Override
	public void renderText(TextComponent text, GUIBoundsInterface e) {
		// Check there is text to render 
		if (text.getText().equals("")) return;
				
		// Load the font if it isn't already loaded
		if (!loadedFonts.contains(text.getFontName())) {
			NVGFont.acquireFont(getContext(), text.getFontName());
			loadedFonts.add(text.getFontName());
		}

		float scaleFactor = 1.0f;
		if (text.scale()) {
			float AR = e.getBounds().x/e.getBounds().y;
			float originalAR = e.getAbsoluteBounds().x/e.getAbsoluteBounds().y;
			// Wider
			if (AR > originalAR) scaleFactor = e.getBounds().y/e.getAbsoluteBounds().y;
			// Thinner
			else scaleFactor = e.getBounds().x/e.getAbsoluteBounds().x;
		}
		nvgFontSize(getContext(), text.getAbsoluteTextSize() * scaleFactor);
		
		nvgFontFace(getContext(), NVGFont.getFont(text.getFontName()).getName());
		nvgFillColor(getContext(), rgba(text.getTextColour()));
		
		if (text.isClipText()) pushScissor(getContext(), e.getPositionX(), e.getPositionY(), e.getBounds().x, e.getBounds().y);
		
		float xPos = 0, yPos = 0;
		
		switch (text.getAlignment()) {
		case LEFT:		
			nvgTextAlign(getContext(), NVG_ALIGN_LEFT 	| NVG_ALIGN_MIDDLE); 
			xPos = e.getPositionX() + text.getPaddingLeft() + text.getOffset().x;
			yPos = e.getPositionY() + e.getBounds().y / 2 + text.getOffset().y;
			break;
		case CENTER:	
			nvgTextAlign(getContext(), NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
			xPos = e.getPositionX() + e.getBounds().x / 2 + text.getOffset().x;
			yPos = e.getPositionY() + e.getBounds().y / 2 + text.getOffset().y;
			break;
		case RIGHT:		
			nvgTextAlign(getContext(), NVG_ALIGN_RIGHT 	| NVG_ALIGN_MIDDLE); 
			xPos = e.getPositionX() + e.getBounds().x - text.getPaddingRight() + text.getOffset().x;
			yPos = e.getPositionY() + e.getBounds().y / 2 + text.getOffset().y;
			break;
		case BOTTOM_LEFT:
			nvgTextAlign(getContext(), NVG_ALIGN_LEFT 	| NVG_ALIGN_BOTTOM); 
			xPos = e.getPositionX() + text.getPaddingLeft() + text.getOffset().x;
			yPos = e.getPositionY() + e.getBounds().y - text.getPaddingBottom() + text.getOffset().y;
			break;
		case BOTTOM_CENTER:
			nvgTextAlign(getContext(), NVG_ALIGN_CENTER | NVG_ALIGN_BOTTOM); 
			xPos = e.getPositionX() + e.getBounds().x / 2 + text.getOffset().x;
			yPos = e.getPositionY() + e.getBounds().y - text.getPaddingBottom() + text.getOffset().y;
			break;
		case BOTTOM_RIGHT:
			nvgTextAlign(getContext(), NVG_ALIGN_RIGHT 	| NVG_ALIGN_BOTTOM); 
			xPos = e.getPositionX() + e.getBounds().x - text.getPaddingRight() + text.getOffset().x;
			yPos = e.getPositionY() + e.getBounds().y - text.getPaddingBottom() + text.getOffset().y;
			break;
		case TOP_LEFT:
			nvgTextAlign(getContext(), NVG_ALIGN_LEFT 	| NVG_ALIGN_TOP); 
			xPos = e.getPositionX() + text.getPaddingLeft() + text.getOffset().x;
			yPos = e.getPositionY() + text.getPaddingTop() + text.getOffset().y;
			break;
		case TOP_CENTER:
			nvgTextAlign(getContext(), NVG_ALIGN_CENTER | NVG_ALIGN_TOP); 
			xPos = e.getPositionX() + e.getBounds().x / 2 + text.getOffset().x;
			yPos = e.getPositionY() + text.getPaddingTop() + text.getOffset().y;
			break;
		case TOP_RIGHT:
			nvgTextAlign(getContext(), NVG_ALIGN_RIGHT 	| NVG_ALIGN_TOP); 
			xPos = e.getPositionX() + e.getBounds().x - text.getPaddingRight() + text.getOffset().x;
			yPos = e.getPositionY() + text.getPaddingTop() + text.getOffset().y;
			break;
		}
		
		if (text.isTextBox()) nvgTextBox(getContext(), xPos, yPos, text.getTextBoxWidth(), text.getText(), 0);
		else nvgText(getContext(), xPos, yPos, text.getText(), 0);
		
		if (text.isClipText()) popScissor(getContext());
	}
	
	
	
	/* ===================
	 *  AUXILIARY METHODS
	 * ===================
	 */
	
	@Override
	protected void renderVectorButton(VectorButton button) {
		renderColouredVectorShape(button, button);
	}
	
	
	/**
	 * Render a coloured vector shape to the screen (with no offset)
	 */
	protected void renderColouredVectorShape(IColouredVectorShape shape, GUIBoundsInterface e) {
		renderColouredVectorShape(shape, e, 0, 0);
	}
	
	
	@Override
	protected void renderColouredVectorShape(IColouredVectorShape shape, GUIBoundsInterface e, float offsetX, float offsetY) {
		if (!shape.renderShape()) return;
		
		nvgBeginPath(getContext());
		
		// Draw the vector shape
		switch (shape.getShape()) {
		case RECTANGLE:
			nvgRect(getContext(), offsetX + e.getPositionX(), offsetY + e.getPositionY(), e.getWidth(), e.getHeight());
			break;
		case ROUNDED_RECT:
			nvgRoundedRect(getContext(), offsetX + e.getPositionX(),  offsetY + e.getPositionY(), e.getWidth(), e.getHeight(), ((IVectorRoundedRect) e).getRadius());
			break;
		case ELLIPSE:
			nvgEllipse(getContext(), offsetX + e.getPositionX() + e.getWidth(), offsetY + e.getPositionY() + e.getHeight(), e.getWidth(), e.getHeight());
			break;
		}
		
		// Render the appropriate colour for the GUI element
		boolean mouseOver = false;
		boolean mouseDown = false;
		boolean selected = false;
		
		if (e instanceof MouseOverInterface) mouseOver = ((MouseOverInterface)e).isMouseOver() && ((MouseOverInterface)e).mouseOverReact();		
		if (e instanceof MouseDownInterface) mouseDown = ((MouseDownInterface)e).isMouseDown();
		if (e instanceof ISelectable) selected = ((ISelectable)e).isSelected();
		
		if (selected) {
			if (mouseDown) nvgFillColor(getContext(), rgba(shape.colourProperties().getMouseDownColour())); 
			else nvgFillColor(getContext(), rgba(shape.colourProperties().getSelectedColour())); 
		}
		else {
			if (mouseOver) {
				if (mouseDown) nvgFillColor(getContext(), rgba(shape.colourProperties().getMouseDownColour())); 
				else nvgFillColor(getContext(), rgba(shape.colourProperties().getMouseoverColour())); 
			}
			else nvgFillColor(getContext(), rgba(shape.colourProperties().getColour())); 
		}
		
		// Render the border
		if (shape.colourProperties().hasBorder()) {
			if (mouseOver) nvgStrokeColor(getContext(), rgba(shape.colourProperties().getBorderMouseoverColour()));
			else nvgStrokeColor(getContext(), rgba(shape.colourProperties().getBorderColour()));
			
			nvgStrokeWidth(getContext(), shape.colourProperties().getBorderWidth());
			nvgStroke(getContext());
		}
		
		nvgFill(getContext());
	}
	
	
	@Override
	protected void renderTexturedButton(TexturedButton button) {
		NVGPaint paint = NVGPaint.create();
		
		// Render the texture
		if (button.isMouseOver()) {
			if (button.isMouseDown()) nvgImagePattern(getContext(), button.getPositionX(), button.getPositionY(), button.getWidth(), button.getHeight(), 0, NVGTexture.getTexture(button.getPressedTexture()), 1, paint);
			else nvgImagePattern(getContext(), button.getPositionX(), button.getPositionY(), button.getWidth(), button.getHeight(), 0, NVGTexture.getTexture(button.getMouseoverTexture()), 1, paint);
		}
		else nvgImagePattern(getContext(), button.getPositionX(), button.getPositionY(), button.getWidth(), button.getHeight(), 0, NVGTexture.getTexture(button.getTexture()), 1, paint);
		
		nvgBeginPath(getContext());
		nvgRoundedRect(getContext(), button.getPositionX(), button.getPositionY(), button.getWidth(), button.getHeight(), 5);
		nvgFillPaint(getContext(), paint);
		nvgFill(getContext());
	}
	
	
	/**
	 * A class to represent bounds for a scissor rectangle
	 * @author bwyap
	 *
	 */
	private static class ScissorBounds {
		final float x, y, w, h;
		ScissorBounds(float x, float y, float w, float h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}
	
	private ArrayList<ScissorBounds> scissorBoundsStack = new ArrayList<ScissorBounds>();
	
	
	/**
	 * Push a new scissor onto the scissor stack and intersect it with the current scissor
	 * @param ctx
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void pushScissor(long ctx, float x, float y, float w, float h) {
		scissorBoundsStack.add(new ScissorBounds(x, y, w, h));
		nvgIntersectScissor(ctx, x, y, w, h);
	}
	
	
	/**
	 * Undo the last applied scissor
	 * @param ctx
	 */
	private void popScissor(long ctx) {
		if (scissorBoundsStack.size() <= 1) {
			if (scissorBoundsStack.size() == 1) scissorBoundsStack.clear();
			nvgResetScissor(ctx);
		}
		else {
			scissorBoundsStack.remove(scissorBoundsStack.size() - 1);
			nvgResetScissor(ctx);
			for (ScissorBounds b : scissorBoundsStack) {
				nvgIntersectScissor(ctx, b.x, b.y, b.w, b.h);
			}
		}		
	}
	
	
	/**
	 * Static method for converting an RGBA colour 
	 * value to a NVGColor equivalent. The result
	 * is stored in the given NVGColour object and
	 * it is also returned.
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @param colour
	 * @return
	 */
	private static NVGColor rgba(Vector4f colour) {
		NVGRenderer.colour.r(colour.x);
		NVGRenderer.colour.g(colour.y);
		NVGRenderer.colour.b(colour.z);
		NVGRenderer.colour.a(colour.w);
		return NVGRenderer.colour;
	}

}
