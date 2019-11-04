package de.kania.asset;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {

	private Vector3f xyz;

	private Vector4f rgba;

	public Vertex(Vector3f xyz, Vector4f rgba) {
		this.xyz = xyz;
		this.rgba = rgba;
	}

	public Vertex(float x, float y, float z, float r, float g, float b, float a) {
		this.xyz = new Vector3f(x, y, z);
		this.rgba = new Vector4f(r, g, b, a);
	}

	public Vector3f getXyz() {
		return xyz;
	}

	public Vector4f getRgba() {
		return rgba;
	}

	public float[] toFloatArray() {
		float[] data = new float[] { xyz.x, xyz.y, xyz.z, rgba.x, rgba.y, rgba.z, rgba.w };

		return data;
	}

	public static int getSizeInBytes() {
		return 7 * Float.BYTES;
	}
}
