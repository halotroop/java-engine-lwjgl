#version 330

const int MAX_DIR_LIGHTS = 4;
const int MAX_POINT_LIGHTS = 10;
const int MAX_SPOT_LIGHTS = 10;

in vec2 outTexCoord;
in vec3 mvVertexNormal;
in vec3 mvVertexPos;

out vec4 fragColour;


struct Attenuation
{
	float constant;
	float linear;
	float exponent;
};

struct PointLight 
{
	vec3 colour;
	vec3 position;		//Light position assumed to be in view coordinates
	float intensity;
	Attenuation att;
};

struct DirectionalLight
{
	vec3 colour;
	vec3 direction;
	float intensity;
};

struct SpotLight
{
	PointLight pl;
	vec3 coneDir;
	float cutoff;
};

struct Material
{
	vec3 colour;
	float reflectance;
};	


uniform int useTexture;
uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;

uniform DirectionalLight directionalLights[MAX_DIR_LIGHTS];
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];


vec4 calcLightColour(vec3 lightColour, float lightIntensity, vec3 position, vec3 toLightDirection, vec3 normal)
{
	vec4 diffuseColour = vec4(0, 0, 0, 0);
	vec4 specColour = vec4(0, 0, 0, 0);

	// Diffuse light
	float diffuseFactor = max(dot(normal, toLightDirection), 0);
	diffuseColour = vec4(lightColour, 1.0) * lightIntensity * diffuseFactor;

	// Specular light
	vec3 cameraDirection = normalize(-position);
	vec3 fromLightDirection = -toLightDirection;
	vec3 reflectedLight = normalize(reflect(fromLightDirection, normal));
	float specularFactor = max(dot(cameraDirection, reflectedLight), 0);
	specularFactor = pow(specularFactor, specularPower);
	specColour = lightIntensity * specularFactor * material.reflectance * vec4(lightColour, 1);

	return (diffuseColour + specColour);
}


vec4 calcPointLight(PointLight light, vec3 position, vec3 normal)
{
	vec3 lightDirection = light.position - position;
	vec3 toLightDirection = normalize(lightDirection);
	vec4 lightColour = calcLightColour(light.colour, light.intensity, position, toLightDirection, normal);

	// Apply Attenuation
	float distance = length(lightDirection);
	float attenuationInv = 
		light.att.constant + 
		(light.att.linear * distance) + 
		(light.att.exponent * distance * distance);

	return lightColour / attenuationInv;
}


vec4 calcSpotLight(SpotLight light, vec3 position, vec3 normal) 
{
	vec3 lightDirection = light.pl.position - position;
	vec3 toLightDirection = normalize(lightDirection);
	vec3 fromLightDirection = -toLightDirection;
	float spotAlpha = dot(fromLightDirection, normalize(light.coneDir));

	vec4 colour = vec4(0, 0, 0, 0);

	if (spotAlpha > light.cutoff) 
	{
		colour = calcPointLight(light.pl, position, normal);
		colour *= (1.0 - (1.0 - spotAlpha)/(1.0 - light.cutoff));
	}

	return colour;
}


vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal) 
{
	return calcLightColour(light.colour, light.intensity, position, normalize(light.direction), normal);
}



void main() 
{
	vec4 baseColour;

	if (useTexture == 0) {
		baseColour = vec4(material.colour, 1);
	}
	else {
		baseColour = texture(textureSampler, outTexCoord);
	}

	vec4 totalLight = vec4(ambientLight, 1);

	for(int i = 0; i < MAX_DIR_LIGHTS; i++) 
	{
		if (directionalLights[i].intensity > 0)
		{
			totalLight += calcDirectionalLight(directionalLights[i], mvVertexPos, mvVertexNormal);
		}
	}

	for(int i = 0; i < MAX_POINT_LIGHTS; i++) 
	{
		if (pointLights[i].intensity > 0)
		{
			totalLight += calcPointLight(pointLights[i], mvVertexPos, mvVertexNormal);
		}
	}

	for(int i = 0; i < MAX_SPOT_LIGHTS; i++) 
	{
		if (spotLights[i].pl.intensity > 0)
		{
			totalLight += calcSpotLight(spotLights[i], mvVertexPos, mvVertexNormal);
		}
	}

	fragColour = baseColour * totalLight;
}

