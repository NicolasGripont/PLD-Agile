package modelesTests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import modeles.ParseurPlan;
import modeles.ParseurLivraison;
import modeles.Plan;

public class ParseurLivraisonTest {

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
	
	@Test
	public void testMauvaisFormat()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/livraisons5x5-4.txt";
		String resTest ="";
		String res1 = "exceptions.BadXmlFile: Erreur : Fichier XML mal formé";

		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.toString();
//			System.err.println(resTest);
		}
		
		assertEquals(res1,resTest);
	}
	
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
//		System.out.println(planTest.getLivraisons().size());
		assertEquals(res1,resTest);
//		assertEquals(res2,planTest.getLivraisons().size());
	}
	
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

}
