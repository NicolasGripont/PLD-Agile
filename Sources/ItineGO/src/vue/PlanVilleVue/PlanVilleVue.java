package vue.PlanVilleVue;

import modeles.Entrepot;
import modeles.Livraison;
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
	private int zoom = 1;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
		this.setStyle("-fx-background-color: rgb(240,237,230);");
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
		gc.setFill(new Color(0.980,0.929,0.847,1));
        gc.setStroke(new Color(0.980,0.929,0.847,1));
		for(Map.Entry<Integer, Noeud> n : noeuds.entrySet()) {
			if(n != null) {
				gc.fillOval(n.getValue().getX() * zoom, n.getValue().getY() * zoom, RAYON_NOEUD, RAYON_NOEUD);
			}
		}
	}
	
	public void dessineTroncon(Map<Pair<Noeud, Noeud>, Troncon> troncons) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Map.Entry<Pair<Noeud, Noeud>, Troncon> t : troncons.entrySet()) {
			if(t != null && t.getKey().first != null && t.getKey().second != null) {
				gc.strokeLine(t.getKey().first.getX() * zoom, t.getKey().first.getY() * zoom,
						t.getKey().second.getX() * zoom, t.getKey().second.getY() * zoom);
			}
		}
	}
	
	public void dessineLivraison(List<Livraison> livraisons) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        for(Livraison l : livraisons) {
			if(l != null && l.getNoeud() != null) {
				gc.fillOval(l.getNoeud().getX() * zoom, l.getNoeud().getY() * zoom, RAYON_LIVRAISON, RAYON_LIVRAISON);
			}
		}
	}
	
	public void dessineEntrepot(Entrepot entrepot) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        if(entrepot != null && entrepot.getNoeud() != null) {
        	gc.fillOval(entrepot.getNoeud().getX() * zoom, entrepot.getNoeud().getY() * zoom, RAYON_LIVRAISON, RAYON_LIVRAISON);
        }
	}
	
	public void dessineChemin(List<Troncon> chemins) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0,0.709,0.968,1));
        gc.setStroke(new Color(0,0.709,0.968,1));
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Troncon t : chemins) {
			if(t != null && t.getOrigine() != null && t.getDestination() != null) {
				gc.strokeLine(t.getOrigine().getX() * zoom, t.getOrigine().getY() * zoom, t.getDestination().getX() * zoom, t.getDestination().getY() * zoom);
			}
		}
	}
}
