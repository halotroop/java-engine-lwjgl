package com.bwyap.lwjgl.engine.render3d.light;

import org.joml.Vector3f;


/**
 * Holds information about all directional, point and spot lights in a scene.
 * 
 * @author bwyap
 *
 */
public class SceneLighting {

	public static final int MAX_DIR_LIGHTS = 4;
	public static final int MAX_POINT_LIGHTS = 10;
	public static final int MAX_SPOT_LIGHTS = 10;

	private DirectionalLight[] directionalLights;
	private PointLight[] pointLights;
	private SpotLight[] spotLights;
	
	private int pointIndex = 0;
	private int directionalIndex = 0;
	private int spotIndex = 0;
	
	private Vector3f ambientLight;
	private float specularPower;
	
	
	public SceneLighting() {
		directionalLights = new DirectionalLight[MAX_DIR_LIGHTS];
		pointLights = new PointLight[MAX_POINT_LIGHTS];
		spotLights = new SpotLight[MAX_SPOT_LIGHTS];
	}
	
	
	/**
	 * Creates a point light. The light is automatically added to the scene.
	 * There is a maximum number of {@code PointLights} permitted in a scene, 
	 * limited by {@code MAX_POINT_LIGHTS}. 
	 * @param colour
	 * @param position
	 * @param intensity
	 * @throws Exception maximum number of point lights exceeded
	 * @return
	 */
	public PointLight createPointLight(Vector3f colour, Vector3f position, float intensity) throws Exception {
		PointLight pointLight = new PointLight(pointIndex, colour, position, intensity);
		pointLights[pointIndex++] = pointLight;
		if (pointIndex == MAX_POINT_LIGHTS) {
			throw new Exception("Unable to create new point light: maximum number of PointLights for this scene exceeded.");
		}
		return pointLight;
	}
	
	
	/**
	 * Creates a directional light. The light is automatically added to the scene.
	 * There is a maximum number of {@code DirectionalLights} permitted in a scene, 
	 * limited by {@code MAX_DIR_LIGHTS}. 
	 * @param colour
	 * @param position
	 * @param intensity
	 * @param direction
	 * @return
	 * @throws Exception maximum number of directional lights exceeded
	 */
	public DirectionalLight createDirectionalLight(Vector3f colour, Vector3f position, float intensity, Vector3f direction) throws Exception {
		DirectionalLight directionalLight = new DirectionalLight(directionalIndex, colour, position, intensity);
		directionalLight.setDirection(direction);
		directionalLights[directionalIndex++] = directionalLight;
		if (directionalIndex == MAX_DIR_LIGHTS) {
			throw new Exception("Unable to create new point light: maximum number of DirectionalLights for this scene exceeded.");
		}
		return directionalLight;
	}
	
	
	/**
	 * Creates a spot light. The light is automatically added to the scene.
	 * There is a maximum number of {@code SpotLights} permitted in a scene, 
	 * limited by {@code MAX_SPOT_LIGHTS}. 
	 * @param colour
	 * @param position
	 * @param intensity
	 * @param coneDirection
	 * @param cutoffAngle
	 * @return
	 * @throws Exception maximum number of spot lights exceeded
	 */
	public SpotLight createSpotLight(Vector3f colour, Vector3f position, float intensity, Vector3f coneDirection, float cutOff) throws Exception {
		PointLight pointLight = new PointLight(spotIndex, colour, position, intensity);
		SpotLight spotLight = new SpotLight(spotIndex, pointLight, coneDirection, cutOff);
		spotLights[spotIndex++] = spotLight;
		if (spotIndex == MAX_SPOT_LIGHTS) {
			throw new Exception("Unable to create new point light: maximum number of SpotLights for this scene exceeded.");
		}
		return spotLight;
	}
	
	
	/**
	 * Set the ambient light of the scene
	 * @param ambientLight
	 */
	public void setAmbient(Vector3f ambientLight) {
		this.ambientLight = ambientLight;
	}
	
	
	/**
	 * Get the ambient light of the scene
	 * @return
	 */
	public Vector3f getAmbientLight() {
		return ambientLight;
	}
	
	
	/**
	 * Set the specular power used in the lighting calculations
	 * @param specularPower
	 */
	public void setSpecularPower(float specularPower) {
		this.specularPower = specularPower;
	}
	
	
	/**
	 * Get the specular power used in the lighting calculations
	 * @return
	 */
	public float getSpecularPower() {
		return specularPower;
	}
	
	
	/**
	 * Get the array of point lights
	 * @return
	 */
	public PointLight[] getPointLights() {
		return pointLights;
	}
	
	
	/**
	 * Get the array of directional lights
	 * @return
	 */
	public DirectionalLight[] getDirectionalLights() {
		return directionalLights;
	}
	
	
	/**
	 * Get the array of spot lights
	 * @return
	 */
	public SpotLight[] getSpotLights() {
		return spotLights;
	}
	
}
