package com.bwyap.lwjgl.engine.gui.nanovg;

import static com.bwyap.engine.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.bwyap.engine.render.Texture;

/**
 * <p>
 * Holds a textureID of a texture loaded for NanoVG. A texture 
 * can also be used as a texture atlas if its rows and columns 
 * are set in the constructor (by default it has a row and col 
 * count of 1, which means the texture is not animated). 
 * </p>
 * <p>
 * Also provides a static method to load textures into memory 
 * for rendering by NanoVG. The rendering context must be 
 * provided for the loading process.
 * </p>
 * @author bwyap
 *
 */
public class NVGTexture extends Texture {
	
	private static Map<String, Integer> loadedTextures = new HashMap<String, Integer>();

	public NVGTexture(int textureID) {
		super(textureID);
	}
	
	
	public NVGTexture(int textureID, int rows, int cols) {
		super(textureID, rows, cols);
	}
	

	/**
	 * Loads a texture
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public static NVGTexture loadTexture(String name, String path, long ctx) throws IOException {
		ByteBuffer img = ioResourceToByteBuffer(path, 64*64);
		int textureID = nvgCreateImageMem(ctx, 0, img);
		loadedTextures.put(name, textureID);
		return new NVGTexture(textureID);
	}
	

	/**
	 * Loads a texture atlas with the specified number of rows and columns
	 * @param path
	 * @param row
	 * @param col
	 * @return
	 * @throws IOException
	 */
	public static NVGTexture loadTexture(String name, String path, long ctx, int row, int col) throws IOException {
		return new NVGTexture(loadTexture(name, path, ctx).getID(), row, col);
	}
	
	
	/**
	 * Get the ID of a loaded texture.
	 * Returns null if the specified texture has not been loaded.
	 * @param name
	 * @return
	 */
	public static int getTexture(String name) {
		return loadedTextures.get(name);
	}

}
