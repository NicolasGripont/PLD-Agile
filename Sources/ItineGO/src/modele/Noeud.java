package modele;

public class Noeud {
	private int id;
	private int x;
	private int y;
	
	public Noeud(int id, int x, int y) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "Noeud [id=" + id + ", x=" + x + ", y=" + y + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noeud other = (Noeud) obj;
		if (id != other.id)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
