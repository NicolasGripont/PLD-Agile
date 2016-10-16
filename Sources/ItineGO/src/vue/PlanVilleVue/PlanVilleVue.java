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
	private int RAYON_NOEUD = 0;
	private int LARGEUR_TRONCON = 3;
	private double zoom = 1;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
		this.setStyle("-fx-background-color: rgb(240,237,230);");
	}
	
	public void dessinePlan(Plan plan) {
		if(plan != null) {
			calculerZoom(plan.getNoeuds());
			System.out.println("Zoom : " + zoom);
			if(plan.getNoeuds() != null) {
				dessineNoeud(plan.getNoeuds());
			}
			if(plan.getTroncons() != null) {
				dessineTroncon(plan.getTroncons());
			}
			if(plan.getEntrepot() != null) {
				dessineEntrepot(plan.getEntrepot());
			}
			if(plan.getLivraisons() != null) {
				dessineLivraison(plan.getLivraisons());
			}
		} else {
			System.err.println("plan is null");
		}
	}
	
	private void dessineNoeud(Map<Integer, Noeud> noeuds) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.859,0.839,0.808,1));
        gc.setStroke(Color.WHITE);
		for(Map.Entry<Integer, Noeud> n : noeuds.entrySet()) {
			if(n != null) {
				gc.fillOval(n.getValue().getX() * zoom, n.getValue().getY() * zoom, RAYON_NOEUD, RAYON_NOEUD);
			}
		}
	}
	
	private void dessineTroncon(Map<Pair<Noeud, Noeud>, Troncon> troncons) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.859,0.839,0.808,1));
        gc.setStroke(Color.WHITE);
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Map.Entry<Pair<Noeud, Noeud>, Troncon> t : troncons.entrySet()) {
			if(t != null && t.getKey().first != null && t.getKey().second != null) {
				gc.strokeLine(t.getKey().first.getX() * zoom, t.getKey().first.getY() * zoom,
						t.getKey().second.getX() * zoom, t.getKey().second.getY() * zoom);
			}
		}
	}
	
	public void dessineLivraison(Map<Noeud, Livraison> livraisons) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        for(Map.Entry<Noeud, Livraison> l : livraisons.entrySet()) {
			if(l != null && l.getKey() != null) {
				gc.fillOval(l.getKey().getX() * zoom, l.getKey().getY() * zoom, RAYON_LIVRAISON, RAYON_LIVRAISON);
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
	
	private void dessineChemin(List<Troncon> chemins) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0,0.709,0.968,1));
        gc.setStroke(new Color(0,0.709,0.968,1));
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Troncon t : chemins) {
			if(t != null && t.getOrigine() != null && t.getDestination() != null) {
				gc.strokeLine(t.getOrigine().getX() * zoom, t.getOrigine().getY() * zoom,
						t.getDestination().getX() * zoom, t.getDestination().getY() * zoom);
			}
		}
	}
	
	private void calculerZoom(Map<Integer, Noeud> noeuds) {
		if(noeuds != null) {
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			for(Map.Entry<Integer, Noeud> n : noeuds.entrySet()) {
				if(n != null) {
					if(n.getValue().getX() <= minX) {
						minX = n.getValue().getX();
					} else if(n.getValue().getX() >= maxX) {
						maxX = n.getValue().getX();
					}
				}
			}
			double widthMap = maxX+minX;
			double width = this.getWidth();
			this.zoom = width/(widthMap) ;
		} else {
			this.zoom = 1;
		}
	}
}
