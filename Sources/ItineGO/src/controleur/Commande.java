package controleur;

import exceptions.NonRespectPlagesHoraires;

public abstract class Commande {

	public abstract void doCode() throws NonRespectPlagesHoraires;
	public abstract void undoCode() throws NonRespectPlagesHoraires;
}
