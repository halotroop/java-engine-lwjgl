package com.bwyap.engine.render3d;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.bwyap.engine.render3d.entity.Entity;
import com.bwyap.engine.render3d.entity.RotatableEntity;

/** Handles the transformation matrices used to render entities in a 3D scene.
 * 
 * @author bwyap */
public class Transformation
{
	private final Matrix4f projectionMatrix;
	private final Matrix4f modelMatrix;
	private final Matrix4f modelViewMatrix;
	//private final Matrix4f modelLightViewMatrix;
	private final Matrix4f viewMatrix;
	//private final Matrix4f lightViewMatrix;
	//private final Matrix4f orthoProjMatrix;
	//private final Matrix4f ortho2DMatrix;
	//private final Matrix4f orthoModelMatrix;

	public Transformation()
	{
		projectionMatrix = new Matrix4f();
		modelMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
		//modelLightViewMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		//orthoProjMatrix = new Matrix4f();
		//ortho2DMatrix = new Matrix4f();
		//orthoModelMatrix = new Matrix4f();
		//lightViewMatrix = new Matrix4f();
	}

	/** Get the projection matrix.
	 * Update the projection matrix when the projection has changed
	 * using the {@code updateProjectionMatrix} method.
	 * 
	 * @return */
	public Matrix4f getProjectionMatrix()
	{ return projectionMatrix; }

	/** Update the projection matrix
	 * 
	 * @param  fov
	 * @param  width
	 * @param  height
	 * @param  zNear
	 * @param  zFar
	 * @return */
	public Matrix4f updateProjectionMatrix(float fov, float width, float height, float zNear, float zFar)
	{
		float aspectRatio = width / height;
		return projectionMatrix.setPerspective(fov, aspectRatio, zNear, zFar);
	}

	/*
	public final Matrix4f getOrthoProjectionMatrix() {
		return orthoProjMatrix;
	}
	
	public Matrix4f updateOrthoProjectionMatrix(float left, float right, float bottom, float top, float zNear, float zFar) {
		return orthoProjMatrix.setOrtho(left, right, bottom, top, zNear, zFar);
	}
	*/
	/** Get the view matrix.
	 * Update the view matrix when the camera view has changed
	 * using the {@code updateViewMatrix} method.
	 * 
	 * @return */
	public Matrix4f getViewMatrix()
	{ return viewMatrix; }

	/** Update the view matrix
	 * 
	 * @param  camera
	 * @return */
	public Matrix4f updateViewMatrix(Camera camera)
	{ return updateGenericViewMatrix(camera.getPosition(), camera.getRotation(), viewMatrix); }

	/*
	public Matrix4f getLightViewMatrix() {
		return lightViewMatrix;
	}
	
	public void setLightViewMatrix(Matrix4f lightViewMatrix) {
		this.lightViewMatrix.set(lightViewMatrix);
	}
	
	public Matrix4f updateLightViewMatrix(Vector3f position, Vector3f rotation) {
		return updateGenericViewMatrix(position, rotation, lightViewMatrix);
	}
	*/
	public static Matrix4f updateGenericViewMatrix(Vector3f position, Vector3f rotation, Matrix4f matrix)
	{
		// First do the rotation so camera rotates over its position
		return matrix.rotationX((float) Math.toRadians(rotation.x))
			.rotateY((float) Math.toRadians(rotation.y))
			.rotateZ((float) Math.toRadians(rotation.z))
			.translate(-position.x, -position.y, -position.z);
	}

	/*
	public final Matrix4f getOrtho2DProjectionMatrix(float left, float right, float bottom, float top) {
		return ortho2DMatrix.setOrtho2D(left, right, bottom, top);
	}
	*/
	/** Create the matrix that describes the object's transformation from model space into world space.
	 * Stores the result in the classes' {@code modelMatrix} instance.
	 * 
	 * @param  entity
	 * @return */
	public Matrix4f buildModelMatrix(RotatableEntity entity)
	{
		Quaternionf rotation = entity.getRotation();
		return modelMatrix.translationRotateScale(
			entity.getPosition().x, entity.getPosition().y, entity.getPosition().z,
			rotation.x, rotation.y, rotation.z, rotation.w,
			entity.getScaleX(), entity.getScaleY(), entity.getScaleZ());
	}

	/** Create the matrix that describes the object's translation from model space into world space.
	 * This model matrix is intended to be a billboard so no rotation or scaling is applied.
	 * Stores the result in the classes' {@code modelMatrix} instance.
	 * 
	 * @param  entity
	 * @return */
	public Matrix4f buildModelBillboardMatrix(Entity entity)
	{ return modelMatrix.translation(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z); }

	/** Create the matrix that describes the object's transformation from model space into view space.
	 * Stores the result in the classes' {@code modelViewMatrix} instance.
	 * 
	 * @param  entity
	 * @param  viewMatrix
	 * @return */
	public Matrix4f buildModelViewMatrix(RotatableEntity entity, Matrix4f viewMatrix)
	{ return buildModelViewMatrix(buildModelMatrix(entity), viewMatrix); }

	/** Create the matrix that describes the object's transformation from
	 * model space into view space as a billboard.
	 * Stores the result in the classes' {@code modelViewMatrix} instance.
	 * 
	 * @param  entity
	 * @param  viewMatrix
	 * @return */
	public Matrix4f buildModelViewBillBoardMatrix(Entity entity, Matrix4f viewMatrix)
	{
		Matrix4f modelMatrix = buildModelBillboardMatrix(entity);
		viewMatrix.transpose3x3(modelMatrix);
		modelMatrix.scale(entity.getScaleX(), entity.getScaleY(), entity.getScaleZ());
		return buildModelViewMatrix(modelMatrix, viewMatrix);
	}

	/** Create the matrix that describes the transformation from model space
	 * into view space as specified by the supplied matrices.
	 * Stores the result in the classes' {@code modelViewMatrix} instance.
	 * 
	 * @param  modelMatrix
	 * @param  viewMatrix
	 * @return */
	public Matrix4f buildModelViewMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix)
	{ return viewMatrix.mulAffine(modelMatrix, modelViewMatrix); }
	/*
	public Matrix4f buildModelLightViewMatrix(RotatableEntity entity, Matrix4f lightViewMatrix) {
		return buildModelViewMatrix(buildModelMatrix(entity), lightViewMatrix);
	}
	
	public Matrix4f buildModelLightViewMatrix(Matrix4f modelMatrix, Matrix4f lightViewMatrix) {
		return lightViewMatrix.mulAffine(modelMatrix, modelLightViewMatrix);
	}
	
	public Matrix4f buildOrthoProjModelMatrix(RotatableEntity entity, Matrix4f orthoMatrix) {
		return orthoMatrix.mulOrthoAffine(buildModelMatrix(entity), orthoModelMatrix);
	}
	*/
}