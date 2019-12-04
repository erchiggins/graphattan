package walk.graphattan.graph;

public class Edge {
	
	public Edge(double weight, EdgeType type, Vertex source, Vertex destination) {
		super();
		this.weight = weight;
		this.type = type;
		this.source = source;
		this.destination = destination;
	}

	public Edge() {
		super();
	}

	// length of crossing or walkway in feet 
	private double weight;
	// whether edge represents a crossing or walkway
	private EdgeType type;
	// "source" and "destination" Vertices, although CityGraph is undirected
	private Vertex source;
	private Vertex destination;

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public EdgeType getType() {
		return type;
	}

	public void setType(EdgeType type) {
		this.type = type;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		return "Edge [weight=" + weight + ", type=" + type + ", source=" + source + ", destination=" + destination
				+ "]";
	}


}
