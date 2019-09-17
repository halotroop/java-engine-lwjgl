package com.bwyap.engine.resource;

import java.util.Map;

import org.json.simple.JSONObject;

import com.bwyap.utility.resource.JSONWrapper;

/** A JSON wrapper that provides access to the resource file
 * paths specified by a Resource library JSON file.
 * 
 * @author bwyap */
@SuppressWarnings("unchecked")
public class JSONResourceLibrary extends JSONWrapper
{
	/** Create a new JSON Resource library with the specified JSON object
	 * 
	 * @param object */
	public JSONResourceLibrary(JSONObject object)
	{ super(object); }

	/** Auxillary method for getting internal resource paths
	 * 
	 * @param  property
	 * @return */
	private Map<String, String> getInternal(String property)
	{ return (Map<String, String>) ((JSONObject) object.get("internal")).get(property); }

	/** Auxillary method for getting external resource paths
	 * 
	 * @param  property
	 * @return */
	private Map<String, String> getExternal(String property)
	{ return (Map<String, String>) ((JSONObject) object.get("external")).get(property); }

	// INTERNAL
	/** Get the Map of shader file paths specified by the resource library
	 * 
	 * @return */
	public Map<String, String> getShaders()
	{ return getInternal("shaders"); }

	/** Get the Map of mesh file paths specified by the resource library
	 * 
	 * @return */
	public Map<String, String> getMeshes()
	{ return getInternal("meshes"); }

	/** Get the Map of texture file paths specified by the resource library
	 * 
	 * @return */
	public Map<String, String> getTextures()
	{ return getInternal("textures"); }

	/** Get the Map of font file paths specified by the resource library
	 * 
	 * @return */
	public Map<String, String> getFonts()
	{ return getInternal("fonts"); }

	// EXTERNAL
	/** Get the external folder paths specified by the resource library
	 * 
	 * @return */
	public Map<String, String> getFolders()
	{ return getExternal("folders"); }

	/** Get the configuration file paths specified by the resource library
	 * 
	 * @return */
	public Map<String, String> getConfig()
	{ return getExternal("config"); }

	// RESOURCE MAPPINGS
	/** Get a font from the font library
	 * 
	 * @param  fontAlias
	 * @return */
	public String getFont(String fontAlias)
	{ return ((JSONObject) ((JSONObject) ((JSONObject) object).get("mapping")).get("font")).get(fontAlias).toString(); }

	/** Validate the loaded JSON file.
	 * 
	 * @return */
	@Override
	public boolean isValid()
	{
		try
		{
			//TODO
			//
			//
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
