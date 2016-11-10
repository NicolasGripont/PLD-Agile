package modelesTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import modeles.Noeud;
import modeles.Trajet;
import modeles.Troncon;

/**
 * Classe test unitaire de la classe Trajet
 *
 */
public class TrajetTest {

	/**
	 * Test du constructeur de la classe Trajet plusieurs noeud formant plusieurs tronçons formant un trajet
	 * 
	 * Resultat: La liste du trajet doit correspondre au resultat souhaité
	 */
	@Test
	public void testTrajet() {
		Noeud nDep = new Noeud(10,10,10);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nArr = new Noeud(20,20,20);
		
		List<Troncon> listTronconTest = new ArrayList<>();

		
		Troncon t1 = new Troncon("t1",10,10,nDep,nInt1);
		listTronconTest.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr);
		listTronconTest.add(t4);
		
		Trajet testTrajet = new Trajet(nDep,nArr,listTronconTest);
		String res = "Trajet [troncons=[Troncon [nomRue=t1, longueur=10, vitesse=10, origine=Noeud [id=10, x=10, y=10], destination=Noeud [id=12, x=12, y=12]],"
				+" Troncon [nomRue=t2, longueur=10, vitesse=10, origine=Noeud [id=12, x=12, y=12], destination=Noeud [id=15, x=15, y=15]],"
				+ " Troncon [nomRue=t3, longueur=10, vitesse=10, origine=Noeud [id=15, x=15, y=15], destination=Noeud [id=18, x=18, y=18]],"
				+ " Troncon [nomRue=t4, longueur=10, vitesse=10, origine=Noeud [id=18, x=18, y=18], destination=Noeud [id=20, x=20, y=20]]],"
				+ " depart=Noeud [id=10, x=10, y=10], arrive=Noeud [id=20, x=20, y=20]]";
		
