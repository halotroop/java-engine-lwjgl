package com.bwyap.lwjgl.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


public class InstancedMesh extends Mesh {
	
	public static final int FLOAT_SIZE_BYTES = 4;
	public static final int VECTOR4F_SIZE_BYTES = 4 * FLOAT_SIZE_BYTES;
	public static final int MATRIX_SIZE_FLOATS = 4 * 4;
	public static final int MATRIX_SIZE_BYTES = MATRIX_SIZE_FLOATS * FLOAT_SIZE_BYTES;
	public static final int INSTANCE_SIZE_BYTES = MATRIX_SIZE_BYTES * 1 + FLOAT_SIZE_BYTES * 2;
	public static final int INSTANCE_SIZE_FLOATS = MATRIX_SIZE_FLOATS * 1 + 2;

	private final int instances;
	private final int instanceDataVbo;
	
	private final FloatBuffer instanceDataBuffer;

	
	protected InstancedMesh(String name, float[] positions, float[] texCoords, float[] normals, int[] indices, int instances) {
		super(name, positions, texCoords, normals, indices);
		this.instances = instances;
		this.instanceDataBuffer = BufferUtils.createFloatBuffer(instances * INSTANCE_SIZE_FLOATS);
		
		glBindVertexArray(getVaoID());
		
		//Model view matrix
		instanceDataVbo = glGenBuffers();
		vboIDs.add(instanceDataVbo);
		glBindBuffer(GL_ARRAY_BUFFER, instanceDataVbo);
		int start = 3;
		int strideStart = 0;
		for (int i = 0; i < 4; i++) {
			glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
			glVertexAttribDivisor(start, 1);
			start++;
			strideStart += VECTOR4F_SIZE_BYTES;
		}
		
		// Texture offsets
		glVertexAttribPointer(start, 2, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
		glVertexAttribDivisor(start, 1);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	
	/**
	 * Get the maximum number of instances which can be rendered
	 * @return
	 */
	public int getInstances() {
		return instances;
	}
	
	
	
	/**
	 * Get the ID of the VBO for the instance data
	 * @return
	 */
	public int getInstanceDataVboID() {
		return instanceDataVbo;
	}
	
	
	/**
	 * Get the float buffer with the instance data for this mesh.
	 * Created with the appropriate size when this mesh was instantiated.
	 * @return
	 */
	public FloatBuffer getInstanceDataBuffer() {
		return instanceDataBuffer;
	}
	
}
