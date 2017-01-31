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
import static org.lwjgl.nanovg.NanoVG.nvgScissor;
import static org.lwjgl.nanovg.NanoVG.nvgStroke;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeColor;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeWidth;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextBox;
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

		for (GUIElementInterface e : panel.getElements()) {
			if (panel.isClip()) {
				nvgScissor(getContext(), 
						panel.getPositionX() + panel.getPaddingLeft(), 
						panel.getPositionY() + panel.getPaddingTop(), 
						panel.getBounds().x - panel.getPaddingRight(), 
						panel.getBounds().y - panel.getPaddingBottom());
			}
			
			// Update the element's bounds
			e.updateBounds(panel, panel.getPosition());
			
			// Render element
			if (e instanceof Button) renderButton((Button)e);
			else if (e instanceof VectorScrollArea) renderScrollArea((VectorScrollArea)e, window);
			else if (e instanceof Panel) renderPanel((Panel)e, window, panel.getPosition());
			else if (e instanceof Label) renderLabel((Label)e);
			else if (e instanceof VectorTextBox) renderTextBox((VectorTextBox)e);
			else if (e instanceof VectorTextField) renderVectorTextField((VectorTextField)e);
		}
		
		// Render any text it may have
		if (panel instanceof ITextDisplay) {
			renderText(((ITextDisplay)panel).getTextComponent(), panel);
		}
		
		nvgGlobalAlpha(getContext(), 1.0f);
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
		renderPanel(scrollArea, window, scrollArea.getPosition());
		
		//TODO
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
		
		if (text.isClipText()) nvgIntersectScissor(getContext(), e.getPositionX(), e.getPositionY(), e.getBounds().x, e.getBounds().y);
		
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
		
		nvgResetScissor(getContext());
	}
	
	
	
	/* ===================
	 *  AUXILIARY METHODS
	 * ===================
	 */
	
	@Override
	protected void renderVectorButton(VectorButton button) {
		renderColouredVectorShape(button, button);
	}
	
	
	@Override
	protected void renderColouredVectorShape(IColouredVectorShape shape, GUIBoundsInterface e) {
		if (!shape.renderShape()) return;
		
		nvgBeginPath(getContext());
		
		// Draw the vector shape
		switch (shape.getShape()) {
		case RECTANGLE:
			nvgRect(getContext(), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight());
			break;
		case ROUNDED_RECT:
			nvgRoundedRect(getContext(), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), ((IVectorRoundedRect) e).getRadius());
			break;
		case ELLIPSE:
			nvgEllipse(getContext(), e.getPositionX() + e.getWidth(), e.getPositionY() + e.getHeight(), e.getWidth(), e.getHeight());
			break;
		}
		
		// Render the appropriate colour for the GUI element
		boolean mouseOver = false;
		boolean mouseDown = false;
		boolean selected = false;
		
		if (e instanceof MouseOverInterface) mouseOver = ((MouseOverInterface)e).isMouseOver();		
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
