package org.formation.eeos.minieclipse.viewmodel.tree;


import java.util.List;
import java.util.Vector;

import org.formation.eeos.minieclipse.model.metier.Classe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;

public class ClassePresentation extends TreeParent {
	private StringProperty nom;
	private List<MethodePresentation> methodes;
	private List<AttributPresentation> attributs;
	
	public ClassePresentation(Classe classe) {
		this.nom = new SimpleStringProperty(classe.getNom());
		this.methodes = new Vector<>();
		this.attributs = new Vector<>();
		classe.getAttributs().forEach((attribut)->{
			System.out.println(attribut.getNom());
			attributs.add(new AttributPresentation(attribut.getNom()));
		});
		classe.getMethodes().forEach((methode)->{
			System.out.println(methode.getNom());
			methodes.add(new MethodePresentation(methode.getNom()));
		});
		
	}
	
	@Override
	public StringProperty nomProperty() {
		return this.nom;
	}

	@Override
	public String getNom() {
		return this.nom.get();
	}

	@Override
	public ContextMenu getContextMenu() {
		return null;
	}

	@Override
	public TreeItem<TreeParent> children() {
		TreeItem<TreeParent> ret = new TreeItem<TreeParent>(this);
		for (AttributPresentation attributPresentation : attributs) {
			ret.getChildren().add(attributPresentation.children());
		}
		for (MethodePresentation methodePresentation : methodes) {
			ret.getChildren().add(methodePresentation.children());
		}
		return ret;
	}

	@Override
	public void ajouterDossier(DossierPresentation e) {
		// TODO Auto-generated method stub
		
	}

}
