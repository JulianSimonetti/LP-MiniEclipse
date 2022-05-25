package org.formation.eeos.minieclipse.viewmodel.tree;

import javafx.beans.property.StringProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

/**
 * Classe mere des item de l'arborescence du projet
 *
 */

public abstract class TreeParent {
	
	public TreeParent() {
	}
	
	public abstract StringProperty nomProperty();
	
	public abstract String getNom();
	
	public abstract ContextMenu getContextMenu();

	public void rename(String string) {
		
	}
	
	public TreeItem<TreeParent> children(){
		TreeItem<TreeParent> ret = new TreeItem<>(this);
		return ret;
	}
	
	public String chemin() {
		return "";
	}
	
	public void ajouterDossier(TreeParent e) {
		
	}
	
	public void ajouterFichier(TreeParent e) {
		
	}

	public abstract void ajouterDossier(DossierPresentation e);

	public void ajouterFichier(FichierPresentation e) {	
	}
	
	public void supprimer() {
		
	}
}
