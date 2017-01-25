package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.element.properties.TextComponent;
import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.ITextDisplay;
import com.bwyap.engine.input.InputHandler;


/**
 * A text field that allows text to be entered within it
 * when it is selected. It contains a {@code TextComponent}
 * to hold text for rendering.
 * @author bwyap
 *
 */
public abstract class TextField extends SelectableElement implements ITextDisplay {
	
	protected final TextComponent text;
	
	private boolean editable;
	private boolean showCaret;
	private boolean displayingCaret;
	private float caretBlinkTime = 0;
	private float BLINK_TIME = 0.5f;
	
	
	public TextField(float x, float y, float width, float height) {
		super(x, y, width, height);
		text = new TextComponent();
		text.setText("");
		text.setTextColour(1, 1, 1, 1);
		showCaret = true;
		editable = true;
	}
	
	
	/**
	 * Check if the text field is showing the blinking caret.
	 */
	public boolean showCaret() {
		return showCaret;
	}
	
	
	/**
	 * Set whether the text field should show the caret when it is selected.
	 * @param showCaret
	 */
	public void setShowCaret(boolean showCaret) {
		this.showCaret = showCaret;
	}
	
	
	/**
	 * Set the blink time of the caret.
	 * Default is 0.5f.
	 * @param blinkTime
	 */
	public void setBlinkTime(float blinkTime) {
		this.BLINK_TIME = blinkTime;
	}
	
	
	/**
	 * Get the blink time of the caret.
	 * @return
	 */
	public float getBlinkTime() {
		return BLINK_TIME;
	}


	/**
	 * Check whether the contents of this text field are editable
	 * @return
	 */
	public boolean isEditable() {
		return editable;
	}
	
	
	/**
	 * Set whether the contents of this text field are editable
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	
	/**
	 * Action to perform when the enter key is pressed 
	 * @param text
	 */
	public abstract void onEnter(String text);
	
	
	@Override
	public void onSelect() {
		if (editable) beginEnterText();
	}
	
	
	@Override
	public void onDeselect() {
		if (editable) stopEnterText();
	}
	
	
	@Override
	public void onMouseOver(float x, float y) {}

	
	@Override
	public TextComponent getTextComponent() {
		return text;
	}
	
	
	/**
	 * {@inheritDoc}
	 * <p>Override this method for custom update functionality.</p>
	 */
	@Override
	public void update(float timestep) { 
		if (showCaret) {
			if (isSelected() && editable) {
				caretBlinkTime += timestep;
				if (caretBlinkTime >= BLINK_TIME) {
					caretBlinkTime = 0;
					if (displayingCaret) {
						text.setText(text.getText().substring(0, text.getText().length() - 1) + " ");
						displayingCaret = false;
					}
					else {
						text.setText(text.getText().substring(0, text.getText().length() - 1) + "|");
						displayingCaret = true;
					}
				}
			}
		}
	}

	
	/**
	 * {@inheritDoc}
	 * <p>Override this method for custom input handling functionality.</p>
	 */
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		super.onHandleInput(input, bounds);
		
		if (isSelected() && editable) enterText(input);
	}
	
	
	private boolean clearCharBuffer;
	
	/**
	 * Set up the text component to begin editing the text
	 */
	protected void beginEnterText() {
		displayingCaret = true;
		text.appendText("|");
		caretBlinkTime = 0;
		clearCharBuffer = true;
	}
	
	
	/**
	 * Clean up after text in the text component has been edited
	 */
	protected void stopEnterText() {
		if (text.getText().length() > 0) text.setText(text.getText().substring(0, text.getText().length() - 1));
	}
	
	
	/**
	 * Poll the input handler to edit the text in the text component
	 */
	protected void enterText(InputHandler input) {
		if (clearCharBuffer) {
			input.consumeLastTypedChar();
			clearCharBuffer = false;
		}
		char c = input.consumeLastTypedChar();
		if (c == '\b') {
			// Remove a character if the key was backspace
			if (showCaret && displayingCaret) {
				if (text.getText().length() > 1) text.setText(text.getText().substring(0, text.getText().length() - 2) + "|");
			}
			else if (text.getText().length() > 1) text.setText(text.getText().substring(0, text.getText().length() - 2) + " ");
		}
		else if (c == '\n') {
			// Call the enter method if the enter key was pressed
			if (showCaret) onEnter(text.getText().substring(0, text.getText().length() - 1));
			else onEnter(text.getText());
		}
		else if (c != 0) {
			// Add a character to the text field
			if (showCaret) {
				if (displayingCaret) text.setText(text.getText().substring(0, text.getText().length() - 1) + c + "|");
				else text.setText(text.getText().substring(0, text.getText().length() - 1) + c + " ");
			}
			else text.setText(text.getText() + c);
		}
	}
	
}
