package proj.manhattan.model;

public class Vertex {
	
	public Vertex() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vertex(int posX, int posY, Edge edgeNorth, Edge edgeSouth, Edge edgeEast, Edge edgeWest) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.edgeNorth = edgeNorth;
		this.edgeSouth = edgeSouth;
		this.edgeEast = edgeEast;
		this.edgeWest = edgeWest;
	}
	// coordinates in generated grid
	private int posX;
	private int posY;
	// edges connecting to adjacent vertices
	private Edge edgeNorth;
	private Edge edgeSouth;
	private Edge edgeEast;
	private Edge edgeWest;
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public Edge getEdgeNorth() {
		return edgeNorth;
	}
	public void setEdgeNorth(Edge edgeNorth) {
		this.edgeNorth = edgeNorth;
	}
	public Edge getEdgeSouth() {
		return edgeSouth;
	}
	public void setEdgeSouth(Edge edgeSouth) {
		this.edgeSouth = edgeSouth;
	}
	public Edge getEdgeEast() {
		return edgeEast;
	}
	public void setEdgeEast(Edge edgeEast) {
		this.edgeEast = edgeEast;
	}
	public Edge getEdgeWest() {
		return edgeWest;
	}
	public void setEdgeWest(Edge edgeWest) {
		this.edgeWest = edgeWest;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edgeEast == null) ? 0 : edgeEast.hashCode());
		result = prime * result + ((edgeNorth == null) ? 0 : edgeNorth.hashCode());
		result = prime * result + ((edgeSouth == null) ? 0 : edgeSouth.hashCode());
		result = prime * result + ((edgeWest == null) ? 0 : edgeWest.hashCode());
		result = prime * result + posX;
		result = prime * result + posY;
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
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Vertex [posX=" + posX + ", posY=" + posY + ", edgeNorth=" + edgeNorth + ", edgeSouth=" + edgeSouth
				+ ", edgeEast=" + edgeEast + ", edgeWest=" + edgeWest + "]";
	}
	
}
