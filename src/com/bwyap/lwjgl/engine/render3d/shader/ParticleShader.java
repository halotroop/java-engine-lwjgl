package com.bwyap.lwjgl.engine.render3d.shader;

public class ParticleShader extends Shader
{
	public ParticleShader() throws Exception
	{ super(); }

	@Override
	public void init() throws Exception
	{
		createVertexShader(Shader.getSource("particle_vertex"));
		createFragmentShader(Shader.getSource("particle_fragment"));
		link();
		// Create uniforms for modelView and projection matrices and texture
		createUniform("projectionMatrix");
		createUniform("textureSampler");
		// Create uniforms for animated particle support
		createUniform("numRows");
		createUniform("numCols");
	}
}
