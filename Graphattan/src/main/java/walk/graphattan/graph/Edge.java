package walk.graphattan.graph;

/*
 * An Edge connects two Vertices, its "source" and "destination."
 * As the CityGraph is undirected, every Edge has a counterpart with its "source" and 
 * "destination" flipped so that the traversal may occur in either direction.
 * The weight of the Edge denotes its length in feet, which is not necessarily the 
 * same as the difference in (x,y) positions of the source and destination Vertices.
 * The Edge may either be a CROSSING or a SIDEWALK, which has consequences for its 
 * availability as a path during a traversal of the map.
 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (type != other.type)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}
