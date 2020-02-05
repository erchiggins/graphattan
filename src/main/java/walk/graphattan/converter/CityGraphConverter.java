package walk.graphattan.converter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import walk.graphattan.dto.SimpleCityGraphDTO;
import walk.graphattan.dto.SimpleEdgeDTO;
import walk.graphattan.dto.SimpleVertexDTO;
import walk.graphattan.graph.CityGraph;
import walk.graphattan.graph.Edge;
import walk.graphattan.graph.Vertex;

public class CityGraphConverter {

	private static ObjectMapper om = new ObjectMapper();

	/*
	 * NOTE that this assumes a valid cityGraph object has been provided
	 */
	public static SimpleCityGraphDTO convertCityGraphToSimpleDTO(CityGraph cityGraph) {
		SimpleCityGraphDTO simpleCityGraph = new SimpleCityGraphDTO();
		Set<Vertex> vertices = ((Hashtable) cityGraph.getGraph()).keySet();
		for (Vertex v : vertices) {
			SimpleVertexDTO sv = conVertex(v);
			List<SimpleEdgeDTO> se = new ArrayList<>();
			for (Edge e : cityGraph.getGraph().get(v)) {
				se.add(conEdge(e));
			}
			simpleCityGraph.getGraph().put(sv, se);
		}
		simpleCityGraph.setStart(conVertex(cityGraph.getStart()));
		simpleCityGraph.setFinish(conVertex(cityGraph.getFinish()));
		return simpleCityGraph;
	}

	private static SimpleVertexDTO conVertex(Vertex vertex) {
		SimpleVertexDTO svd = new SimpleVertexDTO();
		svd.setPosX(vertex.getPosX());
		svd.setPosY(vertex.getPosY());
		return svd;
	}

	private static SimpleEdgeDTO conEdge(Edge edge) {
		SimpleEdgeDTO sed = new SimpleEdgeDTO();
		sed.setType(edge.getType());
		sed.setWeight(edge.getWeight());
		sed.setDestination(conVertex(edge.getDestination()));
		return sed;
	}

	public static String getJSON(SimpleCityGraphDTO simpleCityGraph) throws JsonProcessingException {
		String result = "{\"graph\":[";
		int numVertices = simpleCityGraph.getGraph().size();
		int v = 0;
		for (SimpleVertexDTO svd : simpleCityGraph.getGraph().keySet()) {
			// add vertex
			result += "{\"vertex\":" + om.writeValueAsString(svd) + ", \"edges\":[";
			int numEdges = simpleCityGraph.getGraph().get(svd).size();
			int e = 0;
			// add edge list
			for (SimpleEdgeDTO sed : simpleCityGraph.getGraph().get(svd)) {
				result += om.writeValueAsString(sed);
				e++;
				if (e < numEdges)
					result += ",";
			}
			v++;
			if (v < numVertices)
				result += "]},";
			else
				result += "]}";
		}
		result += "],\"start\":"+om.writeValueAsString(simpleCityGraph.getStart())+
				",\"finish\":"+om.writeValueAsString(simpleCityGraph.getFinish())+"}";
		return result;
	}

}
