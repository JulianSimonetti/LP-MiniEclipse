package org.formation.eeos.minieclipse.viewmodel.tree;

import java.util.ArrayList;
import java.util.List;
import org.formation.eeos.minieclipse.model.technique.Fichier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

public class FichierPresentation extends TreeStockee {
	private Fichier handle;
	private SimpleStringProperty nom;
	private SimpleStringProperty contenu;
	private ObservableList<ClassePresentation> lesClasses;
	
	public FichierPresentation(Fichier fic) {
		this.setHandle(fic);
		nom = new SimpleStringProperty(fic.getNomFichier());
		contenu = new SimpleStringProperty(fic.getContenu());
		lesClasses = FXCollections.observableArrayList(new ArrayList<ClassePresentation>());
		fic.getClasses().forEach((element)->{
			lesClasses.add(new ClassePresentation(element));
		});
	}
	
	public SimpleStringProperty getContenu() {
		return contenu;
	}

	private void setContenu(SimpleStringProperty contenu) {
		this.contenu = contenu;
	}

	private void setNom(SimpleStringProperty nom) {
		this.nom = nom;
	}

	@Override
	public String getNom() {
		return nomProperty().get();
	}

	@Override
	public StringProperty nomProperty() {
		return this.nom;
	}

	@Override
	public ContextMenu getContextMenu() {		
		return super.getContextMenu();
	}

	@Override
	public void rename(String string) {
		this.nom.set(string);
	}

	public ObservableList<ClassePresentation> getLesClasses() {
		return lesClasses;
	}

	private void setLesClasses(ObservableList<ClassePresentation> lesClasses) {
		this.lesClasses = lesClasses;
	}

	@Override
	public TreeItem<TreeParent> children() {
		TreeItem<TreeParent> ret = new TreeItem<>(this);
		 getLesClasses().forEach((classe)->{
			TreeItem<TreeParent> p = new TreeItem<>(classe);
			p.getChildren().add(classe.children());
			ret.getChildren().add(p);
		});
		return ret;
	}

	public Fichier getHandle() {
		return handle;
	}

	private void setHandle(Fichier handle) {
		this.handle = handle;
	}

	@Override
	public void ajouterDossier(DossierPresentation e) {
		// TODO Auto-generated method stub
		
	}
}
