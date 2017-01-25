package com.bwyap.engine.gui.element.base;

import com.bwyap.engine.gui.interfaces.GUIBoundsInterface;
import com.bwyap.engine.gui.interfaces.ISelectable;
import com.bwyap.engine.input.InputHandler;


/**
 * A clickable GUI element which can be selected.
 * Accepted select buttons are inherited from the ClickableElement class.
 * @author bwyap
 *
 */
public abstract class SelectableElement extends ClickableElement implements ISelectable {

	private boolean selected;	
	private boolean selectable;
	
	public SelectableElement(float x, float y, float width, float height) {
		super(x, y, width, height);
		selected = false;
		selectable = true;
	}
	
	
	@Override
	public boolean isSelectable() {
		return selectable;
	}
	
	
	@Override
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	

	/**
	 * Set whether the GUI element has been selected or not.
	 * @param selected
	 * @return
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	@Override
	public final boolean isSelected() {
		return selected;
	}


	@Override
	public void onMouseClicked(float x, float y, int mouseButton) {
		if (selectable) {
			for (int button : acceptedButtons) {
				if (button == mouseButton && !isSelected()) {
					setSelected(true);
					onSelect();
					break; 
				}
			}
		}
	}
	

	@Override
	public void onMouseOver(float x, float y) {}
	

	/**
	 * {@inheritDoc}
	 * <p>Override this method for custom input handling functionality.</p>
	 */
	@Override
	public void onHandleInput(InputHandler input, GUIBoundsInterface bounds) {
		if (!mouseOver && input.isMouseDown() && isSelected()) {
			setSelected(false);
			onDeselect();
		}
	}
	
	
	/**
	 * Action to perform when the element is selected
	 */
	public abstract void onSelect();
	
	
	/**
	 * Action to perform when the element is deselected
	 */
	public abstract void onDeselect();

}
