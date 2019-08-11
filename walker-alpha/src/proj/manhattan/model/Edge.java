package proj.manhattan.model;

public class Edge {
	
	public Edge(double weight, EdgeType type) {
		this();
		this.weight = weight;
		this.type = type;
	}

	public Edge() {
		super();
	}

	// length of street crossing or sidewalk in feet 
	private double weight;
	// whether edge represents a street crossing or sidewalk
	private EdgeType type;

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

	@Override
	public String toString() {
		return "Edge [weight=" + weight + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (type != other.type)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}
