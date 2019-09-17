package com.bwyap.engine.render3d.entity;

/** Contains methods that an entity that is scalable
 * on the x, y and z axis should implement.
 * 
 * @author bwyap */
public interface ScalableInterface
{
	/** Get the x scale of which the model of the entity will be rendered.
	 * 
	 * @return */
	public float getScaleX();

	/** Get the y scale of which the model of the entity will be rendered.
	 * 
	 * @return */
	public float getScaleY();

	/** Get the z scale of which the model of the entity will be rendered.
	 * 
	 * @return */
	public float getScaleZ();

	/** Set the scale at which the model of the entity will be rendered.
	 * 
	 * @param scale */
	public void setScale(float scale);

	/** Set the x scale at which the model of the entity will be rendered.
	 * 
	 * @param scale */
	public void setScaleX(float xScale);

	/** Set the y scale at which the model of the entity will be rendered.
	 * 
	 * @param scale */
	public void setScaleY(float yScale);

	/** Set the z scale at which the model of the entity will be rendered.
	 * 
	 * @param scale */
	public void setScaleZ(float zScale);
}
