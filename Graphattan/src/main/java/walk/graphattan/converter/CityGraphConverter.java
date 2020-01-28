package walk.graphattan.converter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import walk.graphattan.dto.SimpleCityGraphDTO;
import walk.graphattan.dto.SimpleEdgeDTO;
import walk.graphattan.dto.SimpleVertexDTO;
import walk.graphattan.graph.CityGraph;
import walk.graphattan.graph.Edge;
import walk.graphattan.graph.Vertex;

public class CityGraphConverter {
	
	private int vertexIdSource = 1;

	/*
	 * NOTE that this assumes a valid cityGraph object has been provided
	 */
	public SimpleCityGraphDTO convertCityGraphToSimpleDTO(CityGraph cityGraph) {
		SimpleCityGraphDTO simpleCityGraph = new SimpleCityGraphDTO();
		Set<Vertex> vertices = ((Hashtable)cityGraph.getGraph()).keySet();
		for (Vertex v : vertices) {
			SimpleVertexDTO sv = conVertex(v);
			List<SimpleEdgeDTO> se = new ArrayList<>();
			for (Edge e : cityGraph.getGraph().get(v)) {
				// TODO add edge conversion
			}
		}
		return simpleCityGraph;
	}

	private SimpleVertexDTO conVertex(Vertex vertex) {
		SimpleVertexDTO svd = new SimpleVertexDTO();
		vertexIdSource ++;
		svd.setId(vertexIdSource);
		svd.setPosX(vertex.getPosX());
		svd.setPosY(vertex.getPosY());
		return svd;
	}

}
