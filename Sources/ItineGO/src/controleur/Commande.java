package controleur;

import exceptions.NonRespectPlagesHoraires;

/**
 * Classe mère des commandes permettant de faire une action et de gérer le undo/redo.
 *
 */
public abstract class Commande {

	public abstract void doCode() throws NonRespectPlagesHoraires;

	public abstract void undoCode() throws NonRespectPlagesHoraires;
}
