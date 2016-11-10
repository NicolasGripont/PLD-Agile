package exceptions;

/**
 * Classe modélisant une exception propagée pour un fichier xml mal formé
 */
public class BadXmlFile extends Exception {

	private static final long serialVersionUID = 1L;

	public BadXmlFile(String message) {
		super(message);
	}

	public BadXmlFile() {
		super("Erreur : Fichier XML mal formé");
	}
}
