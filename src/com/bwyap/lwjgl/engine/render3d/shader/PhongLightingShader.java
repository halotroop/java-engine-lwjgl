package com.bwyap.lwjgl.engine.render3d.shader;

import com.bwyap.enginedriver.resource.Resource;
import com.bwyap.lwjgl.engine.render3d.Material;
import com.bwyap.lwjgl.engine.render3d.light.DirectionalLight;
import com.bwyap.lwjgl.engine.render3d.light.PointLight;
import com.bwyap.lwjgl.engine.render3d.light.SceneLighting;
import com.bwyap.lwjgl.engine.render3d.light.SpotLight;

/**
 * Phong lighting model shader. 
 * Provides methods to create uniforms for directional lights, point lights and spot lights. 
 * @author bwyap
 *
 */
public class PhongLightingShader extends Shader {
	
	
	public PhongLightingShader() throws Exception {
		super();
	}
	

	/**
	 * Initialise the uniforms required for Phong lighting 
	 * @throws Exception
	 */
	@Override
	public void init() throws Exception {
		createVertexShader(Resource.vertexShaderCode);
		createFragmentShader(Resource.fragmentShaderCode);
		link();
		
		// Create uniforms for modelView and projection matrices and texture
		createUniform("projectionMatrix");
		createUniform("modelViewNonInstancedMatrix");
		createUniform("textureSampler");
		
		createUniform("isInstanced");
		createUniform("numCols");
		createUniform("numRows");
		createUniform("nonInstancedTexOffset");
		
		createMaterialUniform("material");
		createUniform("useTexture");
		createUniform("specularPower");
		createUniform("ambientLight");
		createDirectionalLightListUniform("directionalLights", SceneLighting.MAX_DIR_LIGHTS);
		createPointLightListUniform("pointLights", SceneLighting.MAX_POINT_LIGHTS);
		createSpotLightListUniform("spotLights", SceneLighting.MAX_SPOT_LIGHTS);	
	}
	
	
	/**
	 * Create the uniform for a point light
	 * @param uniformName
	 * @throws Exception
	 */
	public void createPointLightUniform(String uniformName) throws Exception { 
		createUniform(uniformName + ".colour");
		createUniform(uniformName + ".position");
		createUniform(uniformName + ".intensity");
		createUniform(uniformName + ".att.constant");
		createUniform(uniformName + ".att.linear");
		createUniform(uniformName + ".att.exponent");
	}
	
	
	/**
	 * Create the uniform for a directional light
	 * @param uniformName
	 * @throws Exception
	 */
	public void createDirectionalLightUniform(String uniformName) throws Exception {
		createUniform(uniformName + ".colour");
		createUniform(uniformName + ".direction");
		createUniform(uniformName + ".intensity");
	}
	
	
	/**
	 * Create the uniform for a spot light
	 * @param uniformName
	 * @throws Exception
	 */
	public void createSpotLightUniform(String uniformName) throws Exception {
		createPointLightUniform(uniformName  + ".pl");
		createUniform(uniformName + ".coneDir");
		createUniform(uniformName + ".cutoff");
	}
	
	
	/**
	 * Create the uniform for a material 
	 * @param uniformName
	 * @throws Exception
	 */
	public void createMaterialUniform(String uniformName) throws Exception { 
		createUniform(uniformName + ".colour");
		createUniform(uniformName + ".reflectance");
	}
	
	
	/**
	 * Create the uniform for a list of directional lights
	 * @param uniformName
	 * @param size
	 * @throws Exception
	 */
	public void createDirectionalLightListUniform(String uniformName, int size) throws Exception {
		for (int i = 0; i < size; i++) {
			createDirectionalLightUniform(uniformName + "[" + i + "]");
		} 
	}    
	
	
	/**
	 * Create the uniform for a list of point lights
	 * @param uniformName
	 * @param size
	 * @throws Exception
	 */
	public void createPointLightListUniform(String uniformName, int size) throws Exception {
		for (int i = 0; i < size; i++) {
			createPointLightUniform(uniformName + "[" + i + "]");
		}
	}

	
	/**
	 * Create the uniform for a list of spot lights
	 * @param uniformName
	 * @param size
	 * @throws Exception
	 */
	public void createSpotLightListUniform(String uniformName, int size) throws Exception {
		for (int i = 0; i < size; i++) {
			createSpotLightUniform(uniformName + "[" + i + "]");
		} 
	}    
	
	
	/**
	 * Sets the uniforms for a point light. 
	 * @param uniformName
	 * @param pointLight
	 */
	public void setUniform(String uniformName, PointLight pointLight) { 
		setUniform(uniformName + ".colour", pointLight.getColour()); 
		setUniform(uniformName + ".position", pointLight.getPosition()); 
		setUniform(uniformName + ".intensity", pointLight.getIntensity()); 
		PointLight.Attenuation att = pointLight.getAttenuation(); 
		setUniform(uniformName + ".att.constant", att.getConstant()); 
		setUniform(uniformName + ".att.linear", att.getLinear()); 
		setUniform(uniformName + ".att.exponent", att.getExponent());
	}
	
	
	/**
	 * Sets the uniforms for a directional light. 
	 * @param uniformName
	 * @param dirLight
	 * @throws Exception
	 */
	public void setUniform(String uniformName, DirectionalLight dirLight) {
		setUniform(uniformName + ".colour", dirLight.getColour()); 
		setUniform(uniformName + ".direction", dirLight.getDirection()); 
		setUniform(uniformName + ".intensity", dirLight.getIntensity());
	}
	
	
	/**
	 * Sets the uniforms for a spot light. 
	 * @param uniformName
	 * @param spotLight
	 * @throws Exception
	 */
	public void setUniform(String uniformName, SpotLight spotLight) {
		setUniform(uniformName + ".pl", spotLight.getPointLight());
		setUniform(uniformName + ".coneDir", spotLight.getConeDirection());
		setUniform(uniformName + ".cutoff", spotLight.getCutOff());
	}
	
