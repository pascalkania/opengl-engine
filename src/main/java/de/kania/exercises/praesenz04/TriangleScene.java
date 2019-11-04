package de.kania.exercises.praesenz04;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.joml.Vector4f;

import de.kania.OpenGLProgram;
import de.kania.shader.ShaderProgram;
import de.kania.shader.model.Model;
import de.kania.Stage;

public class TriangleScene implements OpenGLProgram {

	private final static Path PATH;

	static {
		try {
			PATH = Paths.get(TriangleScene.class.getResource(TriangleScene.class.getSimpleName() + ".class").toURI()).getParent();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private ShaderProgram shaderProgram;

	private Model model;

	@Override
	public void defineGeometries() {
		// neues Triangle Model erzeugen
		this.model = new TriangleModel();
	}

	@Override
	public void initializeState() {

		// Model Daten uploaden
		this.model.upload();
		shaderProgram = new ShaderProgram(PATH.resolve("vertex.glsl").toString(),
				PATH.resolve("fragment.glsl").toString());
		shaderProgram.compile();
	}

	@Override
	public void renderGeometries() {
		shaderProgram.use();
		shaderProgram.setVector4f("uniformColor", new Vector4f(0.4f, 0.5f, 0.6f, 1.0f));
		// Model zeichnen
		this.model.draw();
	}

	@Override
	public void destroy() {
		this.shaderProgram.destroy();
		// Model l�schen
		this.model.destroy();
	}

	public static void main(String[] args) {
		new Stage(800, 600, "Scene!", new TriangleScene()).run();
	}

}
