package controleur;

import java.util.LinkedList;

import exceptions.NonRespectPlagesHoraires;

public class ListeModifications {

	protected LinkedList<ListeCommandes> listeModifications;
	private int position;

	public ListeModifications() {
		this.listeModifications = new LinkedList<>();
		this.position = 0;
	}

	public void undoModifications() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		if (this.isUndoPossible()) {
			try {
				this.listeModifications.get(this.position).undoAll();
			} catch (NonRespectPlagesHoraires e) {
				exec = e;
			}
			this.position++;
		}
		if (exec != null) {
			throw exec;
		}
	}

	public void undoAllModifications() {
		for (int i = this.position; i < this.listeModifications.size(); i++) {
			try {
				this.undoModifications();
			} catch (NonRespectPlagesHoraires e) {
			}
		}
	}

	public void redoModifications() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		if (this.isRedoPossible()) {
			this.position--;
			try {
				this.listeModifications.get(this.position).redoAll();
			} catch (NonRespectPlagesHoraires e) {
				exec = e;
			}
		}
		if (exec != null) {
			throw exec;
		}
	}

	public void redoAllModifications() {
		for (int i = this.position; i < this.listeModifications.size(); i++) {
			try {
				this.redoModifications();
			} catch (NonRespectPlagesHoraires e) {
			}
		}
	}

	public void ajouterCommande(Commande commande) throws NonRespectPlagesHoraires {
		this.listeModifications.getFirst().ajouterCommande(commande);
	}

	public void creerModification() {
		for (int i = 0; i < this.position; i++) {
			this.listeModifications.removeFirst();
		}
		this.listeModifications.addFirst(new ListeCommandes());
		this.position = 0;
	}

	public void annulerModification() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		try {
			this.undoModifications();
		} catch (NonRespectPlagesHoraires e) {
			exec = e;
		}
		this.listeModifications.removeFirst();
		this.position = 0;
		if (exec != null) {
			throw exec;
		}
	}

	public void finModification() {
		if (this.listeModifications.getFirst().estVide() && !this.listeModifications.isEmpty()) {
			this.listeModifications.removeFirst();
		}
	}

	public boolean isUndoPossible() {
		return this.position < this.listeModifications.size();
	}

	public boolean isRedoPossible() {
		return ((this.position - 1) >= 0) && !this.listeModifications.isEmpty();
	}

	public void viderListeModifications() {
		this.listeModifications.clear();
		this.position = 0;
	}
}
