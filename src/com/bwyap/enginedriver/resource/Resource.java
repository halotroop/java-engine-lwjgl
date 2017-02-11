package com.bwyap.enginedriver.resource;

import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;

import com.bwyap.engine.input.InputMapping;
import com.bwyap.enginedriver.resource.jsonwrapper.Settings;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGFont;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGTexture;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;
import com.bwyap.lwjgl.mesh.OBJLoader;
import com.bwyap.utility.resource.ResourceLoader;

/**
 * The Resource class is responsible for the loading of all resources required for the game and 
 * contains paths and file names for all resource files.
 * 
 * @author bwyap
 *
 */
public class Resource {	
	
	
	public static final String IN_ROOT = "/com/bwyap/enginedriver/";
	
	/* ==============
	 * Internal paths
	 * ==============
	 */
	public static final String IN_RESPATH = IN_ROOT + "resource/defaultcontent/";
	
	// shaders
	public static final String IN_SHADER_PATH = "/com/bwyap/lwjgl/engine/render3d/shader/shaders/";
	public static final String IN_SHADER_VERTEX = "vertexShader.vs";
	public static final String IN_SHADER_FRAGMENT = "fragmentShader.fs";
	public static final String IN_SHADER_UNLIT_VERTEX = "vertexShaderUnlit.vs";
	public static final String IN_SHADER_UNLIT_FRAGMENT = "fragmentShaderUnlit.fs";
	public static final String IN_SHADER_PARTICLE_VERTEX = "particleVertexShader.vs";
	//public static final String IN_SHADER_PARTICLE_GEOMETRY = "particleGeometryShader.gs";
	public static final String IN_SHADER_PARTICLE_FRAGMENT = "particleFragmentShader.fs";
	
	// object meshes
	private static final String IN_MESH_CUBE = Resource.IN_ROOT + "resource/obj/cube.obj";
	private static final String IN_MESH_BUNNY = Resource.IN_ROOT + "resource/obj/bunny.obj";
	private static final String IN_MESH_PARTICLE = Resource.IN_ROOT + "resource/obj/particle.obj";

	/* ==============
	 * External files
	 * ==============
	 */
	private static final File EX_DATAFOLDER = new File("data/");
	private static final File EX_CONFIGFOLDER = new File("data/config");
	private static final File EX_DOMINATIONCONFIG_JSON = new File("data/config/config.json");
	
	/* ==========
	 * JSON files
	 * ==========
	 */
	public static Settings Settings;
	
	/* =======
	 * Shaders
	 * =======
	 */
	public static String vertexShaderCode;
	public static String fragmentShaderCode;
	public static String particleVertexShaderCode;
	public static String particleGeometryShaderCode;
	public static String particleFragmentShaderCode;
	
	/* ========
	 * Textures
	 * ========
	 */
	private static final String TEX_TEST = IN_ROOT + "resource/textures/texture.png"; 
	private static final String TEX_PARTICLE_TEST = IN_ROOT + "resource/textures/particle_smooth.png"; 
	private static final String TEX_ANIM_PARTICLE_TEST = IN_ROOT + "resource/textures/particle_anim.png";
	//public static final String TEX_PARTICLE_TEST = IN_ROOT + "resource/textures/particle_tmp.png"; 
	private static final String TEX_TEST_BUTTON = IN_ROOT + "resource/textures/button_normal.png";
	private static final String TEX_TEST_BUTTON_MOUSEOVER = IN_ROOT + "resource/textures/button_mouseover.png";
	private static final String TEX_TEST_BUTTON_PRESSED = IN_ROOT + "resource/textures/button_pressed.png";
	
	public static LWJGLTexture testTexture;
	public static LWJGLTexture testParticleTexture;
	public static LWJGLTexture testAnimatedParticleTexture;
	
	public static NVGTexture testButton;
	public static NVGTexture testButtonMouseover;
	public static NVGTexture testButtonPressed;
	
	/* ========
	 * Fonts
	 * ========
	 */
	
	public static final String defaultFont = "aeromatics_reg";
	
	/* =============
	 * Input mapping
	 * =============
	 */
	
	public static InputMapping inputMapping;
	
	
	/*
	 * ==============
	 * PUBLIC METHODS
	 * ==============
	 */
	
