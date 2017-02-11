package com.bwyap.lwjgl.window;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowAspectRatio;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.bwyap.engine.input.InputHandler;
import com.bwyap.engine.window.Window;
import com.bwyap.lwjgl.engine.resource.LWJGLResourceManager;
import com.bwyap.lwjgl.window.input.GLFWKeyboardHandler;
import com.bwyap.lwjgl.window.input.GLFWMouseHandler;

/**
 * A GLFW window with input handlers.
 * The {@code start()} method will start a loop which call the 
 * {@code update} and {@code render} methods on a {@GLFWGame} object.
 * <p>
 * The method {@code createGame()} method should be overridden to
 * load a more specific and concrete implementation of GLFWGame.
 * 
 * @author bwyap
 *
 */
public abstract class GLFWWindow extends Window {

	private long window;
	private boolean vSync;
	private boolean polygonMode;
	
	private GLFWKeyboardHandler keyboardHandler;
	private GLFWMouseHandler mouseHandler;
	
	// Callbacks
	GLFWErrorCallback		errorCallback;
	GLFWWindowSizeCallback	windowSizeCallback;
	GLFWKeyCallback			keyCallback;
	GLFWMouseButtonCallback mouseButtonCallback;
	GLFWCursorPosCallback	cursorPosCallback;
	GLFWScrollCallback		scrollCallback;
	GLFWCursorEnterCallback	cursorEnterCallback;
	
	
	/**
	 * A GLFW window.
	 * @param width
	 * @param height
	 */
	public GLFWWindow(int width, int height, String title, boolean vSync, boolean polygonMode, boolean showFps) {
		super(width, height, title, showFps);
		this.vSync = vSync;
		this.polygonMode = polygonMode;
	}
	
	
	@Override
	public void init() {
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit()) 
			throw new IllegalStateException("Unable to initialize GLFW");
		
		//Configure the window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		//Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)	
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Initialise the callbacks for the window
		initCallbacks();
		
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		// Center our window
		glfwSetWindowPos(window,
			(vidmode.width() - width) / 2,
			(vidmode.height() - height) / 2
		);

		glfwMakeContextCurrent(window);	// Make the OpenGL context current
		
		if (vSync) {			
			glfwSwapInterval(1);			// Enable v-sync	
		}
		
		glfwShowWindow(window);			// Make the window visible
		GL.createCapabilities();
		loadGLRequiredResources();
		
		//Create a new engine to run
		try {
			engine = createEngine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setClearColour(0, 0, 0, 0);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_STENCIL_TEST);
		
		if (polygonMode) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	
	
	@Override
	public boolean shouldClose() {
		return glfwWindowShouldClose(this.getID());
	}
	
	
	@Override
	public void processEvents() {
		glfwPollEvents();
	}
	
	
	@Override
	public void swapDisplayBuffers() {
		glfwSwapBuffers(this.getID());
	}
	
	
	/**
	 * Load resources which require GL capabilities to be initialised first.
	 * This method is automatically called.
	 */
	public abstract void loadGLRequiredResources();
	
	
	/**
	 * Initialise the callbacks used by this window.
	 */
	protected void initCallbacks() {
		// Setup an error callback. 
		// The default implementation will print the error message in System.err.
		errorCallback = GLFWErrorCallback.createPrint(System.err).set();
		
		// Setup window callback
		glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				GLFWWindow.this.width = width;
				GLFWWindow.this.height = height;
				GLFWWindow.this.setResized(true);
				glfwSetWindowAspectRatio(window, LWJGLResourceManager.instance().settings().getWidth(), LWJGLResourceManager.instance().settings().getHeight());
			}
		});
				
		// Create input handlers
		keyboardHandler = new GLFWKeyboardHandler();
		mouseHandler = new GLFWMouseHandler();
		input = new InputHandler(keyboardHandler, mouseHandler);
		
		// Setup key callback
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keyboardHandler.invoke(window, key, scancode, action, mods);
			}
		});
		
		// Setup mouse callbacks
		glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				mouseHandler.invokeMouseButtonCallback(window, button, action, mods);
			}
		});
		
		glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				mouseHandler.invokeScrollCallback(window, xoffset, yoffset);
			}
		});

		glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				mouseHandler.invokeCursorPosCallback(window, xpos, ypos);
			}			
		});
		
		glfwSetCursorEnterCallback(window, cursorEnterCallback = new GLFWCursorEnterCallback() {
			@Override
			public void invoke(long window, boolean entered) {
				mouseHandler.invokeCursorEnterCallback(window, entered);				
			}
		});
	
	}
	
	
	@Override
	public final void start() {
		// Initialise the window
		init();
		
		// Run the engine
		engine.run();
		
		// Dispose the window
		dispose();
	}

	
	@Override
	public void dispose() {
		glfwFreeCallbacks(window);
		glfwSetErrorCallback(null).free();
		
		// Destroy the window
		glfwDestroyWindow(window);

		glfwTerminate();
	}
	
	
	/**
	 * Sets the clear colour for the window
	 * @param r
	 * @param g
	 * @param b
	 * @param alpha
	 */
	public void setClearColour(float r, float g, float b, float alpha) {
		glClearColor(r, g, b, alpha);
	}
	
	
	/**
	 * Get the window handle
	 * @return
	 */
	public long getID() {
		return window;
	}
	
	
	@Override
	protected void setWindowTitle(String title) {
		glfwSetWindowTitle(window, title);
	}
	
	
	/**
	 * Checks if vSync is enabled for this window
	 * @return
	 */
	public boolean isVSync() {
		return vSync;
	}
	
}
