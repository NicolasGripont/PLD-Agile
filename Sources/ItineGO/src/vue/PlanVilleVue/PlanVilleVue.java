package vue.PlanVilleVue;

import modeles.Noeud;
import modeles.Plan;
import modeles.Troncon;
import utility.Pair;

import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlanVilleVue extends Canvas {
	private int RAYON_LIVRAISON = 5;
	private int RAYON_NOEUD = 1;
	private int LARGEUR_TRONCON = 1;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
	}
	
	public void dessinePlan(Plan plan) {
		if(plan != null) {
			if(plan.getNoeuds() != null) {
				dessineNoeud(plan.getNoeuds());
			}
			if(plan.getTroncons() != null) {
				dessineTroncon(plan.getTroncons());
			}
		} else {
			System.err.println("plan is null");
		}
	}
	
	public void dessineNoeud(Map<Integer, Noeud> noeuds) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
		for(Map.Entry<Integer, Noeud> n : noeuds.entrySet()) {
			if(n != null) {
				gc.fillOval(n.getValue().getX(), n.getValue().getY(), RAYON_NOEUD, RAYON_NOEUD);
			}
		}
	}
	
	public void dessineTroncon(Map<Pair<Noeud, Noeud>, Troncon> troncons) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Map.Entry<Pair<Noeud, Noeud>, Troncon> t : troncons.entrySet()) {
			if(t != null && t.getKey().first != null && t.getKey().second != null) {
				gc.strokeLine(t.getKey().first.getX(), t.getKey().first.getY(), t.getKey().second.getX(), t.getKey().second.getY());
			}
		}
	}
	
	public void dessineLivraison() {
		
	}
	
	public void dessineEntrepot() {
		
	}
	
	public void dessineChemin() {
		
	}
}
