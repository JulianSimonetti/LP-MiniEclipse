package org.formation.eeos.minieclipse.viewmodel.tree;

import java.io.IOException;
import java.net.URISyntaxException;

import org.formation.eeos.minieclipse.WindowFactory;
import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.viewmodel.ProjetVueModele;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;


public class ProjetPresentation extends TreeStockee {
	private Projet handle;
	private SimpleStringProperty nom;
	private DossierPresentation racineProperty;
	
	public ProjetPresentation(Projet hand) {
		super();
		this.nom = new SimpleStringProperty(hand.getNom());
		this.handle = hand;
		racineProperty = new DossierPresentation(hand.getRacine());
	}
	/*
	 * public Vector<MenuItem> getItemsContextMenu() { Vector<MenuItem>
	 * contexts = new Vector<>(); Menu nouveau = new Menu("Nouveau"); MenuItem file
	 * = new MenuItem("Fichier"); file.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) {
	 * 
	 * } }); nouveau.getItems().add(file); contexts.add(nouveau); return contexts; }

	 */
	 
	@Override
	public final SimpleStringProperty nomProperty() {
		return nom;
	}
	
	public final String getNom() {
		return nom.get();
	}
	
	private void setNom(String s) {
		nom.set(s);
	}	
	
	public DossierPresentation getRacineProperty() {
		return racineProperty;
	}

	private void setRacineProperty(DossierPresentation racineProperty) {
		this.racineProperty = racineProperty;
	}

	@Override
	public ContextMenu getContextMenu() {
		ProjetPresentation handle = this;
		ContextMenu menu = super.getContextMenu();
		menu.getItems().add(new SeparatorMenuItem());
		MenuItem refresh = new MenuItem("Réactualiser");
		refresh.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					ProjetVueModele.get().refresh(handle);
				} catch (ClassNotFoundException | IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		menu.getItems().add(refresh);
		menu.getItems().add(new SeparatorMenuItem());
		MenuItem deplacer = new MenuItem("Déplacer");
		deplacer.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				WindowFactory.get().openDeplacerProjet(handle);				
			}
		});
		menu.getItems().add(deplacer);
		return menu;
	}

	@Override
	public void rename(String string) {
		this.nom.set(string);		
	}

	@Override
	public TreeItem<TreeParent> children() {
		TreeItem<TreeParent> ret = new TreeItem<>(this);
		getRacineProperty().getDossiersProperty().forEach((element)->{
			TreeItem<TreeParent> p = element.children();
			ret.getChildren().add(p);
		});
		getRacineProperty().getFichiersProperty().forEach((element)->{
			TreeItem<TreeParent> p = element.children();
			ret.getChildren().add(p);
		});
		return ret;
	}
	
	@Override
	public String chemin() {
		return this.getRacineProperty().getChemin();
	}
	
	@Override
	public TreeItem<TreeStockee> enfantSansFichier(){
		TreeItem<TreeStockee> ret = new TreeItem<>(this);
		getRacineProperty().getDossiersProperty().forEach((element)->{
			TreeItem<TreeStockee> p = element.enfantSansFichier();
			ret.getChildren().add(p);
		});
		return ret;
	}

	@Override
	public void ajouterDossier(TreeParent e) {
		this.racineProperty.ajouterDossier(e);	
	}
	
	@Override
	public void ajouterFichier(TreeParent e) {
		this.racineProperty.ajouterFichier(e);
	}

	public Projet getHandle() {
		return handle;
	}

	@Override
	public void ajouterDossier(DossierPresentation e) {
		// TODO Auto-generated method stub
		
	}
}
