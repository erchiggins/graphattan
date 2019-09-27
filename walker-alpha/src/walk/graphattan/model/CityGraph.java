package walk.graphattan.model;

import java.util.Map;

public class CityGraph {
	
	private Map<Vertex, Map<Vertex, Edge>> corners;

	public Map<Vertex, Map<Vertex, Edge>> getCorners() {
		return corners;
	}
	
	public void addVertex(Vertex vertex) {
		
	}
	
	public Map<Vertex, Edge> getAdjacent(Vertex vertex) {
		return corners.get(vertex);
	}
	
}
