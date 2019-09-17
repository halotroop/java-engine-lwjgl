package com.bwyap.enginedriver.game.test;

import com.bwyap.engine.gui.GUIController;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGGUI;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGRenderer;

public class TestGUIController extends GUIController
{
	private NVGGUI gui;

	public TestGUIController() throws Exception
	{
		super(new NVGRenderer());
		//gui = new TestGUI();
		//((TestGUI)gui).init((NVGRenderer)renderer);
		gui = new TestGUI();
		gui.init((NVGRenderer) renderer);
		this.setGUI(gui);
	}
}
