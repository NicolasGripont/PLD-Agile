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
		for(int i = 0; i < position; i++) {
			listeCommandes.removeFirst();
		}
		listeCommandes.addFirst(commande);
		position = 0;
		try {
			commande.doCode();
		} catch (NonRespectPlagesHoraires e) {
			//listeCommandes.removeFirst();
			throw e;
		}
	}
	
	public void undoAll() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		for(int i = position; i < listeCommandes.size(); i++) {
			try {
				undo();
			}catch(NonRespectPlagesHoraires e) {
				exec = e;
			}
		}
		if(exec != null)
		{
			throw exec;
		}
	}
	
	public void redoAll() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec = null;
		for(int i = position; i >=0; i--) {
			try {
				redo();
			} catch(NonRespectPlagesHoraires e) {
				exec = e;
			}
		}
		if(exec != null)
		{
			throw exec;
		}
	}
	
	public void undo() throws NonRespectPlagesHoraires {
		if(position < listeCommandes.size()) {
			listeCommandes.get(position).undoCode();
			position++;
		}
	}
	
	public void redo() throws NonRespectPlagesHoraires {
		if(position-1 >= 0 && !listeCommandes.isEmpty()) {
			position--;
			listeCommandes.get(position).doCode();
		}
	}
	
	public boolean estVide() {
		return listeCommandes.isEmpty();
	}
}
