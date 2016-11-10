package vue.planVilleVue;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import modeles.Entrepot;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import modeles.Trajet;
import modeles.Troncon;
import utility.Pair;
import vue.gestionVue.GestionVue;

/**
 * Classe permettant d'afficher le plan de la ville et toutes les informations
 * qu'il contient Noeuds, tronçons, livraisons, entrepôt, tournée calculée...
 */
public class PlanVilleVue extends Canvas {
	private final int RAYON_LIVRAISON = 15;
	private final int RAYON_NOEUD = 10;
	private final int LARGEUR_TRONCON = 3;
	private double zoom = 1;
	private double offsetX = 0;
	private double offsetY = 0;
	private final double pointerMargin = 10;
	private Noeud noeudSelectionned = null;
	private Troncon tronconSelectionned = null;
	private Plan plan;
	private boolean modeAjouterLivraison = false;
	private GestionVue vue;
	private Livraison livraisonSelectionned = null;

	public PlanVilleVue(double width, double height) {
		super(width, height);
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				PlanVilleVue.this.evenementSourisClick(mouseEvent.getX(), mouseEvent.getY());
			}
		});
		this.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				PlanVilleVue.this.evenementSourisMove(mouseEvent.getX(), mouseEvent.getY());
			}
		});
	}

	public PlanVilleVue(double width, double height, GestionVue vue) {
		super(width, height);
		this.setVue(vue);
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				PlanVilleVue.this.evenementSourisClick(mouseEvent.getX(), mouseEvent.getY());
			}
		});
		this.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				PlanVilleVue.this.evenementSourisMove(mouseEvent.getX(), mouseEvent.getY());
			}
		});
		this.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				PlanVilleVue.this.noeudSelectionned = null;
				PlanVilleVue.this.tronconSelectionned = null;
				PlanVilleVue.this.dessinerPlan(PlanVilleVue.this.plan);
			}
		});
	}

	public void livraisonSelected(Livraison livraison) {
		this.noeudSelectionned = null;
		this.tronconSelectionned = null;
		this.livraisonSelectionned = livraison;
		this.dessinerPlan(this.plan);
		this.livraisonIsClicked(livraison);
	}

	public void evenementSourisClick(double x, double y) {
		this.noeudSelectionned = null;
		this.tronconSelectionned = null;
		if (this.plan != null) {
			this.dessinerPlan(this.plan);
			// Recherche d'un noeud, livraison ou entrepot
			if (this.plan.getNoeuds() != null) {
				for (Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
					if ((n != null) && (n.getValue().getId() != -1)) {
						double NoeudX = ((n.getValue().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
						double NoeudY = ((n.getValue().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
						double XCenter = x - (this.pointerMargin / 2);
						double YCenter = y - (this.pointerMargin / 2);
						if ((Math.abs(NoeudX - XCenter) <= this.pointerMargin)
								&& (Math.abs(NoeudY - YCenter) <= this.pointerMargin)) {
							if ((this.plan.getEntrepot() != null)
									&& this.plan.getEntrepot().getNoeud().equals(n.getValue())) {
								this.entrepotIsClicked(this.plan.getEntrepot());
								if (this.vue != null) {
									this.vue.selectionneNoeud(this.plan.getEntrepot().getNoeud());
								}
							} else {
								for (Map.Entry<Integer, Livraison> l : this.plan.getLivraisons().entrySet()) {
									if (l.getKey().equals(n.getValue().getId())) {
										this.livraisonIsClicked(l.getValue());
										if (this.vue != null) {
											this.vue.selectionneNoeud(l.getValue().getNoeud());
										}
										return;
									}
								}
								this.noeudIsClicked(n.getValue());
								if (this.vue != null) {
									this.vue.selectionneNoeud(n.getValue());
								}
							}
							return;
						}
					}
				}
			}
			// Recherche d'un troncon
			if (this.plan.getTroncons() != null) {
				for (Map.Entry<Pair<Noeud, Noeud>, Troncon> troncon : this.plan.getTroncons().entrySet()) {
					if (troncon != null) {
						double XCenter = x - (this.pointerMargin / 2);
						double YCenter = y - (this.pointerMargin / 2);
						double Ax = ((troncon.getKey().first.getX() * this.zoom) + this.offsetX)
								- (this.RAYON_NOEUD / 2);
						double Ay = ((troncon.getKey().first.getY() * this.zoom) + this.offsetY)
								- (this.RAYON_NOEUD / 2);
						double Bx = ((troncon.getKey().second.getX() * this.zoom) + this.offsetX)
								- (this.RAYON_NOEUD / 2);
						double By = ((troncon.getKey().second.getY() * this.zoom) + this.offsetY)
								- (this.RAYON_NOEUD / 2);
						double Ux = Bx - Ax;
						double Uy = By - Ay;
						double ACx = XCenter - Ax;
						double ACy = YCenter - Ay;
						double numerateur = (Ux * ACy) - (Uy * ACx);
						if (numerateur < 0) {
							numerateur = -numerateur;
						}
						double denominateur = Math.sqrt((Ux * Ux) + (Uy * Uy));
						double CI = numerateur / denominateur;
						if (CI <= this.pointerMargin) {
							double ABx = Bx - Ax;
							double ABy = By - Ay;
							double BCx = XCenter - Bx;
							double BCy = YCenter - By;
							double pscal1 = (ABx * ACx) + (ABy * ACy);
							double pscal2 = ((-ABx) * BCx) + ((-ABy) * BCy);
							if ((pscal1 >= 0) && (pscal2 >= 0)) {
								this.tronconIsClicked(troncon.getValue());
								return;
							}
						}
					}
				}
			}
		}
	}

	public void evenementSourisMove(double x, double y) {
		if (this.plan != null) {
			this.dessinerPlan(this.plan);
			// Recherche d'un noeud, livraison ou entrepot
			if (this.plan.getNoeuds() != null) {
				for (Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
					if ((n != null) && (n.getValue().getId() != -1)) {
						double NoeudX = ((n.getValue().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
						double NoeudY = ((n.getValue().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
						double XCenter = x - (this.pointerMargin / 2);
						double YCenter = y - (this.pointerMargin / 2);
						if ((Math.abs(NoeudX - XCenter) <= this.pointerMargin)
								&& (Math.abs(NoeudY - YCenter) <= this.pointerMargin)) {
							if ((this.plan.getEntrepot() != null)
									&& this.plan.getEntrepot().getNoeud().equals(n.getValue())) {
								this.entrepotIsFocused(this.plan.getEntrepot());
							} else {
								for (Map.Entry<Integer, Livraison> l : this.plan.getLivraisons().entrySet()) {
									if (l.getKey().equals(n.getValue().getId())) {
										this.livraisonIsFocused(l.getValue());
										return;
									}
								}
								this.noeudIsFocused(n.getValue());
							}
							return;
						}
					}
				}
			}
			// Recherche d'un troncon
			if (this.plan.getTroncons() != null) {
				for (Map.Entry<Pair<Noeud, Noeud>, Troncon> troncon : this.plan.getTroncons().entrySet()) {
					if (troncon != null) {
						double XCenter = x - (this.pointerMargin / 2);
						double YCenter = y - (this.pointerMargin / 2);
						double Ax = ((troncon.getKey().first.getX() * this.zoom) + this.offsetX)
								- (this.RAYON_NOEUD / 2);
						double Ay = ((troncon.getKey().first.getY() * this.zoom) + this.offsetY)
								- (this.RAYON_NOEUD / 2);
						double Bx = ((troncon.getKey().second.getX() * this.zoom) + this.offsetX)
								- (this.RAYON_NOEUD / 2);
						double By = ((troncon.getKey().second.getY() * this.zoom) + this.offsetY)
								- (this.RAYON_NOEUD / 2);
						double Ux = Bx - Ax;
						double Uy = By - Ay;
						double ACx = XCenter - Ax;
						double ACy = YCenter - Ay;
						double numerateur = (Ux * ACy) - (Uy * ACx);
						if (numerateur < 0) {
							numerateur = -numerateur;
						}
						double denominateur = Math.sqrt((Ux * Ux) + (Uy * Uy));
						double CI = numerateur / denominateur;
						if (CI <= this.pointerMargin) {
							double ABx = Bx - Ax;
							double ABy = By - Ay;
							double BCx = XCenter - Bx;
							double BCy = YCenter - By;
							double pscal1 = (ABx * ACx) + (ABy * ACy);
							double pscal2 = ((-ABx) * BCx) + ((-ABy) * BCy);
							if ((pscal1 >= 0) && (pscal2 >= 0)) {
								this.tronconIsFocused(troncon.getValue());
								return;
							}
						}
					}
				}
			}
		}
	}

	private void tronconIsClicked(Troncon troncon) {
		this.tronconSelectionned = troncon;
		GraphicsContext gc = this.getGraphicsContext2D();
		double x1 = ((troncon.getOrigine().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
		double y1 = ((troncon.getOrigine().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
		double x2 = ((troncon.getDestination().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
		double y2 = ((troncon.getDestination().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
		// Affichage du troncon
		gc.setStroke(new Color(0, 0.3984, 0, 1));
		gc.strokeLine((troncon.getOrigine().getX() * this.zoom) + this.offsetX,
				(troncon.getOrigine().getY() * this.zoom) + this.offsetY,
				(troncon.getDestination().getX() * this.zoom) + this.offsetX,
				(troncon.getDestination().getY() * this.zoom) + this.offsetY);
		// Affichage zone de texte
		int l = troncon.getNomRue().length();
		gc.setFill(new Color(0, 0, 0, 0.5));
		gc.fillRect(Math.min(x2, x1) + (Math.abs(x2 - x1) / 2), Math.min(y2, y1) + (Math.abs(y2 - y1) / 2),
				10 + (l * 7), 20);
		// Affichage texte
		gc.setFill(new Color(1, 1, 1, 1));
		gc.fillText(troncon.getNomRue(), Math.min(x2, x1) + (Math.abs(x2 - x1) / 2) + 5,
				Math.min(y2, y1) + (Math.abs(y2 - y1) / 2) + 15);
		// Affichage informations
		int l2 = troncon.getNomRue().length() + 12 + this.getNumberDigit(troncon.getLongueur())
				+ this.getNumberDigit(troncon.getVitesse());
		gc.setFill(new Color(0, 0, 0, 0.5));
		gc.fillRect(10, this.getHeight() - 22, 20 + (l2 * 7), 20);
		gc.setFill(new Color(1, 1, 1, 1));
		gc.fillText(troncon.getNomRue() + " - " + troncon.getLongueur() + "dm - " + troncon.getVitesse() + "dm/h", 20,
				this.getHeight() - 6);
	}

	private void tronconIsFocused(Troncon troncon) {
		if (troncon != this.tronconSelectionned) {
			GraphicsContext gc = this.getGraphicsContext2D();
			double x1 = ((troncon.getOrigine().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
			double y1 = ((troncon.getOrigine().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
			double x2 = ((troncon.getDestination().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
			double y2 = ((troncon.getDestination().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
			// Affichage du troncon
			gc.setStroke(new Color(0, 0.5976, 0, 1));
			gc.strokeLine((troncon.getOrigine().getX() * this.zoom) + this.offsetX,
					(troncon.getOrigine().getY() * this.zoom) + this.offsetY,
					(troncon.getDestination().getX() * this.zoom) + this.offsetX,
					(troncon.getDestination().getY() * this.zoom) + this.offsetY);
			// Affichage zone de texte
			int l = troncon.getNomRue().length();
			gc.setFill(new Color(0, 0, 0, 0.5));
			gc.fillRect(Math.min(x2, x1) + (Math.abs(x2 - x1) / 2), Math.min(y2, y1) + (Math.abs(y2 - y1) / 2),
					10 + (l * 7), 20);
			// Affichage texte
			gc.setFill(new Color(1, 1, 1, 1));
			gc.fillText(troncon.getNomRue(), Math.min(x2, x1) + (Math.abs(x2 - x1) / 2) + 5,
					Math.min(y2, y1) + (Math.abs(y2 - y1) / 2) + 15);
			// Affichage informations
			int l2 = troncon.getNomRue().length() + 12 + this.getNumberDigit(troncon.getLongueur())
					+ this.getNumberDigit(troncon.getVitesse());
			gc.setFill(new Color(0, 0, 0, 0.5));
			gc.fillRect(10, this.getHeight() - 22, 20 + (l2 * 7), 20);
			gc.setFill(new Color(1, 1, 1, 1));
			gc.fillText(troncon.getNomRue() + " - " + troncon.getLongueur() + "dm - " + troncon.getVitesse() + "dm/h",
					20, this.getHeight() - 6);
		}
	}

	private void livraisonIsClicked(Livraison livraison) {
		if ((livraison != null) && (livraison.getNoeud().getId() != -1)) {
			GraphicsContext gc = this.getGraphicsContext2D();
			this.noeudIsClickedEffect(livraison.getNoeud());
			double x = ((livraison.getNoeud().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
			double y = ((livraison.getNoeud().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
			// Affichage zone de texte
			int l = String.valueOf(livraison.getNoeud().getId()).length();
			gc.setFill(new Color(0, 0, 0, 0.5));
			gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
			// Affichage texte
			gc.setFill(new Color(1, 1, 1, 1));
			gc.fillText(String.valueOf(livraison.getNoeud().getId()), x + 15 + 5, y);
			if ((this.plan != null) && (this.plan.getTournee() != null)) {
				this.miseEnEvidenceTrajet(livraison.getNoeud());
			}
		}
	}

	private void entrepotIsClicked(Entrepot entrepot) {
		GraphicsContext gc = this.getGraphicsContext2D();
		this.noeudIsClickedEffect(entrepot.getNoeud());
		double x = ((entrepot.getNoeud().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
		double y = ((entrepot.getNoeud().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
		// Affichage zone de texte
		int l = String.valueOf(entrepot.getNoeud().getId()).length();
		gc.setFill(new Color(0, 0, 0, 0.5));
		gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
		// Affichage texte
		gc.setFill(new Color(1, 1, 1, 1));
		gc.fillText(String.valueOf(entrepot.getNoeud().getId()), x + 15 + 5, y);
	}

	private void noeudIsClicked(Noeud noeud) {
		GraphicsContext gc = this.getGraphicsContext2D();
		this.noeudIsClickedEffect(noeud);
		double x = ((noeud.getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
		double y = ((noeud.getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
		// Affichage zone de texte
		int l = String.valueOf(noeud.getId()).length();
		gc.setFill(new Color(0, 0, 0, 0.5));
		gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
		// Affichage texte
		gc.setFill(new Color(1, 1, 1, 1));
		gc.fillText(String.valueOf(noeud.getId()), x + 15 + 5, y);
	}

	private void noeudIsClickedEffect(Noeud noeud) {
		this.noeudSelectionned = noeud;
		double x = ((noeud.getX() * this.zoom) + this.offsetX) - (this.RAYON_LIVRAISON / 2);
		double y = ((noeud.getY() * this.zoom) + this.offsetY) - (this.RAYON_LIVRAISON / 2);
		GraphicsContext gc = this.getGraphicsContext2D();
		// Affichage du noeud exterieur
		gc.setFill(new Color(0.859, 0.839, 0.808, 1));
		gc.fillOval(x, y, this.RAYON_LIVRAISON, this.RAYON_LIVRAISON);
		// Affichage du noeud interieur
		gc.setFill(new Color(0, 0.4921, 0.9609, 1));
		gc.fillOval(((noeud.getX() * this.zoom) + this.offsetX) - ((this.RAYON_LIVRAISON - 4) / 2),
				((noeud.getY() * this.zoom) + this.offsetY) - ((this.RAYON_LIVRAISON - 4) / 2),
				this.RAYON_LIVRAISON - 4, this.RAYON_LIVRAISON - 4);
	}

	private void livraisonIsFocused(Livraison livraison) {
		if (livraison.getNoeud() != this.noeudSelectionned) {
			double x = ((livraison.getNoeud().getX() * this.zoom) + this.offsetX) - (this.RAYON_LIVRAISON / 2);
			double y = ((livraison.getNoeud().getY() * this.zoom) + this.offsetY) - (this.RAYON_LIVRAISON / 2);
			GraphicsContext gc = this.getGraphicsContext2D();
			this.noeudIsFocusedEffect(livraison.getNoeud());
			// Affichage zone de texte
			int l = String.valueOf(livraison.getNoeud().getId()).length();
			gc.setFill(new Color(0, 0, 0, 0.8));
			gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
			// Affichage texte
			gc.setFill(new Color(1, 1, 1, 1));
			gc.fillText(String.valueOf(livraison.getNoeud().getId()), x + 15 + 5, y);
		}
	}

	private void entrepotIsFocused(Entrepot entrepot) {
		if (entrepot.getNoeud() != this.noeudSelectionned) {
			double x = ((entrepot.getNoeud().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
			double y = ((entrepot.getNoeud().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
			GraphicsContext gc = this.getGraphicsContext2D();
			this.noeudIsFocusedEffect(entrepot.getNoeud());
			// Affichage zone de texte
			int l = String.valueOf(entrepot.getNoeud().getId()).length();
			gc.setFill(new Color(0, 0, 0, 0.8));
			gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
			// Affichage texte
			gc.setFill(new Color(1, 1, 1, 1));
			gc.fillText(String.valueOf(entrepot.getNoeud().getId()), x + 15 + 5, y);
		}
	}

	private void noeudIsFocused(Noeud noeud) {
		if (noeud != this.noeudSelectionned) {
			double x = ((noeud.getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
			double y = ((noeud.getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
			GraphicsContext gc = this.getGraphicsContext2D();
			this.noeudIsFocusedEffect(noeud);
			// Affichage zone de texte
			int l = String.valueOf(noeud.getId()).length();
			gc.setFill(new Color(0, 0, 0, 0.8));
			gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
			// Affichage texte
			gc.setFill(new Color(1, 1, 1, 1));
			gc.fillText(String.valueOf(noeud.getId()), x + 15 + 5, y);
		}
	}

	private void noeudIsFocusedEffect(Noeud noeud) {
		double x = ((noeud.getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
		double y = ((noeud.getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
		GraphicsContext gc = this.getGraphicsContext2D();
		// Affichage du noeud exterieur
		gc.setFill(new Color(0, 0.4921, 0.9609, 0.7));
		gc.fillOval(x, y, this.RAYON_NOEUD, this.RAYON_NOEUD);
	}

	public void modeAjouterLivraison(boolean mode) {
		this.modeAjouterLivraison = mode;
	}

	public void reinitPlanVille() {
		this.tronconSelectionned = null;
		this.noeudSelectionned = null;
		this.livraisonSelectionned = null;
	}

	public void dessinerPlan(Plan plan) {
		this.effacer();
		if (plan != null) {
			this.plan = plan;
			this.dessinerFond();
			this.calculerZoom();
			this.recentrerPlan();
			if (plan.getTroncons() != null) {
				this.dessinerTroncon();
			}
			if (plan.getNoeuds() != null) {
				this.dessinerNoeud();
			}
			if (plan.getTournee() != null) {
				this.dessinerTournee();
			}
			if (plan.getEntrepot() != null) {
				this.dessinerEntrepot();
			}
			if (plan.getLivraisons() != null) {
				this.dessinerLivraison();
			}
			if (this.noeudSelectionned != null) {
				this.noeudIsClicked(this.noeudSelectionned);
			}
			if (this.tronconSelectionned != null) {
				this.tronconIsClicked(this.tronconSelectionned);
			}
			if (this.livraisonSelectionned != null) {
				this.livraisonIsClicked(this.livraisonSelectionned);
			}
		} else {
			System.err.println("plan is null");
		}
	}

	private void effacer() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	private void dessinerFond() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.250, 0.250, 0.250, 0.8));
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	private void dessinerNoeud() {
		GraphicsContext gc = this.getGraphicsContext2D();
		for (Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
			if (n != null) {
				gc.setFill(new Color(0.859, 0.839, 0.808, 1));
				gc.fillOval(((n.getValue().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2),
						((n.getValue().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2), this.RAYON_NOEUD,
						this.RAYON_NOEUD);
				gc.setFill(new Color(0.250, 0.250, 0.250, 1));
				gc.fillOval(((n.getValue().getX() * this.zoom) + this.offsetX) - ((this.RAYON_NOEUD - 4) / 2),
						((n.getValue().getY() * this.zoom) + this.offsetY) - ((this.RAYON_NOEUD - 4) / 2),
						this.RAYON_NOEUD - 4, this.RAYON_NOEUD - 4);
			}
		}
	}

	private void dessinerTroncon() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(this.LARGEUR_TRONCON);
		for (Map.Entry<Pair<Noeud, Noeud>, Troncon> t : this.plan.getTroncons().entrySet()) {
			if ((t != null) && (t.getKey().first != null) && (t.getKey().second != null)) {
				gc.strokeLine((t.getKey().first.getX() * this.zoom) + this.offsetX,
						(t.getKey().first.getY() * this.zoom) + this.offsetY,
						(t.getKey().second.getX() * this.zoom) + this.offsetX,
						(t.getKey().second.getY() * this.zoom) + this.offsetY);
			}
		}
	}

	public void dessinerLivraison() {
		GraphicsContext gc = this.getGraphicsContext2D();
		for (Map.Entry<Integer, Livraison> l : this.plan.getLivraisons().entrySet()) {
			if ((l != null) && (l.getKey() != null)) {
				gc.setFill(new Color(0.250, 0.250, 0.250, 1));
				gc.fillOval(((l.getValue().getNoeud().getX() * this.zoom) + this.offsetX) - (this.RAYON_LIVRAISON / 2),
						((l.getValue().getNoeud().getY() * this.zoom) + this.offsetY) - (this.RAYON_LIVRAISON / 2),
						this.RAYON_LIVRAISON, this.RAYON_LIVRAISON);
				gc.setFill(Color.LIGHTGREEN);
				gc.fillOval(
						((l.getValue().getNoeud().getX() * this.zoom) + this.offsetX)
								- ((this.RAYON_LIVRAISON - 4) / 2),
						((l.getValue().getNoeud().getY() * this.zoom) + this.offsetY)
								- ((this.RAYON_LIVRAISON - 4) / 2),
						this.RAYON_LIVRAISON - 4, this.RAYON_LIVRAISON - 4);
			}
		}
	}

	public void dessinerEntrepot() {
		GraphicsContext gc = this.getGraphicsContext2D();
		if ((this.plan.getEntrepot() != null) && (this.plan.getEntrepot().getNoeud() != null)) {
			gc.setFill(new Color(0.250, 0.250, 0.250, 1));
			gc.fillOval(
					((this.plan.getEntrepot().getNoeud().getX() * this.zoom) + this.offsetX)
							- (this.RAYON_LIVRAISON / 2),
					((this.plan.getEntrepot().getNoeud().getY() * this.zoom) + this.offsetY)
							- (this.RAYON_LIVRAISON / 2),
					this.RAYON_LIVRAISON, this.RAYON_LIVRAISON);
			gc.setFill(Color.RED);
			gc.fillOval(
					((this.plan.getEntrepot().getNoeud().getX() * this.zoom) + this.offsetX)
							- ((this.RAYON_LIVRAISON - 4) / 2),
					((this.plan.getEntrepot().getNoeud().getY() * this.zoom) + this.offsetY)
							- ((this.RAYON_LIVRAISON - 4) / 2),
					this.RAYON_LIVRAISON - 4, this.RAYON_LIVRAISON - 4);
			gc.setFill(Color.RED);
		}
	}

	private void dessinerTournee() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0, 0.709, 0.968, 1));
		gc.setStroke(new Color(0, 0.709, 0.968, 1));
		gc.setLineWidth(this.LARGEUR_TRONCON);
		for (Trajet trajet : this.plan.getTournee().getTrajets()) {
			for (Troncon t : trajet.getTroncons()) {
				if ((t != null) && (t.getOrigine() != null) && (t.getDestination() != null)) {
					gc.strokeLine((t.getOrigine().getX() * this.zoom) + this.offsetX,
							(t.getOrigine().getY() * this.zoom) + this.offsetY,
							(t.getDestination().getX() * this.zoom) + this.offsetX,
							(t.getDestination().getY() * this.zoom) + this.offsetY);
				}
			}
		}
	}

	private void calculerZoom() {
		if (this.plan.getNoeuds() != null) {
			double width = this.getWidth();
			double height = this.getHeight();
			if (width == Math.min(width, height)) {
				int minX = Integer.MAX_VALUE;
				int maxX = Integer.MIN_VALUE;
				for (Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
					if (n != null) {
						if (n.getValue().getX() <= minX) {
							minX = n.getValue().getX();
						} else if (n.getValue().getX() >= maxX) {
							maxX = n.getValue().getX();
						}
					}
				}
				double widthMap = maxX + minX;
				this.zoom = width / (widthMap);
			} else {
				int minY = Integer.MAX_VALUE;
				int maxY = Integer.MIN_VALUE;
				for (Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
					if (n != null) {
						if (n.getValue().getY() <= minY) {
							minY = n.getValue().getY();
						} else if (n.getValue().getY() >= maxY) {
							maxY = n.getValue().getY();
						}
					}
				}
				double heightMap = maxY + minY;
				this.zoom = height / (heightMap);
			}

		} else {
			this.zoom = 1;
		}
	}

	private void recentrerPlan() {
		this.offsetY = 0;
		this.offsetX = 0;
		double width = this.getWidth();
		double height = this.getHeight();
		if (width == Math.min(width, height)) {
			this.offsetY = (height - width) / 2;
		} else {
			this.offsetX = (width - height) / 2;
		}
	}

	@Override
	public void resize(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	public GestionVue getVue() {
		return this.vue;
	}

	public void setVue(GestionVue vue) {
		this.vue = vue;
	}

	public boolean getModeAjouterLivraison() {
		return this.modeAjouterLivraison;
	}

	public void miseEnEvidenceTrajet(Noeud arrivee) {
		if ((this.plan != null) && !this.modeAjouterLivraison) {
			for (Trajet trajet : this.plan.getTournee().getTrajets()) {
				if (trajet.getArrive().equals(arrivee)) {
					GraphicsContext gc = this.getGraphicsContext2D();
					double x = ((trajet.getDepart().getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
					double y = ((trajet.getDepart().getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
					int l = String.valueOf(trajet.getDepart().getId()).length() + 3;
					gc.setFill(new Color(0.1, 0.1, 0.1, 1));
					gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
					// Affichage texte
					gc.setFill(new Color(1, 1, 1, 1));
					gc.fillText("D : " + String.valueOf(trajet.getDepart().getId()), x + 15 + 5, y);
					Noeud n = null;
					for (Troncon troncon : trajet.getTroncons()) {
						gc.setStroke(new Color(0, 0, 1, 1));
						gc.strokeLine((troncon.getOrigine().getX() * this.zoom) + this.offsetX,
								(troncon.getOrigine().getY() * this.zoom) + this.offsetY,
								(troncon.getDestination().getX() * this.zoom) + this.offsetX,
								(troncon.getDestination().getY() * this.zoom) + this.offsetY);
						n = troncon.getDestination();
					}
					x = ((n.getX() * this.zoom) + this.offsetX) - (this.RAYON_NOEUD / 2);
					y = ((n.getY() * this.zoom) + this.offsetY) - (this.RAYON_NOEUD / 2);
					l = String.valueOf(n.getId()).length() + 3;
					gc.setFill(new Color(0.1, 0.1, 0.1, 1));
					gc.fillRect(x + 15, y - 15, 10 + (l * 7), 20);
					// Affichage texte
					gc.setFill(new Color(1, 1, 1, 1));
					gc.fillText("A : " + String.valueOf(n.getId()), x + 15 + 5, y);
					return;
				}
			}
		}
	}

	private int getNumberDigit(int number) {
		int t = 0;
		while (number > 0) {
			number = number / 10;
			t++;
		}
		return t;
	}
}
