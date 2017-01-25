package com.bwyap.enginedriver.resource.jsonwrapper;

import org.json.simple.JSONObject;

import com.bwyap.utility.resource.JSONWrapper;

/**
 * A wrapper class for the settings config file.
 * Allows easy access to the different properties stored in the config file.
 * @author bwyap
 *
 */
public class Settings extends JSONWrapper {
	
	public Settings(JSONObject object) {
		super(object);
	}

	
	/**
	 * Gets the target fps 
	 * @return
	 */
	public int getFPS() {
		return getInteger(object, "fps");
	}
	
	
	/**
	 * Get the default width of the game window
	 * @return
	 */
	public int getWidth() {
		return getInteger((JSONObject) object.get("window"), "width");
	}
	
	
	/**
	 * Get the default height of the game window
	 * @return
	 */
	public int getHeight() {
		return getInteger((JSONObject) object.get("window"), "height");
	}
	
	
	/**
	 * Check whether the window should enable vsync
	 * @return
	 */
	public boolean isVSync() {
		return getBoolean(object, "vsync");
	}
	
	
	/**
	 * Check whether the window should render the GUI using antialiasing
	 * @return
	 */
	public boolean isAntiAlias() {
		return getBoolean(object, "antialias");
	}
	
	
	/**
	 * Check whether the renderer should render in polygon mode
	 * @return
	 */
	public boolean usePolygonMode() {
		return getBoolean(object, "polygon_mode");
	}
	
	
	/**
	 * Check whether the window should show the fps
	 * @return
	 */
	public boolean showFpsWindow() {
		return getBoolean(object, "showfps_window");
	}
	
	
	/**
	 * Validate the loaded settings file.
	 * @return
	 */
	@Override
	public boolean isValid() {
		try {
			if (getFPS() < 0) throw new Exception("fps must be a positive integer.");
			getWidth();
			getHeight();
			isVSync();
			isAntiAlias();
			usePolygonMode();
			showFpsWindow();
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
		return true;
	}
	
	
}
