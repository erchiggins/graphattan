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
	private SimpleVertexDTO start; // starting vertex
	private SimpleVertexDTO finish; // finishing vertex

	public Map<SimpleVertexDTO, List<SimpleEdgeDTO>> getGraph() {
		return graph;
	}

	public void setGraph(Map<SimpleVertexDTO, List<SimpleEdgeDTO>> graph) {
		this.graph = graph;
	}

	public SimpleVertexDTO getStart() {
		return start;
	}

	public void setStart(SimpleVertexDTO start) {
		this.start = start;
	}

	public SimpleVertexDTO getFinish() {
		return finish;
	}

	public void setFinish(SimpleVertexDTO finish) {
		this.finish = finish;
	}

}
