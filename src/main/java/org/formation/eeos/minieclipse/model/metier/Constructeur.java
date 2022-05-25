package org.formation.eeos.minieclipse.model.metier;

import java.util.List;

/**
 * Representation metier d'un constructeur rempli depuis JavaReflect
 * @author rayen
 *
 */

public class Constructeur {
	private String nom;
	private List<Parametre> param;
	
	public Constructeur(String n, List<Parametre> p) {
		this.nom = n;
		this.param = p;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public List<Parametre> getParam(){
		return this.param;
	}

}
