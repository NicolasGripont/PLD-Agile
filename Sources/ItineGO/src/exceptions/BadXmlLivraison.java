package exceptions;

public class BadXmlLivraison extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadXmlLivraison(String message) {
		super("Livraisons non valides");
	}
	
	public BadXmlLivraison() {
		super("Fichier livraison non valide");
	}
}
