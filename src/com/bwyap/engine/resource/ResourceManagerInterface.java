package com.bwyap.engine.resource;

/**
 * Methods required by a concrete Resource Manager implementation
 * @author bwyap
 *
 */
public interface ResourceManagerInterface {
	
	
	/**
	 * Load all non-engine dependent resources
	 */
	public void loadIndepedentResources() throws Exception;
	
	
	/**
	 * Load all engine dependent resources
	 */
	public void loadDependentResources() throws Exception;
	
	
}
