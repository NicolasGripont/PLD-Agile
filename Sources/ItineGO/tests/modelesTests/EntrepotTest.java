package modelesTests;

import static org.junit.Assert.*;
import org.junit.Test;
import modeles.Entrepot;
import modeles.Horaire;
import modeles.Noeud;

/**
 * Classe test unitaire de la classe Entrepot
 *
 */
public class EntrepotTest {

	
	/**
	 * Test du constructeur de la classe Entrepot avec un noeud, une heure, une minute et une seconde
	 * 
	 * Resultat: L'affiche de l'entrepot doit correspondre au resultat souhaité
	 */
	@Test
	public void testEntrepotNoeudIntIntInt() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, 10, 20, 29);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		assertEquals(res1,entrepot.toString());
	}
	
	
	/**
	 * Test du constructeur avec une horaire impossible (Ex: 25h70min100s)
	 * 
	 * Resultat: L'heure de départ de l'entrepot doit être à 0h0m0s
	 */
	@Test
	public void testEntrepotHoraireImpossible() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, 25, 70, 100);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=0:0:0]";
		assertEquals(res1,entrepot.toString());
	}

	/**
	 * Test du constructeur avec une horaire de type Horaire
	 * 
	 * Resultat: L'affiche de l'entrepot doit correspondre au resultat souhaité
	 */
	@Test
	public void testEntrepotNoeudHoraire() {
		Horaire horaire = new Horaire(10, 20, 29);
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, horaire);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		assertEquals(res1, entrepot.toString());
	}

	/**
	 * Test de l'affichage texte de la classe Entrepot
	 * 
	 * Resultat: L'affiche de l'entrepot doit correspondre au resultat souhaité 
	 */
	@Test
	public void testEntrepotNoeudString() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "10:20:29");
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		assertEquals(res1, entrepot.toString());
	}
	
	/**
	 * Test de l'affichage texte de la classe Entrepot avec un mauvais format pour l'heure
	 * 
	 * Resultat: L'heure de départ de l'entrepot doit être à 0h0m0s
	 */
	@Test
	public void testEntrepotNoeudStringMauvaisFormat() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "helloworld!:20:20");
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=0:0:0]";
		assertEquals(res1, entrepot.toString());
	}

	/**
	 * Test lorsqu'on met le noeud de l'entrepot à null
	 * 
	 * Resultat: L'identifiant du noeud de l'entrepot doit être à -1 avec [0,0] comme coordonnée
	 */
	@Test
	public void testSetNoeudNull() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "10:20:29");
		entrepot.setNoeud(null);
		String res1 = "Entrepot [noeud=Noeud [id=-1, x=0, y=0], horaireDepart=10:20:29]";
		assertEquals(res1, entrepot.toString());
	}

	/**
	 * Test pour la modification de l'heure de départ de l'entrepot
	 * 
	 * Resultat: L'heure de départ doit être modifié à 0h0m0s
	 */
	@Test
	public void testSetHoraireDepart() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "10:20:29");
		entrepot.setHoraireDepart(null);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=0:0:0]";
		assertEquals(res1, entrepot.toString());
	}

	/**
	 * Test qui vérifie que 2 objets de type entrepot sont égales et 2 autres sont différents
	 * 
	 * Resultat: Premier est égale, deuxiéme est différent
	 */
	@Test
	public void testEqualsObject() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot1 = new Entrepot(noeud, "10:20:29");
		Entrepot entrepot2 = new Entrepot(noeud, "10:20:29");
		Noeud noeud2 = new Noeud(13,16,12);
		Entrepot entrepot3 = new Entrepot(noeud2, "10:20:29");
		assertEquals(entrepot1, entrepot2);
		assertNotEquals(entrepot1, entrepot3);
	}
}
