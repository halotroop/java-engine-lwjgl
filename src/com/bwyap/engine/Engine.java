package com.bwyap.engine;

import com.bwyap.engine.window.WindowInterface;

/**
 * An abstract Engine that holds a {@code WindowInterface}.
 * This class should be extended to implement the main engine
 * loop which calls the {@code update} and {@code render} 
 * methods.
 * @author bwyap
 *
 */
public abstract class Engine implements EngineInterface {
	
	protected final WindowInterface window;
	
	protected int targetFPS;
	protected float fps;
	
	
	public Engine(WindowInterface window, int targetFPS) throws Exception {
		this.window = window;
		this.targetFPS = targetFPS;
	}
	
	@Override
	public float getFPS() {
		return fps;
	}
	
	@Override
	public int getTargetFPS() {
		return targetFPS;
	}
	
}
