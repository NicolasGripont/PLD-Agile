package exceptions;

/**
 * Classe modélisant une exception propagée pour un fichier plan présentant des incohérences
 */
public class BadXmlPlan  extends Exception {

	private static final long serialVersionUID = 1L;

	public BadXmlPlan(String message) {
		super(message);
	}
	
	public BadXmlPlan() {
		super("Erreur : Fichier plan non valide");
	}
}
