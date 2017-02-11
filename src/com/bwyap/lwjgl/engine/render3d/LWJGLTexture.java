package com.bwyap.lwjgl.engine.render3d;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.bwyap.engine.render.Texture;
import com.bwyap.utility.resource.ResourceLoader;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;


/**
 * <p>
 * Holds a textureID of a texture loaded for LWJGL, and a flag 
 * to indicate whether the texture has alpha support. A texture 
 * can also be used as a texture atlas if its rows and columns 
 * are set in the constructor (by default it has a row and col 
 * count of 1, which means the texture is not animated). 
 * </p>
 * <p>
 * The class also provides a static method to load a texture 
 * to the graphics card using {@code PNGDecoder}.
 * </p>
 * @author bwyap
 *
 */
public class LWJGLTexture extends Texture {
	
	private static Map<String, LWJGLTexture> loadedTextures = new HashMap<String, LWJGLTexture>();
	
	protected boolean hasAlpha;

	
	public LWJGLTexture(int textureID, int rows, int cols) {
		super(textureID, rows, cols);
		this.hasAlpha = false;
	}
	
	
	public LWJGLTexture(int textureID) {
		this(textureID, 1, 1);
	}
	

	/**
	 * Set the hasAlpha flag for this texture.
	 * If alpha is supported for this texture, it should be 
	 * set to true for the renderer render with transparency.
	 * It is set to false by default.
	 * @param hasAlpha
	 */
	public void setAlpha(boolean hasAlpha) {
		this.hasAlpha = hasAlpha;
	}
	
	
	/**
	 * Check if the texture requires alpha transparency
	 * @return
	 */
	public boolean hasAlpha() {
		return hasAlpha;
	}
	
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
	
	
	public void cleanup() {
		glDeleteTextures(textureID);
	}
	

	/**
	 * Loads a texture atlas with the specified number of rows and columns
	 * @param name
	 * @param path
	 * @param row
	 * @param col
	 * @return
	 * @throws IOException 
	 */
	public static LWJGLTexture loadTexture(String name, String path, int row, int col) throws IOException {
		PNGDecoder decoder = new PNGDecoder(ResourceLoader.class.getResourceAsStream(path));
		
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		buf.flip();
		
		// Create a new OpenGL texture
		int textureID = glGenTextures();
		
		// Bind the textures
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		// Tell OpenGL how to unpack the RGBA bytes
		// Each component is 1 byte in size
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		
		// Upload the texture data
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); 
		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		// Generate mip map
		glGenerateMipmap(GL_TEXTURE_2D);
		
		// Put the texture in the list of loaded textures
		LWJGLTexture texture = new LWJGLTexture(textureID, row, col);
		loadedTextures.put(name, texture);
		
		return texture;
	}
	

	/**
	 * Loads a texture
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static LWJGLTexture loadTexture(String name, String path) throws IOException {
		return loadTexture(name, path, 1, 1);
	}
	
	
	/**
	 * Get the ID of a loaded texture.
	 * Returns null if the specified texture has not been loaded.
	 * @param name
	 * @return
	 */
	public static LWJGLTexture getTexture(String name) {
		return loadedTextures.get(name);
	}

}
