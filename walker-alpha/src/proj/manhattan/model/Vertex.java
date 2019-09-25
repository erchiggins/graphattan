package proj.manhattan.model;

public class Vertex {

	public Vertex() {
		super();
	}

	public Vertex(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}

	// distances in feet from origin of traversal
	private int posX;
	private int posY;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		return "Vertex [posX=" + posX + ", posY=" + posY + "]";
	}

}
