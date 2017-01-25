package com.bwyap.lwjgl.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

/**
 * <p>
 * A mesh holds the vertices, indices, normals and texture co-ordinates 
 * necessary to render a 3D model using LWJGL. Meshes should be loaded
 * from OBJ files using the {@code OBJLoader} class.
 * </p>
 * 
 * <p>
 * A mesh must be given a texture using the {@code setTexture} method,
 * and the texture must have been previously loaded. By default a mesh
 * without a texture will be rendered using a default colour. A mesh 
 * also be rendered with a colour which can be assigned using the 
 * {@code setColour} method if no texture has been previously assigned.
 * </p>
 * 
 * @author bwyap
 * 
 */
public class Mesh {
	
	private static final Map<String, Mesh> loadedMeshes = new HashMap<String, Mesh>();
	
	/**
	 * Get the mesh with the specified name.
	 * If no such mesh is found, null is returned.
	 * @param name
	 * @return
	 */
	public static Mesh getMesh(String name) {
		return loadedMeshes.get(name);
	}
	
	/**
	 * Add a mesh to the map of loaded meshes.
	 * Returns the mesh with the given name previously if overriding.
	 * Null otherwise.
	 * @param name
	 * @param mesh
	 * @return
	 */
	protected static Mesh addMesh(String name, Mesh mesh) {
		return loadedMeshes.put(name, mesh);
	}
	
	
	private static final Vector3f DEFAULT_COLOUR = new Vector3f(0.5f, 0.5f, 0.5f);

	private final String name;
	private final int vaoID;
	private final int vertexCount;
	protected final ArrayList<Integer> vboIDs;
	
	private Vector3f colour;
	
	
	/**
	 * Creates a mesh with the given vertices, indices, normals and texture co-ordinates.
	 * This constructor should only be used by the {@code OBJLoader} class.
	 * The required vertex buffer objects and vertex array objects will be created for use by the graphics card.
	 * @param positions
	 * @param texCoords
	 * @param normals
	 * @param indices
	 */
	protected Mesh(String name, float[] positions, float[] texCoords, float[] normals, int[] indices) {
		this.name = name;
		this.vertexCount = indices.length;
		this.colour = DEFAULT_COLOUR;
		
		vboIDs = new ArrayList<Integer>();
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		// Position VBO
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
		verticesBuffer.put(positions).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		// Tex coord VBO
		vboID = glGenBuffers();
		vboIDs.add(vboID);
		FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(texCoords.length); 
		textureBuffer.put(texCoords).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboID); 
		glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW); 
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		// Vertex normals VBO
		vboID = glGenBuffers();
		vboIDs.add(vboID);
		FloatBuffer vecNormalsBuffer = BufferUtils.createFloatBuffer(normals.length); 
		vecNormalsBuffer.put(normals).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW); 
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		
		// Index VBO
		vboID = glGenBuffers();
		vboIDs.add(vboID);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length); 
		indicesBuffer.put(indices).flip(); 
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID); 
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	
	/**
	 * Get the name assigned to the mesh
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Clean up the resources when the mesh was created.
	 */
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
	
	
	/**
	 * Set the colour for this mesh.
	 * The model will be rendered with this colour if no texture has been set.
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setColour(float r, float g, float b) {
		colour.x = r;
		colour.y = g;
		colour.z = b;
	}
	
	
	/**
	 * Set the colour for this mesh.
	 * The model will be rendered with this colour if no texture has been set.
	 * @param colour
	 */
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	
	/**
	 * Get the assigned colour of this mesh.
	 * @return
	 */
	public Vector3f getColour() {
		return colour;
	}
	
	
	/**
	 * Get the ID of the vertex array object for this mesh.
	 * @return
	 */
	public int getVaoID() {
		return vaoID;
	}
	
	
	/**
	 * Get the vertex count of this mesh's model.
	 * @return
	 */
	public int getVertexCount() {
		return vertexCount;
	}
	
}
