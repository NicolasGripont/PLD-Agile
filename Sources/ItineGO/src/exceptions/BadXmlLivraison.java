package exceptions;

public class BadXmlLivraison extends Exception {
	public BadXmlLivraison(String message) {
		super("Livraisons non valides");
	}
	
	public BadXmlLivraison() {
		super("Fichier livraison non valide");
	}
}
