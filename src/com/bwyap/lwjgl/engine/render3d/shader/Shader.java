package com.bwyap.lwjgl.engine.render3d.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

/**
 * Responsible for initialising a vertex and fragment shader in the rendering pipeline.
 * Contains methods to pass uniform data to the shaders when rendering a scene.
 * @author bwyap
 *
 */
public abstract class Shader {
	
	
	private static final Map<String, String> sources = new HashMap<String, String>();
	
	
	/**
	 * Get the source code of the shader with the given name
	 * from the static Map of loaded shader sources.
	 * If the specified shader has not been loaded, this 
	 * method will return {@code null}. 
	 * @param shaderName
	 * @return
	 */
	public static String getSource(String shaderName) {
		return sources.get(shaderName);
	}
	
	
	/**
	 * Add the source code of the shader with the given name
	 * to the static Map of shader sources. This will allow
	 * the source code of the shader to be retrieved by name.
	 * @param shaderName
	 * @param sourceCode
	 */
	public static void addSource(String shaderName, String sourceCode) {
		sources.put(shaderName, sourceCode);
	}
	
	
	
	protected final int programID;
	protected final Map<String, Integer> uniforms;
	
	protected int vertexShaderID;
	protected int fragmentShaderID;
	
	
	public Shader() throws Exception {
		programID = glCreateProgram();
		if (programID == 0) {
			throw new Exception("Could not create shader.");
		}
		
		 uniforms = new HashMap<>();
	}
	
	
	/**
	 * Initialise the shader
	 * @throws Exception
	 */
	public abstract void init() throws Exception;
	
	
	/**
	 * Get the ID of the shader program
	 * @return
	 */
	public int getID() {
		return programID;
	}
	
	
	/**
	 * Create a uniform for the shader
	 * @param uniformName
	 * @throws Exception
	 */
	public void createUniform(String uniformName) throws Exception {
		int uniformLocation = glGetUniformLocation(programID, uniformName);
	    
		if (uniformLocation < 0) {
	    	throw new Exception("Could not find uniform:" + uniformName);
	    }
		
	    uniforms.put(uniformName, uniformLocation);
	}
	
	
	/**
	 * Set the specified uniform to the value of a 4x4 matrix
	 * @param uniformName
	 * @param value
	 */
	public void setUniform(String uniformName, Matrix4f value) {
		// Dump the matrix into a float buffer
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		value.get(fb);
		glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
	}

	
	/**
	 * Set the specified uniform to the value of a Vector3f
	 * @param uniformName
	 * @param value
	 */
	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z );
	}
	


	public void setUniform(String uniformName, Vector2f value) {
		glUniform2f(uniforms.get(uniformName), value.x, value.y);
	}
	
		
	/**
	 * Set the specified uniform to the value of an integer
	 * @param uniformName
	 * @param value
	 */
	public void setUniform(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}

	
	/**
	 * Set the specified uniform to the value of an float
	 * @param uniformName
	 * @param value
	 */
	public void setUniform(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	
	/**
	 * Create a vertex shader and attach it to the program
	 * @param shaderCode
	 * @throws Exception
	 */
	public void createVertexShader(String shaderCode) throws Exception {
		vertexShaderID = createShader(shaderCode, GL_VERTEX_SHADER);
	}
	

	/**
	 * Create a fragment shader and attach it to the program
	 * @param shaderCode
	 * @throws Exception
	 */
	public void createFragmentShader(String shaderCode) throws Exception {
		fragmentShaderID = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}
	
	
	/**
	 * Create a shader of the specified type and attach it to the program
	 * @param shaderCode
	 * @param shaderType
	 * @return
	 * @throws Exception
	 */
	protected int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderID = glCreateShader(shaderType);
		
		if (shaderID == 0) {
			throw new Exception("Error creating shader. ");
		}
		
		glShaderSource(shaderID, shaderCode);
		glCompileShader(shaderID);
		
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
			//System.out.println(shaderCode);
			throw new Exception("Error compiling shader code: " + glGetShaderInfoLog(shaderID, 1024));
		}
		
		glAttachShader(programID, shaderID);
		
		return shaderID;
	}
	
	
	/**
	 * Links the shaders to the program
	 * @throws Exception
	 */
	public void link() throws Exception {
		glLinkProgram(programID);
		
		if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking shader code: " + glGetProgramInfoLog(programID, 1024));
		}
		
		glValidateProgram(programID);
		if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating shader code: " + glGetProgramInfoLog(programID, 1024));
		}
	}
	
	
	/**
	 * 
	 */
	public void bind() {
		glUseProgram(programID);
	}
	
	
	/**
	 * 
	 */
	public void unbind() {
		glUseProgram(0);
	}
	
	
	/**
	 * Clean up the shaders
	 */
	public void cleanUp() {
		unbind();
		
		if (programID != 0) {
			if (vertexShaderID != 0) glDetachShader(programID, vertexShaderID);
			if (fragmentShaderID != 0) glDetachShader(programID, fragmentShaderID);
		}
		
		glDeleteProgram(programID);
	}
	
}
