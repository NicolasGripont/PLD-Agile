package controleur;

import java.util.LinkedList;

import exceptions.NonRespectPlagesHoraires;

public class ListeCommandes {

	private LinkedList<Commande> listeCommandes;
	private int position;
	
	public ListeCommandes() {
		listeCommandes = new LinkedList<>();
		position = 0;
	}
	
	public void ajouterCommande(Commande commande) throws NonRespectPlagesHoraires {
		listeCommandes.addFirst(commande);
		try {
			commande.doCode();
		} catch (NonRespectPlagesHoraires e) {
			listeCommandes.removeFirst();
			throw e;
		}
		for(int i = 0; i < position; i++) {
			listeCommandes.removeFirst();
		}
		position = 0;
	}
	
	public void undoAll() throws NonRespectPlagesHoraires {
		for(int i = position; i < listeCommandes.size(); i++) {
			undo();
		}
	}
	
	public void redoAll() throws NonRespectPlagesHoraires {
		for(int i = position; i >=0; i--) {
			redo();
		}
	}
	
	public void undo() throws NonRespectPlagesHoraires {
		if(position < listeCommandes.size()) {
			listeCommandes.get(position).undoCode();
			position++;
		}
	}
	
	public void redo() throws NonRespectPlagesHoraires {
		if(position-1 >= 0) {
			position--;
			listeCommandes.get(position).doCode();
		}
	}
}
