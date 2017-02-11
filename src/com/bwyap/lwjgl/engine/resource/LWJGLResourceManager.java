package com.bwyap.lwjgl.engine.resource;

import java.io.File;
import java.io.IOException;

import com.bwyap.engine.input.InputMapping;
import com.bwyap.engine.resource.ResourceManagerBase;
import com.bwyap.enginedriver.resource.jsonwrapper.Settings;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGFont;
import com.bwyap.lwjgl.engine.gui.nanovg.NVGTexture;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;
import com.bwyap.lwjgl.engine.render3d.shader.Shader;
import com.bwyap.lwjgl.mesh.OBJLoader;
import com.bwyap.utility.resource.ResourceLoader;


/**
 * A resource manager for the LWJGL engine. 
 * This class is a Singleton and its instance should be called using the method {@code instance()}.
 * The instance must first be initialised through the method {@code initInstance()}.
 * @author bwyap
 *
 */
public class LWJGLResourceManager extends ResourceManagerBase {
	
	public static final String RESOURCE_JSON = "/com/bwyap/engine/resource/resources.json";
	public static final String DEFAULT_INTERNAL_PATH = "/com/bwyap/enginedriver/resource/defaultcontent/";
	
	private static LWJGLResourceManager instance;
	
	
	private Settings settings;
	private InputMapping inputMapping;
	
	
	/**
	 * Get the resource manager instance
	 * @return
	 */
	public static LWJGLResourceManager instance() {
		return instance;
	}
	
	
	/**
	 * Initialise the resource manager instance with 
	 * the resource library at the given path
	 * @param JSONPath
	 */
	public static void initInstance(String JSONPath) {
		instance = new LWJGLResourceManager(JSONPath);
	}
	
	
	protected LWJGLResourceManager(String JSONPath) {
		super(JSONPath);
	}
	
	
	@Override
	public void loadIndepedentResources() throws IOException {
		loadFolders();
		loadConfig();
		loadShaders();
	}
	
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * This method requires that LWJGL's {@code GL.createCapabilities()} method be called.
	 * </p> 
	 */
	@Override
	public void loadDependentResources() throws Exception {
		loadMeshes();
		loadLWJGLTextures();
	}
	
	
	/**
	 * Load resources for the GUI renderer (through NanoVG)
	 * @param ctx
	 * @throws IOException
	 */
	public void loadNVGResources(long ctx) throws IOException {
		loadNVGFonts();
		loadNVGTextures(ctx);
	}
	
	
	/**
	 * Get the settings object
	 * @return
	 */
	public Settings settings() {
		return settings;
	}
	
	
	/**
	 * Get the input mapping object
	 * @return
	 */
	public InputMapping inputMapping() {
		return inputMapping;
	}
	
	
	
	/* 
	 * ===============
	 * PRIVATE METHODS
	 * ===============
	 */
	
	
	// EXTERNAL
	
	/**
	 * Load folders for local data
	 */
	private void loadFolders() {
		ResourceLoader.loadFolder(new File(lib.getFolders().get("data_folder")));
		ResourceLoader.loadFolder(new File(lib.getFolders().get("config_folder")));
	}
	
	
	/**
	 * Load configuration files
	 */
	private void loadConfig() {
		File configFile = new File(lib.getConfig().get("config_file"));
		settings = new Settings(ResourceLoader.loadJSON(configFile));
		if (!settings.isValid()) {
			ResourceLoader.copyFileFromJar(configFile, null);
			settings = new Settings(ResourceLoader.loadJSON(configFile));
		}
		
		File inputMappingFile = new File(lib.getConfig().get("input_file"));
		inputMapping = new InputMapping(ResourceLoader.loadJSON(inputMappingFile));
		if (!inputMapping.isValid()) {
			ResourceLoader.copyFileFromJar(inputMappingFile, null);
			inputMapping = new InputMapping(ResourceLoader.loadJSON(inputMappingFile));
		}
	}
	
	
	
	// INTERNAL
	
	/**
	 * Load shader code into memory. The shader code can be retrieved 
	 * from the {@code Shader} class to create and link the shader.
	 */
	private void loadShaders() {
		Shader.addSource("vertex", ResourceLoader.loadShaderFile(lib.getShaders().get("vertex")));
		Shader.addSource("fragment", ResourceLoader.loadShaderFile(lib.getShaders().get("fragment")));
		Shader.addSource("particle_vertex", ResourceLoader.loadShaderFile(lib.getShaders().get("particle_vertex")));
		Shader.addSource("particle_fragment", ResourceLoader.loadShaderFile(lib.getShaders().get("particle_fragment")));
	}
	
	
	/**
	 * Load meshes from their OBJ files into memory
	 * @throws Exception
	 */
	private void loadMeshes() throws Exception {
		OBJLoader.loadInstancedMesh("particle", lib.getMeshes().get("particle"), 2000);
		OBJLoader.loadInstancedMesh("icube", lib.getMeshes().get("cube"), 64);
		OBJLoader.loadMesh("bunny", lib.getMeshes().get("bunny"));
		OBJLoader.loadMesh("cube", lib.getMeshes().get("cube"));
	}
	
	
	/**
	 * Load mesh textures
	 * @throws IOException
	 */
	private void loadLWJGLTextures() throws IOException {
		LWJGLTexture.loadTexture("test", lib.getTextures().get("test"));
		LWJGLTexture.loadTexture("test_particle", lib.getTextures().get("test_particle"));
		LWJGLTexture.loadTexture("test_animated_particle", lib.getTextures().get("test_animated_particle"), 4, 4);
	}
	
	
	/**
	 * Load fonts for GUI rendering
	 * @throws IOException
	 */
	private void loadNVGFonts() throws IOException {
		NVGFont.createFont("aeromatics_reg", lib.getFonts().get("aeromatics_reg"));
	}
	
	
	/**
	 * Load textures for GUI rendering
	 * @param ctx
	 * @throws IOException
	 */
	private void loadNVGTextures(long ctx) throws IOException{
		NVGTexture.loadTexture("test_button", lib.getTextures().get("test_button"), ctx);
		NVGTexture.loadTexture("test_button_mouseover", lib.getTextures().get("test_button_mouseover"), ctx);
		NVGTexture.loadTexture("test_button_pressed", lib.getTextures().get("test_button_pressed"), ctx);
	}
	
}
