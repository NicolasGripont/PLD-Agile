package modelesTests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import modeles.ParseurPlan;
import modeles.ParseurLivraison;
import modeles.Plan;
import vue.glisserDeposerFichierVue.GlisserDeposerFichierVue;

/**
 * Classe test unitaire de la classe ParseurLivraison
 *
 */
public class ParseurLivraisonTest {

	/**
	 * Test du Parseur sur un fichier de Livraison correct
	 * 
	 * Resultat: Le fichier est parsé 
	 */
	@Test
	public void testParseurLivraison() {
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-4.xml";

		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){}
		
		int resNbLivraison = 4;
		assertEquals(resNbLivraison,planTest.getLivraisons().size());
		
		int resId = 21;
		assertEquals(resId,planTest.getEntrepot().getNoeud().getId());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison correct ayant des plages horaire
	 * 
	 * Resultat: Le fichier est parsé 
	 */
	@Test
	public void testParseurLivraisonAvecPlage() {
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-9-TW.xml";

		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){}
		
		int resNbLivraison = 9;
		assertEquals(resNbLivraison,planTest.getLivraisons().size());
		
		int resId = 6;
		assertEquals(resId,planTest.getEntrepot().getNoeud().getId());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison au mauvais format
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testMauvaisFormat()
	{
		String nomFichierTest = "./tests/assetsForTests/livraisons5x5-4.txt";

		File xmlFile = new File(nomFichierTest);
		GlisserDeposerFichierVue testVue = new GlisserDeposerFichierVue();
		testVue.addExtensionAcceptee(".xml");
		
		assertEquals(false,testVue.estFichierAccepte(xmlFile));
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison ayant un noeud pour l'entrepot inconnu
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testEntrepotInconnue()
	{
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-4-UknEntrepot.xml";
		
		String res1 = "Erreur : Fichier livraison non valide";
		int res2 = 0;
		String resTest = "";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){
			resTest = e.getMessage();
		}
		
		assertEquals(res1,resTest);
		assertEquals(res2,planTest.getLivraisons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison n'ayant pas de livraison
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testPasDeLivraison()
	{
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-4-PasDeLivraison.xml";
		
		String res1 = "Erreur : Fichier livraison non valide";
		int res2 = 0;
		String resTest = "";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){
			resTest = e.getMessage();
		}
		
		assertEquals(res1,resTest);
		assertEquals(res2,planTest.getLivraisons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison ayant un identifiant d'entrepot égale à -1
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testEntrepotMoinsUn()
	{
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-4-EntrepotMoinsUn.xml";
		
		String res1 = "Erreur : Fichier livraison non valide";
		int res2 = 0;
		String resTest = "";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){
			resTest = e.getMessage();
		}
		
		assertEquals(res1,resTest);
		assertEquals(res2,planTest.getLivraisons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison ayant un identifiant de livraison égale à -1
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testLivraisonMoinsUn()
	{
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-4-LivraisonMoinsUn.xml";
		
		String res1 = "Erreur : Fichier livraison non valide";
		int res2 = 0;
		String resTest = "";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){
			resTest = e.getMessage();
		}
		assertEquals(res1,resTest);
		assertEquals(res2,planTest.getLivraisons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison ayant un noeud de livraison qui n'exite pas 
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testIdNoeudInexistant()
	{
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-4-IDNoeudInexistant.xml";
		
		String res1 = "Erreur : Fichier livraison non valide";
		int res2 = 0;
		String resTest = "";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){
			resTest = e.getMessage();
		}
		
		assertEquals(res1,resTest);
		assertEquals(res2,planTest.getLivraisons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier de Livraison correct ayant une plage horaire de début plus tard
	 * que la plage horaire de fin
	 * 
	 * Resultat: Le fichier est parsé 
	 */
	@Test
	public void testParseurLivraisonAvecPlageDébutSupPlageFin() {
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		String nomFichierLivraisonTest = "./tests/assetsForTests/livraisons5x5-9-TW-PDAvantPF.xml";

		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){}
//		assertEquals("0:0:0",planTest.getTournee().getLivraisonAtPos(23).getDebutPlage());
		
		int resNbLivraison = 9;
		assertEquals(resNbLivraison,planTest.getLivraisons().size());
		
		int resId = 6;
		assertEquals(resId,planTest.getEntrepot().getNoeud().getId());
	}

}
