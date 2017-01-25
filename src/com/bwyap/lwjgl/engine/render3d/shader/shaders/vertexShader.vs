#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;
layout (location=3) in mat4 modelViewInstancedMatrix;
layout (location=7) in vec2 texOffset;


out vec2 outTexCoord;
out vec3 mvVertexNormal;
out vec3 mvVertexPos;
//out mat4 outModelViewMatrix;

uniform int isInstanced;
uniform mat4 projectionMatrix;
uniform mat4 modelViewNonInstancedMatrix;

uniform int numCols;
uniform int numRows;
uniform vec2 nonInstancedTexOffset;

void main() 
{
	mat4 modelViewMatrix;

	if (isInstanced > 0) 
	{
		modelViewMatrix = modelViewInstancedMatrix;
		// Support for texture atlas, update texture coordinates
		float x = (texCoord.x / numCols + texOffset.x);
		float y = (texCoord.y / numRows + texOffset.y);
		outTexCoord = vec2(x, y);
	}
	else 
	{
		modelViewMatrix = modelViewNonInstancedMatrix;
		// Support for texture atlas, update texture coordinates
		float x = (texCoord.x / numCols + nonInstancedTexOffset.x);
		float y = (texCoord.y / numRows + nonInstancedTexOffset.y);
		outTexCoord = vec2(x, y);
	}

	vec4 mvPos = modelViewMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * mvPos;


	mvVertexNormal = normalize(modelViewMatrix * vec4(vertexNormal, 0.0)).xyz;
	mvVertexPos = mvPos.xyz;
	//outModelViewMatrix = modelViewMatrix;
}