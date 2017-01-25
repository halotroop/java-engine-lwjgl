package com.bwyap.lwjgl.engine.render3d.shader;

import com.bwyap.enginedriver.resource.Resource;


public class ParticleShader extends Shader {

	
	public ParticleShader() throws Exception {
		super();
	}
	
	
	@Override
	public void init() throws Exception {
	    createVertexShader(Resource.particleVertexShaderCode);
	    createFragmentShader(Resource.particleFragmentShaderCode);
	    link();
	    
		// Create uniforms for modelView and projection matrices and texture
	    createUniform("projectionMatrix");
	    createUniform("textureSampler");
	    
	    // Create uniforms for animated particle support
	    createUniform("numRows");
	    createUniform("numCols");
	}

}
