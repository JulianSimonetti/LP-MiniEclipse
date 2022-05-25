package org.formation.eeos.minieclipse.viewmodel.tree;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

public class AttributPresentation extends TreeParent {
	private StringProperty nom;
	
	public AttributPresentation(String nom) {
		this.nom = new SimpleStringProperty(nom);
	}
	
	@Override
	public StringProperty nomProperty() {
		return nom;
	}

	@Override
	public String getNom() {
		return nom.get();
	}

	@Override
	public ContextMenu getContextMenu() {
		return null;
	}

	@Override
	public void ajouterDossier(DossierPresentation e) {
		// TODO Auto-generated method stub
		
	}

}
