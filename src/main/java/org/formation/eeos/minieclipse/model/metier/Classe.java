package org.formation.eeos.minieclipse.model.metier;


import java.util.List;
import java.util.Vector;

import org.formation.eeos.minieclipse.model.technique.Fichier;



/**
 *  Representation metier d'une classe rempli depuis JavaReflect
 *
 */


public class Classe {
	private String nom;
	private List<Constructeur> Constructeurs;
	private List<Attribut> Attributs;
	private List<Methode> Methodes;
	
	
	/**
	 * Constructeur de la classe Classe, il sera rempli par JavaReflect donc pas besoin de faire tout les cas exhaustif
	 * @param leFichier
	 * @param pk
	 * @param construct
	 * @param at
	 * @param mt
	 */
	public Classe(String nom, List<Constructeur> construct, List<Methode> mt, List<Attribut> at) {
		setNom(nom);
		setConstructeurs(construct);
		this.Attributs = at;
		this.Constructeurs = construct;
		this.Methodes = mt;
	}
	
	public String getNom() {
		return this.nom;
	}

	private void setNom(String n) {
		nom = n;
	}
	
	private void setConstructeurs(List<Constructeur> cs) {
		this.Constructeurs = cs;
	}

	public List<Attribut> getAttributs() {
		return Attributs;
	}

	public void setAttributs(List<Attribut> attributs) {
		Attributs = attributs;
	}

	public List<Methode> getMethodes() {
		return Methodes;
	}

	public void setMethodes(List<Methode> methodes) {
		Methodes = methodes;
	}

}
