package com.bwyap.engine.gui.element;

import com.bwyap.engine.gui.element.base.ETextAlignment;
import com.bwyap.engine.gui.element.base.GUIElement;
import com.bwyap.engine.gui.element.properties.TextComponent;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.GUIElementInterface;
import com.bwyap.engine.gui.interfaces.ITextDisplay;
import com.bwyap.engine.input.InputHandler;
import com.bwyap.lwjgl.engine.resource.LWJGLResourceManager;


/**
 * An GUI element that displays text. NOTE: The bounds for this element 
 * are not used to describe the bounds of the text. If you need to detect
 * mouse interaction with the element, use a button instead.
 * @author bwyap
 *
 */
public class Label extends GUIElement implements GUIElementInterface, ITextDisplay {

	
	private TextComponent text;
	
	
	public Label(String string, float x, float y) {
		super(x, y, 1, 1);
		text = new TextComponent(string);
		text.setClipText(false);
		text.setTextSize(20f);
		text.setAlignment(ETextAlignment.TOP_LEFT);
		// Set the font of the label text
		text.setFontName(LWJGLResourceManager.instance().lib.getFont("default"));
	}
	
	
	@Override
	public TextComponent getTextComponent() {
		return text;
	}


	@Override
	public void update(float timestep) { }


	@Override
	public void handleInput(InputHandler input, GUIBoundsInterface bounds) { }

}
