package com.bwyap.lwjgl.engine.gui.nanovg;

import static com.bwyap.engine.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/** Holds the name and path to a font that can be
 * used by NanoVG to render text using a .tff font
 * file. The font must be loaded into a byte buffer
 * using the {@code load()} command before it can
 * be used. */
public class NVGFont
{
	private static Map<String, NVGFont> fonts = new HashMap<String, NVGFont>();
	private final String path;
	private final String fontName;
	private ByteBuffer fontData;

	private NVGFont(String fontName, String path)
	{
		this.fontName = fontName;
		this.path = path;
	}

	/** Load the font data into a Byte Buffer.
	 * This is automatically called when the font
	 * is loaded via the static method {@code createFont}.
	 * 
	 * @throws IOException */
	private void load() throws IOException
	{ fontData = ioResourceToByteBuffer(path, 64 * 64); }

	/** Get the name of the font
	 * 
	 * @return */
	public String getName()
	{ return fontName; }

	/** Get the buffer containing the loaded font data.
	 * If the font has not yet been loaded this will be null.
	 * Use the method {@code load()} to load the font data.
	 * 
	 * @return */
	public ByteBuffer getBuffer()
	{ return fontData; }

	/* ==============
	 * STATIC METHODS
	 * ==============
	 */
	/** Create a font.
	 * The font is automatically added to
	 * NanoVGFont's static list of fonts.
	 * 
	 * @param  fontName
	 * @param  path
	 * @return
	 * @throws IOException */
	public static NVGFont createFont(String fontName, String path) throws IOException
	{
		NVGFont font = new NVGFont(fontName, path);
		font.load();
		fonts.put(fontName, font);
		return fonts.get(fontName);
	}

	/** Gets a font that has been previously created.
	 * If the font doesn't exist, null will be returned.
	 * 
	 * @param  fontName
	 * @return */
	public static NVGFont getFont(String fontName)
	{ return fonts.get(fontName); }

	/** Loads a specified font for the given NanoVG context.
	 * This must be called before a font can be used by a
	 * NanoVGGUI.
	 * 
	 * @param context
	 * @param fontName */
	public static void acquireFont(long context, String fontName)
	{ nvgCreateFontMem(context, getFont(fontName).getName(), getFont(fontName).getBuffer(), 0); }
}