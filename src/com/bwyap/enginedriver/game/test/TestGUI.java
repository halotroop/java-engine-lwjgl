package com.bwyap.enginedriver.game.test;

import com.bwyap.engine.gui.element.EllipticalButton;
import com.bwyap.engine.gui.element.RectangularButton;
import com.bwyap.engine.gui.element.RectangularScrollArea;
import com.bwyap.engine.gui.element.RoundedRectangularButton;
import com.bwyap.engine.gui.element.RoundedRectangularPanel;
import com.bwyap.engine.gui.element.RoundedRectangularPanelWindow;
import com.bwyap.engine.gui.element.RoundedRectangularTextBox;
import com.bwyap.engine.gui.element.RoundedRectangularTextfield;
import com.bwyap.engine.gui.element.TexturedButton;
import com.bwyap.engine.gui.element.base.ETextAlignment;
import com.bwyap.engine.gui.element.base.PanelWindow;
import com.bwyap.engine.gui.element.base.ScrollArea.ScrollDirection;
import com.bwyap.enginedriver.resource.Resource;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGGUI;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGRenderer;

public class TestGUI extends NVGGUI {
	
	
	public TestGUI() {
		super(Resource.Settings.getWidth(), Resource.Settings.getHeight());
	}
	
	
	@Override
	public void init(NVGRenderer renderer) {
		
		RectangularButton button = new RectangularButton(5, 5, 100, 30) {
			@Override
			public void onMouseClicked(float x, float y, int mouseButton) {
				System.out.println("Pressed rectangle with " + mouseButton);
			}
		};
		button.colourProperties().setColour(1, 0, 0, 1);
		button.colourProperties().setMouseoverColour(0, 1, 0, 1);
		button.colourProperties().setHasBorder(true);
		button.colourProperties().setBorderColour(0, 1, 1, 1);
		button.getTextComponent().setFontName(Resource.defaultFont);
		button.getTextComponent().setText("Press me now");
		addElement(button);
		
		
		EllipticalButton ebutton = new EllipticalButton(150, 5, 50f, 15f) {
			@Override
			public void onMouseClicked(float x, float y, int mouseButton) {
				System.out.println("Pressed ellipse with " + mouseButton);
			}
		};
		ebutton.colourProperties().setHasBorder(true);
		addElement(ebutton);
		
		
		RoundedRectangularButton rbutton = new RoundedRectangularButton(5, 50, 100f, 30f, 10.0f) {
			@Override
			public void onMouseClicked(float x, float y, int mouseButton) {
				System.out.println("Pressed rounded with " + mouseButton);
			}
		};
		rbutton.colourProperties().setColour(1, 0, 0, 1);
		rbutton.colourProperties().setMouseoverColour(0, 1, 0, 1);
		addElement(rbutton);
		
		
		TexturedButton tbutton = new TexturedButton(5, 100, 120, 50) {
			@Override
			public void onMouseClicked(float x, float y, int mouseButton) {
				System.out.println("Pressed textured with " + mouseButton);				
			}
		};
		tbutton.setTexture("testButton");
		tbutton.setMouseoverTexture("testButtonMouseover");
		tbutton.setPressedTexture("testButtonPressed");
		addElement(tbutton);
		
		
		RoundedRectangularTextfield field = new RoundedRectangularTextfield(200, 100, 200, 25, 5) {
			@Override
			public void onSubmit(String text) {				
				System.out.println("Enter pressed: " + text);
			}
		};
		field.getTextComponent().setAlignment(ETextAlignment.LEFT);
		field.getTextComponent().setFontName(Resource.defaultFont);
		field.getTextComponent().setPadding(5, 5, 5, 5);
		field.colourProperties().setHasBorder(true);
		addElement(field);
		
		
		PanelWindow window = new RoundedRectangularPanelWindow("", 5, 300, 300, 200, 5) {
			@Override
			protected void initElements() { }
		};
		window.setVisible(true);
		window.setSelectable(false);
		addElement(window);
		
		
		RoundedRectangularPanel panel = new RoundedRectangularPanel(450, 10, 400, 400, 4);
		panel.colourProperties().setColour(1, 1, 1, 0.5f);
		panel.colourProperties().setMouseoverColour(1, 1, 1, 0.5f);
		panel.colourProperties().setMouseDownColour(1, 1, 1, 0.5f);
		panel.setSelectable(false);
		
		
		PanelWindow window2 = new RoundedRectangularPanelWindow("Panel", 0, 0, 200, 200, 5) {
			@Override
			protected void initElements() { }
		};
		window2.setVisible(true);
		window2.setSelectable(false);
		window2.setResizable(true);
		
		RectangularButton button2 = new RectangularButton(20, 20, 100, 30) {
			@Override
			public void onMouseClicked(float x, float y, int mouseButton) {
				System.out.println("Pressed rectangle2 with " + mouseButton);
			}
		};
		button2.colourProperties().setColour(1, 0, 0, 1);
		button2.colourProperties().setMouseoverColour(0, 1, 0, 1);
		button2.colourProperties().setHasBorder(true);
		button2.colourProperties().setBorderColour(0, 1, 1, 1);
		button2.getTextComponent().setFontName(Resource.defaultFont);
		button2.getTextComponent().setText("Press me 2");
		button2.setPositionAbsolute(true);
		window2.addElement(button2);
		panel.addElement(window2);
		
		addElement(panel);		
		
		RoundedRectangularTextBox textbox = new RoundedRectangularTextBox(0, 0, 300, 100, 5, 5) {
			@Override
			public void onSubmit(String text) {
				System.out.println("Enter pressed on textbox");
			} 
		};
		textbox.colourProperties().setColour(0.8f, 0.8f, 0.8f, 0.8f);
		textbox.getTextComponent().setTextColour(0.1f, 0.0f, 0.0f, 1.0f);
		textbox.getTextComponent().setText(""
				+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore magna.");
		//		+ "aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
		//		+ "ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
		textbox.setEditable(true);
		
		//panel.addElement(textbox);
		
		
		RectangularScrollArea scroll = new RectangularScrollArea(5, 300, 300, 100, 10, ScrollDirection.VERTICAL) {
			
		};
		scroll.colourProperties().setColour(0.5f, 0.5f, 0.5f, 0.5f);
		scroll.addElement(textbox);
		addElement(scroll);
	}
	

	@Override
	public void onUpdate(float timestep) {
		
	}

}
