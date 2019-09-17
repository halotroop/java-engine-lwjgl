package com.bwyap.enginedriver.game.test;

import org.joml.Vector4f;

import com.bwyap.engine.gui.element.CircularRadioButton;
import com.bwyap.engine.gui.element.EllipticalButton;
import com.bwyap.engine.gui.element.RectangularButton;
import com.bwyap.engine.gui.element.RectangularCheckBox;
import com.bwyap.engine.gui.element.RectangularSolidProgressBar;
import com.bwyap.engine.gui.element.RoundedRectangularButton;
import com.bwyap.engine.gui.element.RoundedRectangularCheckBox;
import com.bwyap.engine.gui.element.RoundedRectangularPanel;
import com.bwyap.engine.gui.element.RoundedRectangularPanelWindow;
import com.bwyap.engine.gui.element.RoundedRectangularScrollArea;
import com.bwyap.engine.gui.element.RoundedRectangularSolidProgressBar;
import com.bwyap.engine.gui.element.RoundedRectangularTextBox;
import com.bwyap.engine.gui.element.RoundedRectangularTextfield;
import com.bwyap.engine.gui.element.TexturedButton;
import com.bwyap.engine.gui.element.base.ETextAlignment;
import com.bwyap.engine.gui.element.base.ImageHolder;
import com.bwyap.engine.gui.element.base.PanelWindow;
import com.bwyap.engine.gui.element.base.ProgressBar.ProgressFillStyle;
import com.bwyap.engine.gui.element.base.RadioButtonGroup;
import com.bwyap.engine.gui.element.base.ScrollArea.ScrollDirection;
import com.bwyap.engine.gui.element.vector.VectorCheckBox.CheckBoxCheckStyle;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGGUI;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGRenderer;
import com.bwyap.lwjgl.engine.resource.LWJGLResourceManager;

public class TestGUI extends NVGGUI
{
	public TestGUI()
	{ super(LWJGLResourceManager.instance().settings().getWidth(),
		LWJGLResourceManager.instance().settings().getHeight()); }

