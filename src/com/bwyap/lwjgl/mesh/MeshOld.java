package com.bwyap.lwjgl.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;


@Deprecated
public class MeshOld {
	
	private final int vaoID;
	private final int vertexCount;
	private final ArrayList<Integer> vboIDs;
	private final int textureID;
	
	
	public MeshOld(float[] positions, float[] texCoords, int[] indices, int textureID) {
		this.vertexCount = indices.length;
		this.textureID = textureID;
		vboIDs = new ArrayList<Integer>();
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		int positionVboID = glGenBuffers();
		vboIDs.add(positionVboID);
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
		verticesBuffer.put(positions).flip();
		glBindBuffer(GL_ARRAY_BUFFER, positionVboID);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		int textureVboID = glGenBuffers();
		vboIDs.add(textureVboID);
		FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(texCoords.length); 
		textureBuffer.put(texCoords).flip();
		glBindBuffer(GL_ARRAY_BUFFER, textureVboID); 
		glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW); 
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		int indexVboID = glGenBuffers();
		vboIDs.add(indexVboID);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length); 
		indicesBuffer.put(indices).flip(); 
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboID); 
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		
		glBindVertexArray(0);
	}
	
	
	public void render() {
        // Activate first texture unit
        glActiveTexture(GL_TEXTURE0);
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, getTextureID());
        
        // Draw the mesh
		glBindVertexArray(getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
		
		// Restore state
	    glDisableVertexAttribArray(0);
	    glDisableVertexAttribArray(1);
	    glBindVertexArray(0);
	}
	
	
	public void cleanup() {
		glDisableVertexAttribArray(0);
		
		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		for (int id : vboIDs) {
			glDeleteBuffers(id);
		}
		
		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoID);	
	}

	
	public int getTextureID() {
		return textureID;
	}
	
	
	public int getVaoID() {
		return vaoID;
	}
	
	
	public int getVertexCount() {
		return vertexCount;
	}
	
}
