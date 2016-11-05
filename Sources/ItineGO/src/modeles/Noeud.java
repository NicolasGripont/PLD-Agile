package modeles;

/*
 * Classe modélisant une position sur la carte
 */
public class Noeud {
	/*
	 * identifiant unique du noeud
	 */
	private int id;
	/*
	 * Position horizontale
	 */
	private int x;
	/*
	 * Position verticake
	 */
	private int y;
	
	/*
	 * Constructeur de la classe
	 */
	public Noeud(int id, int x, int y) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(id >= 0) {
			this.id = id;
		} else {
			this.id = -1;
		}
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