		assertEquals(res,testTrajet.toString());
	}

	/**
	 * Test retournant les différents tronçons du trajet
	 * 
	 * Resultat: Les tronçons du trajet doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetTroncons() {
		Noeud nDep = new Noeud(10,10,10);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nArr = new Noeud(20,20,20);
		
		List<Troncon> listTronconTest = new ArrayList<>();

		
		Troncon t1 = new Troncon("t1",10,10,nDep,nInt1);
		listTronconTest.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr);
		listTronconTest.add(t4);
		
		Trajet testTrajet = new Trajet(nDep,nArr,listTronconTest);
		
		String res = "[Troncon [nomRue=t1, longueur=10, vitesse=10, origine=Noeud [id=10, x=10, y=10], destination=Noeud [id=12, x=12, y=12]],"
				+ " Troncon [nomRue=t2, longueur=10, vitesse=10, origine=Noeud [id=12, x=12, y=12], destination=Noeud [id=15, x=15, y=15]],"
				+ " Troncon [nomRue=t3, longueur=10, vitesse=10, origine=Noeud [id=15, x=15, y=15], destination=Noeud [id=18, x=18, y=18]],"
				+ " Troncon [nomRue=t4, longueur=10, vitesse=10, origine=Noeud [id=18, x=18, y=18], destination=Noeud [id=20, x=20, y=20]]]";

		assertEquals(res,testTrajet.getTroncons().toString());
	}
	
	/**
	 * Test retournant le départ du trajet
	 * 
	 * Resultat: L'horaire de départ du trajet doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetDepart() {
		Noeud nDep = new Noeud(10,10,10);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nArr = new Noeud(20,20,20);
		
		List<Troncon> listTronconTest = new ArrayList<>();

		
		Troncon t1 = new Troncon("t1",10,10,nDep,nInt1);
		listTronconTest.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr);
		listTronconTest.add(t4);
		
		Trajet testTrajet = new Trajet(nDep,nArr,listTronconTest);
		
		String res = "Noeud [id=10, x=10, y=10]";
		
		assertEquals(res,testTrajet.getDepart().toString());
	}

	/**
	 * Test retournant l'arrivée du trajet
	 * 
	 * Resultat: L'horaire de départ du trajet doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetArrive() {
		Noeud nDep = new Noeud(10,10,10);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nArr = new Noeud(20,20,20);
		
		List<Troncon> listTronconTest = new ArrayList<>();

		
		Troncon t1 = new Troncon("t1",10,10,nDep,nInt1);
		listTronconTest.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr);
		listTronconTest.add(t4);
		
		Trajet testTrajet = new Trajet(nDep,nArr,listTronconTest);
		
		String res = "Noeud [id=20, x=20, y=20]";
		
		assertEquals(res,testTrajet.getArrive().toString());
	}

	/**
	 * Test l'affichage texte du trajet
	 * 
	 * Resultat: L'affiche de trajet doit correspondre au resultat souhaité
	 */
	@Test
	public void testToString() {
		Noeud nDep = new Noeud(10,10,10);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nArr = new Noeud(20,20,20);
		
		List<Troncon> listTronconTest = new ArrayList<>();

		
		Troncon t1 = new Troncon("t1",10,10,nDep,nInt1);
		listTronconTest.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr);
		listTronconTest.add(t4);
		
		Trajet testTrajet = new Trajet(nDep,nArr,listTronconTest);
		String res = "Trajet [troncons=[Troncon [nomRue=t1, longueur=10, vitesse=10, origine=Noeud [id=10, x=10, y=10], destination=Noeud [id=12, x=12, y=12]],"
				+" Troncon [nomRue=t2, longueur=10, vitesse=10, origine=Noeud [id=12, x=12, y=12], destination=Noeud [id=15, x=15, y=15]],"
				+ " Troncon [nomRue=t3, longueur=10, vitesse=10, origine=Noeud [id=15, x=15, y=15], destination=Noeud [id=18, x=18, y=18]],"
				+ " Troncon [nomRue=t4, longueur=10, vitesse=10, origine=Noeud [id=18, x=18, y=18], destination=Noeud [id=20, x=20, y=20]]],"
				+ " depart=Noeud [id=10, x=10, y=10], arrive=Noeud [id=20, x=20, y=20]]";
		
		assertEquals(res,testTrajet.toString());
	}

	/**
	 * Test qui vérifie que 2 objets de type Trajet sont égaux et 2 autres sont différents
	 * 
	 * Resultat: Premier est égale, deuxiéme est différent
	 */
	@Test
	public void testEqualsObject() {
		Noeud nDep1 = new Noeud(10,10,10);
		Noeud nDep2 = new Noeud(15,15,15);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nInt4 = new Noeud(30,30,30);
		Noeud nArr1 = new Noeud(20,20,20);
		Noeud nArr2 = new Noeud(45,45,45);
		
		List<Troncon> listTronconTest1 = new ArrayList<>();
		List<Troncon> listTronconTest2 = new ArrayList<>();
		
		Troncon t1 = new Troncon("t1",10,10,nDep1,nInt1);
		listTronconTest1.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest1.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest1.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr1);
		listTronconTest1.add(t4);
		
		Troncon t10 = new Troncon("t10",10,10,nDep2,nInt4);
		listTronconTest1.add(t10);
		Troncon t20 = new Troncon("t20",10,10,nInt4,nArr2);
		listTronconTest1.add(t20);
		
		Trajet testTrajet1 = new Trajet(nDep1,nArr1,listTronconTest1);
		Trajet testTrajet2 = new Trajet(nDep1,nArr1,listTronconTest1);
		Trajet testTrajet3 = new Trajet(nDep2,nArr2,listTronconTest2);
		
		assertEquals(testTrajet1.toString(),testTrajet2.toString());
		assertNotEquals(testTrajet3.toString(), testTrajet1.toString());
	}

	/**
	 * Test retournant la durée du trajet
	 * 
	 * Resultat: L'affiche de l'entrepot doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetTemps() {
		Noeud nDep = new Noeud(10,10,10);
		Noeud nInt1 = new Noeud(12,12,12);
		Noeud nInt2 = new Noeud(15,15,15);
		Noeud nInt3 = new Noeud(18,18,18);
		Noeud nArr = new Noeud(20,20,20);
		
		List<Troncon> listTronconTest = new ArrayList<>();

		
		Troncon t1 = new Troncon("t1",10,10,nDep,nInt1);
		listTronconTest.add(t1);
		Troncon t2 = new Troncon("t2",10,10,nInt1,nInt2);
		listTronconTest.add(t2);
		Troncon t3 = new Troncon("t3",10,10,nInt2,nInt3);
		listTronconTest.add(t3);
		Troncon t4 = new Troncon("t4",10,10,nInt3,nArr);
		listTronconTest.add(t4);
		
		Trajet testTrajet = new Trajet(nDep,nArr,listTronconTest);
		
		int res = 4;
		
		assertEquals(res,testTrajet.getTemps());
	}

}