	/**
	 * Sets the uniforms for a material. 
	 * @param uniformName
	 * @param material
	 * @throws Exception
	 */
	public void setUniform(String uniformName, Material material) { 
		setUniform(uniformName + ".colour", material.getColour()); 
		setUniform(uniformName + ".reflectance", material.getReflectance());
	}
	

	/**
	 * Sets the uniforms for a list of directional lights
	 * @param uniformName
	 * @param directionalLights
	 */
	public void setUniform(String uniformName, DirectionalLight[] directionalLights) { 
		int numLights = directionalLights != null ? directionalLights.length : 0;	
		for (int i = 0; i < numLights; i++) {
			setUniform(uniformName, directionalLights[i], i);
		}
	}
	
	
	/**
	 * Sets the uniforms for a directional light in an array at the specified position.
	 * @param uniformName
	 * @param directionalLight
	 * @param pos
	 */
	public void setUniform(String uniformName, DirectionalLight directionalLight, int pos) { 
		setUniform(uniformName + "[" + pos + "]", directionalLight);
	}
	
	
	/**
	 * Sets the uniforms for a list of point lights
	 * @param uniformName
	 * @param pointLights
	 */
	public void setUniform(String uniformName, PointLight[] pointLights) { 
		int numLights = pointLights != null ? pointLights.length : 0;	
		for (int i = 0; i < numLights; i++) {
			setUniform(uniformName, pointLights[i], i);
		}
	}
	
	/**
	 * Sets the uniforms for a point light in an array at the specified position.
	 * @param uniformName
	 * @param pointLight
	 * @param pos
	 */
	public void setUniform(String uniformName, PointLight pointLight, int pos) { 
		setUniform(uniformName + "[" + pos + "]", pointLight);
	}
	
	
	/**
	 * Sets the uniforms for a list of spot lights
	 * @param uniformName
	 * @param spotLights
	 */
	public void setUniform(String uniformName, SpotLight[] spotLights) {
		int numLights = spotLights != null ? spotLights.length : 0;
		for (int i = 0; i < numLights; i++) {
			setUniform(uniformName, spotLights[i], i);
		}
	}
	
	
	/**
	 * Sets the uniforms for a spot light in an array at the specified position.
	 * @param uniformName
	 * @param spotLight
	 * @param pos
	 */
	public void setUniform(String uniformName, SpotLight spotLight, int pos) { 
		setUniform(uniformName + "[" + pos + "]", spotLight);
	}
	
}
