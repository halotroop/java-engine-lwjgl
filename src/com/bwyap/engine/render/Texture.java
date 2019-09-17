package com.bwyap.engine.render;

/** <p>
 * An abstract Texture object that holds the ID a loaded texture.
 * A texture can also be used as a texture atlas if its rows and
 * columns are set in the constructor (by default it has a row
 * and col count of 1, which means the texture is not animated).
 * </p>
 * 
 * @author bwyap */
public abstract class Texture
{
	protected final int textureID;
	protected int rows;
	protected int cols;

	public Texture(int textureID)
	{
		this.textureID = textureID;
		this.rows = 1;
		this.cols = 1;
	}

	public Texture(int textureID, int rows, int cols)
	{
		this.textureID = textureID;
		this.rows = rows;
		this.cols = cols;
	}

	/** Get the ID of the texture
	 * 
	 * @return */
	public int getID()
	{ return textureID; }

	/** Get the number of rows this texture has if it is a texture atlas.
	 * 
	 * @return */
	public int getRows()
	{ return rows; }

	/** Get the number of columns this texture has if it is a texture atlas.
	 * 
	 * @return */
	public int getCols()
	{ return cols; }
}
