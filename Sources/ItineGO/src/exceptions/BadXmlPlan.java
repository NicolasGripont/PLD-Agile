package exceptions;

public class BadXmlPlan  extends Exception {
	public BadXmlPlan(String message) {
		super(message);
	}
	
	public BadXmlPlan() {
		super("Fichier plan non valide");
	}
}
