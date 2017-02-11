package com.bwyap.engine.resource;

import com.bwyap.utility.resource.ResourceLoader;

/**
 * A base class for a Resource manager. Each implementation
 * of this engine should have its own subclass of this class.
 * The ResourceManager should be implemented as a Singleton.
 * @author bwyap
 *
 */
public abstract class ResourceManagerBase implements ResourceManagerInterface {
	
	
	protected final JSONResourceLibrary lib;
	
	
	/**
	 * Create a new resource manager with the 
	 * resource library specified at the path
	 * @param JSONPath
	 */
	protected ResourceManagerBase(String JSONPath) {
		lib = new JSONResourceLibrary(ResourceLoader.loadInternalJSON(JSONPath));
	}
	
	
}
