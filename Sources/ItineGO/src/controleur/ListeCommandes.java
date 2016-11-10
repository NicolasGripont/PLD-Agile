package controleur;

import java.util.LinkedList;

import exceptions.NonRespectPlagesHoraires;

public class ListeCommandes {

	private final LinkedList<Commande> listeCommandes;
	private int position;

	public ListeCommandes() {
		this.listeCommandes = new LinkedList<>();
		this.position = 0;
	}

	public void ajouterCommande(Commande commande) throws NonRespectPlagesHoraires {
		for (int i = 0; i < this.position; i++) {
			this.listeCommandes.removeFirst();
		}
		this.listeCommandes.addFirst(commande);
		this.position = 0;
		try {
			commande.doCode();
		} catch (NonRespectPlagesHoraires e) {
			throw e;
		}
	}

	public void undoAll() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		for (int i = this.position; i < this.listeCommandes.size(); i++) {
			try {
				this.undo();
			} catch (NonRespectPlagesHoraires e) {
				exec = e;
			}
		}
		if (exec != null) {
			throw exec;
		}
	}

	public void redoAll() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		for (int i = this.position; i >= 0; i--) {
			try {
				this.redo();
			} catch (NonRespectPlagesHoraires e) {
				exec = e;
			}
		}
		if (exec != null) {
			throw exec;
		}
	}

	public void undo() throws NonRespectPlagesHoraires {
		if (this.position < this.listeCommandes.size()) {
			NonRespectPlagesHoraires exec = null;
			try {
				this.listeCommandes.get(this.position).undoCode();
			} catch (NonRespectPlagesHoraires e) {
				exec = e;
			}
			this.position++;
			if (exec != null) {
				throw exec;
			}
		}
	}

	public void redo() throws NonRespectPlagesHoraires {
		if (((this.position - 1) >= 0) && !this.listeCommandes.isEmpty()) {
			this.position--;
			this.listeCommandes.get(this.position).doCode();
		}
	}

	public boolean estVide() {
		return this.listeCommandes.isEmpty();
	}
}
