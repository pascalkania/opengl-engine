package de.kania.shader.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import de.kania.asset.Vertex;

public abstract class Model {

	private int vao;

	private int vbo;

	private int ebo;

	private List<Vertex> vertices;

	private List<Integer> indices;

	public Model() {
		vertices = new ArrayList<>();
		indices = new ArrayList<>();
		this.defineModel(vertices, indices);
	}

	public abstract void defineModel(List<Vertex> vertices, List<Integer> indices);

	public void upload() {
		// VAO-, VBO-, EBO-Objekt erzeugen und IDs/Objektnamen in Attributen vao,
		// vbo und ebo speichern.
		this.vao = glGenVertexArrays();
		this.vbo = glGenBuffers();
		this.ebo = glGenBuffers();

		FloatBuffer vertexArray = BufferUtils.createFloatBuffer(vertices.size() * Vertex.getSizeInBytes());
		for (Vertex v : vertices) {
			vertexArray.put(v.toFloatArray());
		}
		vertexArray.flip();

		glBindVertexArray(this.vao);

		// VBO bef�llen
		glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
		glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);

		// VAO Attribute f�r Position (Attribut 0) und Farbe (Attribut 1)
		// konfigurieren.
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * Float.BYTES, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES);

		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.size());
		for (int index : indices) {
			indexBuffer.put(index);
		}
		indexBuffer.flip();

		// EBO bef�llen
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

		glBindVertexArray(0);

	}

	public void draw() {
		// VAO binden
		glBindVertexArray(this.vao);

		// Rendering-Pipeline f�r Indexed Drawing aufrufen
		glDrawElements(GL_TRIANGLES, indices.size(), GL_UNSIGNED_INT, 0);
		glBindVertexArray(0); // VAO lösen
	}

	public void destroy() {
		// VAO, EBO, VBO l�schen
		glDeleteBuffers(this.vbo);
		glDeleteVertexArrays(this.vao);
	}

}
