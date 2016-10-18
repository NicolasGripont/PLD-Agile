package modelesTests;

import static org.junit.Assert.*;
import org.hamcrest.*;
import org.junit.Test;
import modeles.Entrepot;
import modeles.Noeud;

public class EntrepotTest {

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEntrepotNoeudIntIntInt() {
		Noeud noeud = new Noeud(12,12,12);
		Entrepot entrepot = new Entrepot(noeud, 10, 20, 29);
		String res1 = "Entrepot [noeud=Noeud [id=12, x=12, y=12], horaireDepart=10:20:29]";
		String res2 = entrepot.toString();
		assertEquals(res1,res2);
	}

	@Test
	public void testEntrepotNoeudHoraire() {
		fail("Not yet implemented");
	}

	@Test
	public void testEntrepotNoeudString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNoeud() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNoeud() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHoraireDepart() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetHoraireDepart() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

}
