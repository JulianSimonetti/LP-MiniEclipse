package org.formation.eeos.minieclipse.model.metier;

import java.util.List;
/**
 * 
 * @author rayen
 *
 */
public class Methode {
	private String nom;
	private List<Parametre> param;
	private Type retour;
	

	/**
	 * Methode avec parametre avec type retour
	 * @param param
	 * @param typeRetour
	 */
	public Methode(String nom,Type typeRetour, List<Parametre> param) {
		this.nom = nom;
		setTypeRetour(typeRetour);
		this.param = param;
	}

	/**
	 * 
	 * @return Son type qu'il retourne 
	 */
	public Type getTypeRetour() {
		return retour;
	}
	/**
	 * 
	 * @param typeRetour
	 */
	private void setTypeRetour(Type typeRetour) {
		this.retour = typeRetour;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