	/**
	 * Load all resources.
	 */
	public static void load() {
		loadFolders();
		loadShaders();
		loadConfig();
		loadInputMapping();
		try {			
			loadFonts();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//TODO
		//		
	}
	
	
	/* ===============================
	 * GL CAPABILITY dependent methods
	 * ===============================
	 */
	
	/**
	 * Loads textures to the graphics card.
	 * This needs to be done after {@code GL.createCapabilities()} has been called.
	 */
	public static void loadLWJGLTextures() {
		try {
			testTexture = LWJGLTexture.loadTexture("testTexture", TEX_TEST);
			testParticleTexture = LWJGLTexture.loadTexture("testParticle", TEX_PARTICLE_TEST);
			testAnimatedParticleTexture = LWJGLTexture.loadTexture("testAnimatedParticle", TEX_ANIM_PARTICLE_TEST, 4, 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Loads meshes and stores them in the static mesh map in the {@code Mesh} class.
	 * This needs to be done after {@code GL.createCapabilities()} has been called.
	 */
	public static void loadMeshes() {
		try {
			OBJLoader.loadInstancedMesh("particle", Resource.IN_MESH_PARTICLE, 2000);
			OBJLoader.loadInstancedMesh("icube", Resource.IN_MESH_CUBE, 64);
			OBJLoader.loadMesh("bunny", Resource.IN_MESH_BUNNY);
			OBJLoader.loadMesh("cube", Resource.IN_MESH_CUBE);
		}
		catch (Exception e) {
			
		}
	}	
	
	
	/**
	 * Load NVG textures for GUI rendering into the current NanoVG context
	 */
	public static void loadNVGTextures(long ctx) {
		try {
			testButton = NVGTexture.loadTexture("testButton", TEX_TEST_BUTTON, ctx);
			testButtonMouseover = NVGTexture.loadTexture("testButtonMouseover", TEX_TEST_BUTTON_MOUSEOVER, ctx);
			testButtonPressed = NVGTexture.loadTexture("testButtonPressed", TEX_TEST_BUTTON_PRESSED, ctx);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/* ===============
	 * PRIVATE METHODS
	 * ===============
	 */
	
	/**
	 * Loads all folders required for the game content.
	 * If required directories do not exist, they are created.
	 */
	private static void loadFolders() {
		ResourceLoader.loadFolder(EX_DATAFOLDER);
		//ResourceLoader.loadFolder(EX_SHADERFOLDER);
		ResourceLoader.loadFolder(EX_CONFIGFOLDER);
		//TODO
		//
	}	
	
	
	/**
	 * Load any required fonts
	 * @throws IOException
	 */
	private static void loadFonts() throws IOException {
		NVGFont.createFont(defaultFont, "/com/bwyap/enginedriver/resource/font/aeromatics_reg.ttf");
		
	}
	
	
	/**
	 * Load the shader code from files
	 */
	private static void loadShaders() {
		vertexShaderCode = ResourceLoader.loadShaderFile(Resource.IN_SHADER_PATH + IN_SHADER_VERTEX);
		fragmentShaderCode = ResourceLoader.loadShaderFile(Resource.IN_SHADER_PATH + IN_SHADER_FRAGMENT);

		particleVertexShaderCode = ResourceLoader.loadShaderFile(Resource.IN_SHADER_PATH + IN_SHADER_PARTICLE_VERTEX);
		//particleGeometryShaderCode = ResourceLoader.loadShaderFile(Resource.IN_SHADER_PATH + IN_SHADER_PARTICLE_GEOMETRY);
		particleFragmentShaderCode = ResourceLoader.loadShaderFile(Resource.IN_SHADER_PATH + IN_SHADER_PARTICLE_FRAGMENT);
	}
	
	
	/**
	 * Load all config files
	 */
	private static void loadConfig() {
		Settings = new Settings(ResourceLoader.loadJSON(EX_DOMINATIONCONFIG_JSON));
		if (!Settings.isValid()) {
			ResourceLoader.copyFileFromJar(EX_DOMINATIONCONFIG_JSON, null);
			Settings = new Settings(ResourceLoader.loadJSON(EX_DOMINATIONCONFIG_JSON));
		}
		
		//TODO
		//
	}
	
	
	/**
	 * Initialise the input mapping for this application.
	 */
	private static void loadInputMapping() {
		inputMapping = new InputMapping();
		//TODO replace this to read a json config file
		inputMapping.setBinding(InputMapping.MOUSE_LEFT, GLFW.GLFW_MOUSE_BUTTON_1);
		inputMapping.setBinding(InputMapping.MOUSE_RIGHT, GLFW.GLFW_MOUSE_BUTTON_2);
	}
	
	
	/**
	 * Clean up resources.
	 */
	public static void cleanup() {
		//TODO clean up textures
	}
	
}
