package controleur;

import java.util.LinkedList;

import exceptions.NonRespectPlagesHoraires;

/**
 * Cette classe va gérer les commandes qui sont réalisées par 
 * l'utilisateur tant que celui-ci ne les sauvegarde pas.
 */
public class ListeCommandes {

	private final LinkedList<Commande> listeCommandes;
	private int position;

	public ListeCommandes() {
		this.listeCommandes = new LinkedList<>();
		this.position = 0;
	}

	/**
	 * Ajoute la commande à la liste et réalise le code de la commande ajoutée.
	 * 
	 * @param commande
	 *            : Commande que l'on veut ajouter.
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Undo toutes les modifications.
	 * 
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Redo toutes les modifications qui ont été undo.
	 * 
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Appel la méthode undo de la commande.
	 * 
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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

	/**
	 * Appel la méthode redo de la commande.
	 * 
	 * @throws NonRespectPlagesHoraires
	 *            : Erreur envoyée lorsque les plages ne sont plus respectées.
	 */
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
