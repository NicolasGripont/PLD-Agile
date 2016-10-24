package exceptions;

public class BadXmlFile extends Exception {
	public BadXmlFile(String message) {
		super(message);
	}
	
	public BadXmlFile() {
		super("Fichier XML non valide");
	}
}
