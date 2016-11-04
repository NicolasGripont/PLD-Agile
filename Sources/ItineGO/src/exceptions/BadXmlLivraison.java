package exceptions;

public class BadXmlLivraison extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadXmlLivraison(String message) {
		super(message);
	}
	
	public BadXmlLivraison() {
		super("Erreur : Fichier livraison non valide");
	}
}
