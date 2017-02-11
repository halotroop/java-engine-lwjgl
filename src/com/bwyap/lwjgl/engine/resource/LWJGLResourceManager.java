package com.bwyap.lwjgl.engine.resource;

import java.io.File;
import java.io.IOException;

import com.bwyap.engine.resource.ResourceManagerBase;
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
	
	
	private static LWJGLResourceManager instance;
	
	
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
	
	
	public void loadNVGResources(long ctx) throws IOException {
		loadNVGFonts();
		loadNVGTextures(ctx);
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
	
	
	/* 
	 * ===============
	 * PRIVATE METHODS
	 * ===============
	 */
	
	
	// EXTERNAL
	
	private void loadFolders() {
		ResourceLoader.loadFolder(new File(lib.getFolders().get("data_folder")));
		ResourceLoader.loadFolder(new File(lib.getFolders().get("config_folder")));
	}
	
	
	private void loadConfig() {
		//TODO
		//
	}
	
	
	
	// INTERNAL
	
	private void loadShaders() {
		Shader.addSource("vertex", ResourceLoader.loadShaderFile(lib.getShaders().get("vertex")));
		Shader.addSource("fragment", ResourceLoader.loadShaderFile(lib.getShaders().get("fragment")));
		Shader.addSource("particle_vertex", ResourceLoader.loadShaderFile(lib.getShaders().get("particle_vertex")));
		Shader.addSource("particle_fragment", ResourceLoader.loadShaderFile(lib.getShaders().get("particle_fragment")));
	}
	
	
	private void loadMeshes() throws Exception {
		OBJLoader.loadInstancedMesh("particle", lib.getMeshes().get("particle"), 2000);
		OBJLoader.loadInstancedMesh("icube", lib.getMeshes().get("cube"), 64);
		OBJLoader.loadMesh("bunny", lib.getMeshes().get("bunny"));
		OBJLoader.loadMesh("cube", lib.getMeshes().get("cube"));
	}
	
	
	private void loadLWJGLTextures() throws IOException {
		LWJGLTexture.loadTexture("testTexture", lib.getTextures().get("test"));
		LWJGLTexture.loadTexture("testParticle", lib.getTextures().get("test_particle"));
		LWJGLTexture.loadTexture("testAnimatedParticle", lib.getTextures().get("test_animated_particle"), 4, 4);
	}
	

	private void loadNVGFonts() throws IOException {
		NVGFont.createFont("aeromatics_reg", lib.getFonts().get("aeromatics_reg"));
	}
	
	
	private void loadNVGTextures(long ctx) throws IOException{
		NVGTexture.loadTexture("test_button", lib.getTextures().get("test_button"), ctx);
		NVGTexture.loadTexture("test_button_mouseover", lib.getTextures().get("test_button_mouseover"), ctx);
		NVGTexture.loadTexture("test_button_pressed", lib.getTextures().get("test_button_pressed"), ctx);
	}
	
	
}
