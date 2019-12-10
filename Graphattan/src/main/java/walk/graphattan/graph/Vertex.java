package walk.graphattan.graph;

/*
 * The Vertex is the basic building block of the CityGraph, representing a location 
 * at which a pedestrian's path can branch. Multiple Vertices may be present at the 
 * intersection of n streets/paths/other non-pedestrian routes, so several Vertices
 * in the CityGraph will have the same Intersection. 
 * The position of a Vertex represents the pedestrian's location, not the geographic
 * center of the Intersection of which the Vertex is a corner.  
 */
public class Vertex {

	public Vertex(double posX, double posY, Intersection intersection) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.intersection = intersection;
	}

	public Vertex() {
		super();
	}

	// distances in feet from starting position of grid traversal
	private double posX;
	private double posY;
	// Intersection of which the Vertex is a corner, may be shared by other Vertices.
	private Intersection intersection;

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public Intersection getIntersection() {
		return intersection;
	}

	public void setIntersection(Intersection intersection) {
		this.intersection = intersection;
	}

	// hashCode and equals are based on position values, for validation in CityGraph map
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(posX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(posY);
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
		Vertex other = (Vertex) obj;
		if (Double.doubleToLongBits(posX) != Double.doubleToLongBits(other.posX))
			return false;
		if (Double.doubleToLongBits(posY) != Double.doubleToLongBits(other.posY))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [posX=" + posX + ", posY=" + posY + ", intersection=" + intersection + "]";
	}

	
	
}
