package com.bwyap.engine.input;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.bwyap.utility.resource.JSONWrapper;

/** Provides a configurable mapping of user inputs loaded from an external JSON file
 * 
 * @author bwyap */
public final class InputMapping extends JSONWrapper
{
	private final Map<String, Integer> inputMapping;

	public InputMapping(JSONObject object)
	{
		super(object);
		inputMapping = new HashMap<String, Integer>();
		if (isValid())
		{
			JSONObject mouseBindings = ((JSONObject) object.get("mouse"));
			for (Object key : mouseBindings.keySet())
			{ setBinding(key.toString(), Integer.parseInt(String.valueOf(mouseBindings.get(key.toString())))); }
		}
	}

	/** Get the map of input bindings
	 * 
	 * @return */
	@SuppressWarnings("unchecked")
	public Map<String, String> getBindings()
	{ return (Map<String, String>) object; }

	/** Get the value of the specified binding.
	 * 
	 * @param  name
	 * @return */
	public int getBinding(String name)
	{
		if (inputMapping.get(name) == null) return 0;
		return inputMapping.get(name);
	}

	/** Set the value of the specified binding
	 * 
	 * @param bindingName
	 * @param value */
	public void setBinding(String bindingName, int value)
	{ inputMapping.put(bindingName, value); }

	@Override
	public boolean isValid()
	{
		try
		{
			if (Integer.parseInt(((JSONObject) object.get("mouse")).get("mouse_left").toString()) < 0) throw new Exception("Invalid mouse_left value");
			if (Integer.parseInt(((JSONObject) object.get("mouse")).get("mouse_right").toString()) < 0) throw new Exception("Invalid mouse_right value");
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			return false;
		}
		return true;
	}
}
