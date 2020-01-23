package walk.graphattan.dto;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SimpleCityGraphDTO {
	
	public SimpleCityGraphDTO() {
		super();
	}
	private Map<SimpleVertexDTO, List<SimpleEdgeDTO>> graph = new Hashtable<>();
	private int start_id; // id of starting vertex
	private int finish_id; // id of finishing vertex
	public Map<SimpleVertexDTO, List<SimpleEdgeDTO>> getGraph() {
		return graph;
	}
	public void setGraph(Map<SimpleVertexDTO, List<SimpleEdgeDTO>> graph) {
		this.graph = graph;
	}
	public int getStart_id() {
		return start_id;
	}
	public void setStart_id(int start_id) {
		this.start_id = start_id;
	}
	public int getFinish_id() {
		return finish_id;
	}
	public void setFinish_id(int finish_id) {
		this.finish_id = finish_id;
	}

}