	@Override
	public void init(NVGRenderer renderer)
	{
		RectangularButton button = new RectangularButton(5, 5, 100, 30)
		{
			@Override
			public void onMouseClicked(float x, float y, int mouseButton)
			{ System.out.println("Pressed rectangle with " + mouseButton); }
		};
		button.colourProperties().setColour(1, 0, 0, 1);
		button.colourProperties().setMouseoverColour(0, 1, 0, 1);
		button.colourProperties().setHasBorder(true);
		button.colourProperties().setBorderColour(0, 1, 1, 1);
		button.getTextComponent().setFontName(LWJGLResourceManager.instance().lib.getFont("default"));
		button.getTextComponent().setText("Press me now 123456789");
		addElement(button);
		EllipticalButton ebutton = new EllipticalButton(150, 5, 50f, 15f)
		{
			@Override
			public void onMouseClicked(float x, float y, int mouseButton)
			{ System.out.println("Pressed ellipse with " + mouseButton); }
		};
		ebutton.colourProperties().setHasBorder(true);
		addElement(ebutton);
		RoundedRectangularButton rbutton = new RoundedRectangularButton(5, 50, 100f, 30f, 10.0f)
		{
			@Override
			public void onMouseClicked(float x, float y, int mouseButton)
			{ System.out.println("Pressed rounded with " + mouseButton); }
		};
		rbutton.colourProperties().setColour(1, 0, 0, 1);
		rbutton.colourProperties().setMouseoverColour(0, 1, 0, 1);
		addElement(rbutton);
		TexturedButton tbutton = new TexturedButton(5, 100, 120, 50)
		{
			@Override
			public void onMouseClicked(float x, float y, int mouseButton)
			{ System.out.println("Pressed textured with " + mouseButton); }
		};
		tbutton.setTexture("test_button");
		tbutton.setMouseoverTexture("test_button_mouseover");
		tbutton.setPressedTexture("test_button_pressed");
		addElement(tbutton);
		RoundedRectangularTextfield field = new RoundedRectangularTextfield(200, 100, 200, 25, 5)
		{
			@Override
			public void onSubmit(String text)
			{ System.out.println("Enter pressed: " + text); }
		};
		field.getTextComponent().setAlignment(ETextAlignment.LEFT);
		field.getTextComponent().setFontName(LWJGLResourceManager.instance().lib.getFont("default"));
		field.getTextComponent().setPadding(5, 5, 5, 5);
		field.colourProperties().setHasBorder(true);
		addElement(field);
		RoundedRectangularPanel panel = new RoundedRectangularPanel(450, 10, 400, 400, 4);
		panel.colourProperties().setColour(1, 1, 1, 0.5f);
		panel.colourProperties().setMouseoverColour(1, 1, 1, 0.5f);
		panel.colourProperties().setMouseDownColour(1, 1, 1, 0.5f);
		panel.setSelectable(false);
		PanelWindow window2 = new RoundedRectangularPanelWindow("Panel", 300, 0, 200, 200, 5)
		{
			@Override
			protected void initElements()
			{}
		};
		window2.setVisible(true);
		window2.setSelectable(false);
		window2.setResizable(true);
		RectangularButton button2 = new RectangularButton(20, 20, 100, 30)
		{
			@Override
			public void onMouseClicked(float x, float y, int mouseButton)
			{ System.out.println("Pressed rectangle2 with " + mouseButton); }
		};
		button2.colourProperties().setColour(1, 0, 0, 1);
		button2.colourProperties().setMouseoverColour(0, 1, 0, 1);
		button2.colourProperties().setHasBorder(true);
		button2.colourProperties().setBorderColour(0, 1, 1, 1);
		button2.getTextComponent().setFontName(LWJGLResourceManager.instance().lib.getFont("default"));
		button2.getTextComponent().setText("Press me 2");
		button2.setPositionAbsolute(true);
		window2.addElement(button2);
		panel.addElement(window2);
		addElement(panel);
		RoundedRectangularTextBox textbox = new RoundedRectangularTextBox(0, 0, 300, 100, 5, 5)
		{
			@Override
			public void onSubmit(String text)
			{ System.out.println("Enter pressed on textbox"); }
		};
		textbox.colourProperties().setColour(0.8f, 0.8f, 0.8f, 0.8f);
		textbox.getTextComponent().setTextColour(0.1f, 0.0f, 0.0f, 1.0f);
		textbox.getTextComponent().setFontName(LWJGLResourceManager.instance().lib.getFont("default"));
		textbox.getTextComponent().setText(""
			+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
			+ "sed do eiusmod tempor incididunt ut labore et dolore magna.");
		//		+ "aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
		//		+ "ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
		textbox.setEditable(true);
		textbox.setReactToMouseOver(false);
		//panel.addElement(textbox);
		RoundedRectangularScrollArea scroll = new RoundedRectangularScrollArea(10, 10, 300, 200, 25, ScrollDirection.VERTICAL, 4);
		scroll.colourProperties().setColour(0.5f, 0.5f, 0.5f, 0.5f);
		scroll.addElement(textbox);
		//addElement(scroll);
		RectangularCheckBox box1 = new RectangularCheckBox(5, 30, 15, CheckBoxCheckStyle.TICK);
		RoundedRectangularCheckBox box2 = new RoundedRectangularCheckBox(5, 50, 15, 5, CheckBoxCheckStyle.CROSS);
		box1.colourProperties().setColour(new Vector4f(0.3f, 0.3f, 0.3f, 1.0f));
		box2.colourProperties().setColour(new Vector4f(0.3f, 0.3f, 0.3f, 1.0f));
		RadioButtonGroup group1 = new RadioButtonGroup();
		CircularRadioButton radio1 = new CircularRadioButton("test", 5, 70, 7.5f);
		radio1.colourProperties().setColour(new Vector4f(0.3f, 0.3f, 0.3f, 1.0f));
		CircularRadioButton radio2 = new CircularRadioButton("test2", 5, 90, 7.5f);
		radio2.colourProperties().setColour(new Vector4f(0.3f, 0.3f, 0.3f, 1.0f));
		CircularRadioButton radio3 = new CircularRadioButton("test3", 5, 110, 7.5f);
		radio3.colourProperties().setColour(new Vector4f(0.3f, 0.3f, 0.3f, 1.0f));
		group1.add(radio1);
		group1.add(radio2);
		group1.add(radio3);
		RoundedRectangularSolidProgressBar bar1 = new RoundedRectangularSolidProgressBar(340, 5, 5, 200, 3)
		{
			@Override
			public void update(float timestep)
			{ this.addProgress(0.1f * timestep); }
		};
		bar1.setFillStyle(ProgressFillStyle.DOWN);
		RectangularSolidProgressBar bar2 = new RectangularSolidProgressBar(5, 240, 340, 5)
		{
			@Override
			public void update(float timestep)
			{ this.addProgress(0.1f * timestep); }
		};
		PanelWindow window = new RoundedRectangularPanelWindow("", 5, 300, 350, 250, 5)
		{
			@Override
			protected void initElements()
			{}
		};
		window.setVisible(true);
		window.setSelectable(false);
		window.addElement(scroll);
		window.addElement(box1);
		window.addElement(box2);
		window.addElement(group1);
		window.addElement(bar1);
		window.addElement(bar2);
		window.setReactToMouseOver(false);
		window.setResizable(true);
		addElement(1, window);
		ImageHolder image = new ImageHolder("test_image", 500, 50, 100, 100);
		addElement(1, image);
	}

	@Override
	public void onUpdate(float timestep)
	{}
}
