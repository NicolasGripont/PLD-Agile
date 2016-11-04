package exceptions;

public class BadXmlFile extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadXmlFile(String message) {
		super(message);
	}
	
	public BadXmlFile() {
		super("Erreur : Fichier XML mal formé");
	}
}
