package com.bwyap.lwjgl.engine.entity;

import com.bwyap.engine.render3d.entity.RotatableEntity;
import com.bwyap.lwjgl.engine.render3d.Material;
import com.bwyap.lwjgl.engine.render3d.LWJGLTexture;

/** <p>
 * A 3D entity that can be rendered to the screen.
 * Stores a mesh name, as well as a texture and
 * material with which to render the mesh.
 * Extends {@code RotatableEntity} which provide
 * methods to move, scale and rotate the entity.
 * </p>
 * <p>
 * Implements the following interfaces:
 * {@code EntityInterface},
 * {@code ScalableInterface},
 * {@code MovableInterface} and
 * {@code RotatableInterface}.
 * </p>
 * 
 * @author bwyap */
public class RenderableEntity extends RotatableEntity
{
	private final String meshName;
	private LWJGLTexture texture;
	private Material material;

	public RenderableEntity(String meshName)
	{ this.meshName = meshName; }

	/** Update the object's state.
	 * TODO make this method abstract
	 * 
	 * @param timestep */
	public void update(float timestep)
	{}

	/** Get the name of the mesh used by this object
	 * 
	 * @return */
	public String getMeshName()
	{ return meshName; }

	/** Get the texture used by this object
	 * 
	 * @return */
	public LWJGLTexture getTexture()
	{ return texture; }

	/** Check if the object has a texture to use
	 * 
	 * @return */
	public boolean isTextured()
	{ return texture != null; }

	/** Set the texture used by this object
	 * 
	 * @param texture */
	public void setTexture(LWJGLTexture texture)
	{ this.texture = texture; }

	/** Get the material used by this object
	 * 
	 * @return */
	public Material getMaterial()
	{ return material; }

	/** Set the material used by this object
	 * 
	 * @param material */
	public void setMaterial(Material material)
	{ this.material = material; }
}
