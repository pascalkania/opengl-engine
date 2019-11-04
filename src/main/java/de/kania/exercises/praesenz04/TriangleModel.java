package de.kania.exercises.praesenz04;

import java.util.Arrays;
import java.util.List;

import de.kania.asset.Vertex;
import de.kania.shader.model.Model;

public class TriangleModel extends Model {

	@Override
	public void defineModel(List<Vertex> vertices, List<Integer> indices) {
		vertices.add(new Vertex(-0.8f, -0.8f, 0f, 1f, 1.0f, 1.0f, 1.0f));
		vertices.add(new Vertex(0.8f, -0.8f, 0f, 0f, 1f, 0f, 1.0f));
		vertices.add(new Vertex(0.0f, 0.8f, 0f, 0.6f, 0.6f, 0.6f, 1.0f));

		indices.addAll(Arrays.asList(0, 1, 2));
	}

}
