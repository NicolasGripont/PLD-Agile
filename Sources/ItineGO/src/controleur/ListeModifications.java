package controleur;

import java.util.LinkedList;

import exceptions.NonRespectPlagesHoraires;

/**
 * Classe qui va gérer la liste de ListeCommande qui contiendra les modifications qui seront sauvegardées. 
 */
public class ListeModifications {

	protected LinkedList<ListeCommandes> listeModifications;
	/**
	 * Correspond à la position de la dernière liste de modification sauvegardée.
	 */
	private int position;

	public ListeModifications() {
		this.listeModifications = new LinkedList<>();
		this.position = 0;
	}

	/**
	 * Appel la métode undoAll de ListeCommande.
	 * 
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Annule toutes les modifications.
	 */
	public void undoAllModifications() {
		for (int i = this.position; i < this.listeModifications.size(); i++) {
			try {
				this.undoModifications();
			} catch (NonRespectPlagesHoraires e) {
			}
		}
	}

	/**
	 * Appel la métode redoAll de ListeCommande.
	 * 
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Redo toutes les modifications.
	 */
	public void redoAllModifications() {
		for (int i = this.position; i < this.listeModifications.size(); i++) {
			try {
				this.redoModifications();
			} catch (NonRespectPlagesHoraires e) {
			}
		}
	}

	/**
	 * Ajoute la commande à la liste de commande.
	 * 
	 * @param commande
	 *            : Commande a ajouté.
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
	public void ajouterCommande(Commande commande) throws NonRespectPlagesHoraires {
		this.listeModifications.getFirst().ajouterCommande(commande);
	}

	/**
	 * Créer une nouvelle liste de commande lorsque l'on clique sur le bouton modifier.
	 */
	public void creerModification() {
		for (int i = 0; i < this.position; i++) {
			this.listeModifications.removeFirst();
		}
		this.listeModifications.addFirst(new ListeCommandes());
		this.position = 0;
	}

	/**
	 * Annule toutes les modifications.
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Vérifie si le undo est possible ou non.
	 */
	public boolean isUndoPossible() {
		return this.position < this.listeModifications.size();
	}

	/**
	 * Vérifie si le redo est possible ou non.
	 */
	public boolean isRedoPossible() {
		return ((this.position - 1) >= 0) && !this.listeModifications.isEmpty();
	}

	/**
	 * Vide la liste des modifications.
	 */
	public void viderListeModifications() {
		this.listeModifications.clear();
		this.position = 0;
	}
}
