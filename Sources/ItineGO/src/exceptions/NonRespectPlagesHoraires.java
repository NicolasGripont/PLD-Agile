package exceptions;

/**
 * Classe modélisant une exception propagée pour le non respect des plages horaires des livraisons
 */
public class NonRespectPlagesHoraires  extends Exception {

	private static final long serialVersionUID = 1L;

	public NonRespectPlagesHoraires(String message) {
		super(message);
	}
	
	public NonRespectPlagesHoraires() {
		super("Erreur : Plages horaires non respectées");
	}
}
