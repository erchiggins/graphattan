package walk.graphattan.graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/*
 * CityGraph is an undirected weighted graph representing the pedestrian walkways 
 * available in section of a city, implemented with adjacency lists.
 * The position of a Vertex must be unique within the CityGraph, enforcing a strictly
 * two-dimensional (though not necessarily simply connected) graph. Vertex objects form
 * the entry set of the CityGraph map, so duplicate Vertices cannot be added. 
 * A list of Edges accompanies each Vertex. Duplicate Edges are allowed (parallel paths). 
 */
public class CityGraph {

	// Hashtable is chosen to prevent insertion of null keys
	private Map<Vertex, List<Edge>> graph = new Hashtable<Vertex, List<Edge>>();

	public Map<Vertex, List<Edge>> getGraph() {
		return graph;
	}

	// returns true if Vertex was not already present and was added to the CityGraph
	public boolean addVertex(Vertex vertex) {
		if (vertex == null || graph.containsKey(vertex)) {
			return false;
		} else {
			graph.put(vertex, new ArrayList<Edge>());
			return true;
		}
	}

	// returns true if both Vertices are already present in the CityGraph
	public boolean addEdge(Edge edge) {
		if (edge == null || edge.getWeight() < 0)
			return false;
		Vertex source = edge.getSource();
		Vertex destination = edge.getDestination();
		if (source == null || destination == null)
			return false;
		List<Edge> sourceEdges = graph.get(source);
		List<Edge> destinationEdges = graph.get(destination);
		if (sourceEdges != null && destinationEdges != null) {
			sourceEdges.add(edge);
			// flip edge and source to complete undirected Edge addition
			destinationEdges.add(new Edge(edge.getWeight(), edge.getType(), edge.getDestination(), edge.getSource()));
			return true;
		} else {
			return false;
		}
	}
}
