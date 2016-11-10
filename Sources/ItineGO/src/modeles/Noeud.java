package modeles;

/**
 * Classe modélisant une position sur la carte
 */
public class Noeud {
	/**
	 * identifiant unique du noeud
	 */
	private int id;
	/**
	 * Position horizontale
	 */
	private int x;
	/**
	 * Position verticake
	 */
	private int y;

	/**
	 * Constructeur de la classe
	 */
	public Noeud(int id, int x, int y) {
		this.setId(id);
		this.setX(x);
		this.setY(y);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if (id >= 0) {
			this.id = id;
		} else {
			this.id = -1;
		}
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Noeud [id=" + this.id + ", x=" + this.x + ", y=" + this.y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.id;
		result = (prime * result) + this.x;
		result = (prime * result) + this.y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Noeud other = (Noeud) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}
}
