package org.formation.eeos.minieclipse.viewmodel.tree;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import org.formation.eeos.minieclipse.model.technique.Dossier;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

public class DossierPresentation extends TreeStockee {
	private Dossier handle;
	private SimpleStringProperty cheminProperty;
	private SimpleStringProperty nomProperty;
	private ObservableList<DossierPresentation> dossiersProperty;
	private ObservableList<FichierPresentation> fichiersProperty;
	private TreeItem<TreeStockee> ret = new TreeItem<>(this);

	public DossierPresentation(Dossier hand) {
		this.handle = hand;
		nomProperty = new SimpleStringProperty(hand.getNom());
		cheminProperty = new SimpleStringProperty(hand.getChemin());
		setDossiersProperty(FXCollections.observableArrayList(new ArrayList<DossierPresentation>()));
		setFichiersProperty(FXCollections.observableArrayList(new ArrayList<FichierPresentation>()));
		hand.getDossiers().forEach((dossier)->{
			dossiersProperty.add(new DossierPresentation(dossier));
		});
		hand.getFichiers().forEach((fichierObs)->{
			fichiersProperty.add(new FichierPresentation(fichierObs));
		});
		this.dossiersProperty.addListener(new ListChangeListener<>() {
			@Override
			public void onChanged(Change<? extends DossierPresentation> c) {
				while(c.next()) {
					if(c.wasAdded()) {
						c.getAddedSubList().forEach((element)->{
							try {
								hand.ajouter(element.getHandle());
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} 
					if(c.wasRemoved()) {
						c.getRemoved().forEach((element)->{
							try {
								hand.supprimer(element.getHandle());
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
				}				
			}
		});
		this.fichiersProperty.addListener(new ListChangeListener<>() {
			@Override
			public void onChanged(Change<? extends FichierPresentation> c) {
				while(c.next()) {
					if(c.wasAdded()) {
						c.getAddedSubList().forEach((element)->{
							hand.ajouter(element.getHandle());							
						});
					}
					if(c.wasRemoved()) {
						c.getRemoved().forEach((element)->{
							try {
								hand.supprimer(element.getHandle());
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
				}			
			}
		});
	}
	
	public final String getChemin() {
		return this.cheminProperty.get();
	}

	public ObservableList<DossierPresentation> getDossiersProperty() {
		return dossiersProperty;
	}

	private void setDossiersProperty(ObservableList<DossierPresentation> dossiersProperty) {
		this.dossiersProperty = dossiersProperty;
	}

	public ObservableList<FichierPresentation> getFichiersProperty() {
		return fichiersProperty;
	}

	private void setFichiersProperty(ObservableList<FichierPresentation> fichiersProperty) {
		this.fichiersProperty = fichiersProperty;
	}

	@Override
	public String getNom() {
		return nomProperty().get();
	}


	@Override
	public StringProperty nomProperty() {
		return this.nomProperty;
	}
	
	public StringProperty cheminProperty() {
		return cheminProperty;
	}

	@Override
	public ContextMenu getContextMenu() {
		
		return super.getContextMenu();
	}

	@Override
	public void rename(String string) {
		this.nomProperty.set(string);		
	}

	@Override
	public TreeItem<TreeParent> children() {
		TreeItem<TreeParent> ret = new TreeItem<>(this);
		getDossiersProperty().forEach((element)->{
			TreeItem<TreeParent> p = element.children();
			ret.getChildren().add(p);
		});
		getFichiersProperty().forEach((element)->{
			TreeItem<TreeParent> p = element.children();
			ret.getChildren().add(p);
		});
		return ret;
	}
	
	@Override
	public String chemin() {
		return this.getChemin();
	}
	
	@Override
	public TreeItem<TreeStockee> enfantSansFichier(){
		ret = new TreeItem<>(this);
		getDossiersProperty().forEach((element)->{
			TreeItem<TreeStockee> p = element.enfantSansFichier();
			ret.getChildren().add(p);
		});
		return ret;
	}

	@Override
	public void ajouterDossier(DossierPresentation e) {
		this.dossiersProperty.add(e);
	}
	
	@Override
	public void ajouterFichier(FichierPresentation e) {
		this.fichiersProperty.add(e);
	}
	
	@Override
	public Path getPath() {
		return UtilitaireFichier.getNIOPath(getChemin());
	}

	public Dossier getHandle() {
		return handle;
	}
}
