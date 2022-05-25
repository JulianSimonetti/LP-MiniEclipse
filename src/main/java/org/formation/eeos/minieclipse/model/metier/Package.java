package org.formation.eeos.minieclipse.model.metier;

import java.nio.file.Path;
import java.util.Vector;

import org.formation.eeos.minieclipse.model.technique.Dossier;

/**
 *  Representation metier d'un package rempli depuis JavaReflect
 *
 */

public class Package extends Dossier {
	private String nomPackage;
	private Vector<Package> lesSousPackages;
	private Vector<Classe> Classes;
	
	public Package(String nom, Path path) {
		super(path);
		this.setNomPackage(nom);
		Classes = new Vector<>();
		lesSousPackages = new Vector<>();
	}
	
	public void ajouter(Classe laClasse) {
		Classes.add(laClasse);
	}
	
	public void ajouter(Package lePackage) {
		this.lesSousPackages.add(lePackage);
	}
	
	public void supprimer(Classe laClasse) {
		Classes.remove(laClasse);
	}

	public String getNomPackage() {
		return nomPackage;
	}

	private void setNomPackage(String nomPackage) {
		this.nomPackage = nomPackage;
	}
}
