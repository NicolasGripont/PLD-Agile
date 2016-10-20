package modelesTests;

import static org.junit.Assert.*;
import org.junit.Test;
import modeles.Entrepot;
import modeles.Horaire;
import modeles.Noeud;

public class EntrepotTest {

	@Test
	public void testEntrepotNoeudIntIntInt() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, 10, 20, 29);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		assertEquals(res1,entrepot.toString());
	}
	
	@Test
	public void testEntrepotHoraireImpossible() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, 25, 70, 100);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=0:0:0]";
		assertEquals(res1,entrepot.toString());
	}

	@Test
	public void testEntrepotNoeudHoraire() {
		Horaire horaire = new Horaire(10, 20, 29);
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, horaire);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		assertEquals(res1, entrepot.toString());
	}

	@Test
	public void testEntrepotNoeudString() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "10:20:29");
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		assertEquals(res1, entrepot.toString());
	}
	
	@Test
	public void testEntrepotNoeudStringMauvaisFormat() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "helloworld!:20:20");
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=0:0:0]";
		assertEquals(res1, entrepot.toString());
	}

	@Test
	public void testSetNoeudNull() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "10:20:29");
		entrepot.setNoeud(null);
		String res1 = "Entrepot [noeud=Noeud [id=-1, x=0, y=0], horaireDepart=10:20:29]";
		assertEquals(res1, entrepot.toString());
	}

	@Test
	public void testSetHoraireDepart() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, "10:20:29");
		entrepot.setHoraireDepart(null);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=0:0:0]";
		assertEquals(res1, entrepot.toString());
	}

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
