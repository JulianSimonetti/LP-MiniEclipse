package org.formation.eeos.minieclipse.view.components;
import org.formation.eeos.minieclipse.viewmodel.tree.FichierPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class TabFile extends Tab {
	private FichierPresentation leFichierCorrespondant;
	public TabFile(FichierPresentation fichierCorrespondant, String nom, Node contenuDeTab) {
		super(nom, contenuDeTab);
		this.leFichierCorrespondant = fichierCorrespondant;
	}
	
	public FichierPresentation getFichierCorrespondant(){
		return this.leFichierCorrespondant;
	}
}
