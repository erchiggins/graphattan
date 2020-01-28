package walk.graphattan.dto;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import walk.graphattan.graph.Vertex;

public class SimpleCityGraphDTO {
	
	public SimpleCityGraphDTO() {
		super();
	}
	private Map<SimpleVertexDTO, List<SimpleEdgeDTO>> graph = new Hashtable<>();
	private Vertex start; // starting vertex
	private Vertex finish; // finishing vertex
	public Map<SimpleVertexDTO, List<SimpleEdgeDTO>> getGraph() {
		return graph;
	}
	public void setGraph(Map<SimpleVertexDTO, List<SimpleEdgeDTO>> graph) {
		this.graph = graph;
	}
	public Vertex getStart() {
		return start;
	}
	public void setStar(Vertex start) {
		this.start = start;
	}
	public Vertex getFinish() {
		return finish;
	}
	public void setFinish(Vertex finish) {
		this.finish = finish;
	}

}
