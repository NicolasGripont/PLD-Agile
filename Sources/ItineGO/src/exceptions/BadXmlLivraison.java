package exceptions;

/**
 * Classe modélisant une exception propagée pour un fichier de livraison présentant des incohérences
 */
public class BadXmlLivraison extends Exception {

	private static final long serialVersionUID = 1L;

	public BadXmlLivraison(String message) {
		super(message);
	}
	
	public BadXmlLivraison() {
		super("Erreur : Fichier livraison non valide");
	}
}
