package exceptions;

public class BadXmlPlan  extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadXmlPlan(String message) {
		super(message);
	}
	
	public BadXmlPlan() {
		super("Erreur : Fichier plan non valide");
	}
}
