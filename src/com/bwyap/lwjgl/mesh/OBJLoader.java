package com.bwyap.lwjgl.mesh;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.bwyap.utility.resource.ResourceLoader;

/** A helper class for parsing an OBJ file and loading it as a mesh.
 * 
 * @author bwyap */
public class OBJLoader
{
	/** Loads an {@code OBJ} model as a mesh with the vertex array object initialised.
	 * 
	 * @param  fileName
	 * @param  i         number of instances
	 * @return
	 * @throws Exception */
	public static InstancedMesh loadInstancedMesh(String name, String fileName, int i) throws Exception
	{
		MeshProperties p = decodeMesh(fileName);
		Mesh.addMesh(name, new InstancedMesh(name, p.posArr, p.textCoordArr, p.normArr, p.indicesArr, i));
		return (InstancedMesh) Mesh.getMesh(name);
	}

	/** Loads an {@code OBJ} model as an instanced mesh with the vertex array object initialised.
	 * 
	 * @param  fileName
	 * @return
	 * @throws Exception */
	public static Mesh loadMesh(String name, String fileName) throws Exception
	{
		MeshProperties p = decodeMesh(fileName);
		Mesh.addMesh(name, new Mesh(name, p.posArr, p.textCoordArr, p.normArr, p.indicesArr));
		return Mesh.getMesh(name);
	}

	/** Load and decode the {@code OBJ} specified by {@code filename}.
	 * 
	 * @param  fileName
	 * @return
	 * @throws Exception */
	private static MeshProperties decodeMesh(String fileName) throws Exception
	{
		List<String> lines = ResourceLoader.loadInternalTextFileAsList(fileName);
		List<Vector3f> vertices = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		List<Face> faces = new ArrayList<>();
		for (String line : lines)
		{
			String[] tokens = line.split("\\s+");
			switch (tokens[0])
			{
			case "v":
				// Geometric vertex
				Vector3f vec3f = new Vector3f(
					Float.parseFloat(tokens[1]),
					Float.parseFloat(tokens[2]),
					Float.parseFloat(tokens[3]));
				vertices.add(vec3f);
				break;
			case "vt":
				// Texture coordinate
				Vector2f vec2f = new Vector2f(
					Float.parseFloat(tokens[1]),
					Float.parseFloat(tokens[2]));
				textures.add(vec2f);
				break;
			case "vn":
				// Vertex normal
				Vector3f vec3fNorm = new Vector3f(
					Float.parseFloat(tokens[1]),
					Float.parseFloat(tokens[2]),
					Float.parseFloat(tokens[3]));
				normals.add(vec3fNorm);
				break;
			case "f":
				Face face = new Face(tokens[1], tokens[2], tokens[3]);
				faces.add(face);
				break;
			default:
				// Ignore other lines
				break;
			}
		}
		return reorderLists(vertices, textures, normals, faces);
	}

	private static MeshProperties reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList, List<Vector3f> normList, List<Face> facesList)
	{
		List<Integer> indices = new ArrayList<Integer>();
		// Create position array in the order it has been declared
		float[] posArr = new float[posList.size() * 3];
		int i = 0;
		for (Vector3f pos : posList)
		{
			posArr[i * 3] = pos.x;
			posArr[i * 3 + 1] = pos.y;
			posArr[i * 3 + 2] = pos.z;
			i++;
		}
		float[] textCoordArr = new float[posList.size() * 2];
		float[] normArr = new float[posList.size() * 3];
		for (Face face : facesList)
		{
			IndexGroup[] faceVertexIndices = face.getFaceVertexIndices();
			for (IndexGroup indValue : faceVertexIndices)
			{ processFaceVertex(indValue, textCoordList, normList,
				indices, textCoordArr, normArr); }
		}
		int[] indicesArr = new int[indices.size()];
		indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
		return new MeshProperties(posArr, textCoordArr, normArr, indicesArr);
	}

	private static void processFaceVertex(IndexGroup indices, List<Vector2f> textCoordList, List<Vector3f> normList, List<Integer> indicesList, float[] texCoordArr, float[] normArr)
	{
		// Set index for vertex coordinates
		int posIndex = indices.idxPos;
		indicesList.add(posIndex);
		// Reorder texture coordinates
		if (indices.idxTextCoord >= 0)
		{
			Vector2f textCoord = textCoordList.get(indices.idxTextCoord);
			texCoordArr[posIndex * 2] = textCoord.x;
			texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
		}
		if (indices.idxVecNormal >= 0)
		{
			// Reorder vector normals
			Vector3f vecNorm = normList.get(indices.idxVecNormal);
			normArr[posIndex * 3] = vecNorm.x;
			normArr[posIndex * 3 + 1] = vecNorm.y;
			normArr[posIndex * 3 + 2] = vecNorm.z;
		}
	}

	/** Represents an Index Group in the OBJ file for a vertex.
	 * 
	 * @author bwyap */
	protected static class IndexGroup
	{
		public static final int NO_VALUE = -1;
		public int idxPos;
		public int idxTextCoord;
		public int idxVecNormal;

		public IndexGroup()
		{
			idxPos = NO_VALUE;
			idxTextCoord = NO_VALUE;
			idxVecNormal = NO_VALUE;
		}
	}

	protected static class Face
	{
		/** List of idxGroup groups for a face triangle (3 vertices per face). */
		private IndexGroup[] idxGroups = new IndexGroup[3];

		public Face(String v1, String v2, String v3)
		{
			idxGroups = new IndexGroup[3];
			// Parse the lines
			idxGroups[0] = parseLine(v1);
			idxGroups[1] = parseLine(v2);
			idxGroups[2] = parseLine(v3);
		}

		private IndexGroup parseLine(String line)
		{
			IndexGroup idxGroup = new IndexGroup();
			String[] lineTokens = line.split("/");
			int length = lineTokens.length;
			idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
			if (length > 1)
			{
				// It can be empty if the obj does not define texture coords
				String textCoord = lineTokens[1];
				idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IndexGroup.NO_VALUE;
				if (length > 2)
				{ idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1; }
			}
			return idxGroup;
		}

		public IndexGroup[] getFaceVertexIndices()
		{ return idxGroups; }
	}

	protected static class MeshProperties
	{
		float[] posArr, textCoordArr, normArr;
		int[] indicesArr;

		MeshProperties(float[] posArr, float[] textCoordArr, float[] normArr, int[] indicesArr)
		{
			this.posArr = posArr;
			this.textCoordArr = textCoordArr;
			this.normArr = normArr;
			this.indicesArr = indicesArr;
		}
	}
}
