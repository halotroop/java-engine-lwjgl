package com.bwyap.engine;

import com.bwyap.engine.window.WindowInterface;

/** An Engine implementation which uses a fixed time loop.
 * This class should be extended to provide implementations
 * for the {@code update} and {@code render} methods.
 * 
 * @author bwyap */
public abstract class FixedTimestepEngine extends Engine
{
	public FixedTimestepEngine(WindowInterface window, int targetFPS) throws Exception
	{ super(window, targetFPS); }

	/** Run the engine loop using a fixed timestep.
	 * This method should not be altered. */
	@Override
	public final void run()
	{
		float now, delta;
		float last = 0f, fpsTimer = 0f, secsPerUpdate = 1f / targetFPS;
		int fps = 0;
		init();
		while (!window.shouldClose())
		{
			now = this.getCurrentTime();
			delta = now - last;
			last = now;
			window.processEvents();
			update(delta);
			render(delta);
			window.swapDisplayBuffers();
			fps++;
			fpsTimer += delta;
			window.setFpsDisplay(fps / fpsTimer);
			if (fpsTimer >= 1.0f)
			{
				this.fps = fps;
				fpsTimer -= 1.0f;
				fps = 0;
			}
			while ((float) this.getCurrentTime() < now + secsPerUpdate)
			{
				// wait 
			}
		}
		cleanup();
	}
}
// Old method
/*
public final void run() {
		float now, delta;
		@SuppressWarnings("unused")
		float last = 0f, steps = 0f, fpsTimer = 0f;
		float secsPerUpdate = 1f/targetFPS;
		int fps = 0;
		
		init();
		
		while (!window.shouldClose()) {
			now = this.getCurrentTime();
			delta = now - last;
			last = now;
			steps += delta;
			
			window.processEvents();
			
			float d = delta;
			float n = now;
			float l = last;
			while (steps >= secsPerUpdate) {			
				update(d);
				n = (float) glfwGetTime();
				d = n - l;
				l = n;
				steps -= secsPerUpdate;
			}
			
			update(delta);
			render(delta);
			window.swapDisplayBuffers();
			
			fps++;
			fpsTimer += delta;
			
			window.setFpsDisplay(fps/fpsTimer);
			
			if (fpsTimer >= 1.0f) {
				this.fps = fps;
				fpsTimer -= 1.0f;
				fps = 0;
			}
			
			while ((float) this.getCurrentTime() < now + secsPerUpdate) {
				// wait 
			}
		}
		
		cleanup();
	}
*/
