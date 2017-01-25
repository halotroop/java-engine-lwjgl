package com.bwyap.engine;

/**
 * Specifies the methods that an engine requires.
 * @author bwyap
 *
 */
public interface EngineInterface {

	/**
	 * Run the game engine loop.
	 */
	public void run();
	
	/**
	 * Initialise the engine and prepare resources for rendering
	 */
	public void init();
	
	/**
	 * Get the current system time
	 * @return
	 */
	public float getCurrentTime();
	
	/**
	 * Update the application
	 * @param timestep
	 */
	public void update(float timestep);
	
	/**
	 * Render the graphics of the application
	 * @param timestep
	 */
	public void render(float timestep);
	
	/**
	 * Frees resources that are no longer needed.
	 */
	public void cleanup();
	
	/**
	 * Get the last measured FPS of the engine.
	 * @return
	 */
	public float getFPS();
	
	/**
	 * Gets the target FPS of the game.
	 * @return
	 */
	public int getTargetFPS();
	
}
