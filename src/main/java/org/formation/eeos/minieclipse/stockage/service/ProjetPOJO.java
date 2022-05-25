package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;
import java.util.List;

public class ProjetPOJO implements Serializable {
	
	/**
	 * Pojo de la classe metier {@link Projet}
	 *
	 */

	private static final long serialVersionUID = -5044703470697451493L;
	private String _nom;
	private String _racine;	
	
	/**
	 * 
	 * @param nom
	 * @param racine
	 * @param mainclasspath
	 * @param classpath
	 * @param outputpath
	 */
	public ProjetPOJO(String nom, String racine) {
		setNom(nom);
		setRacine(racine);	
	}
	
	public String getNom() {
		return this._nom;
	}

	private void setNom(String newValue) {
		this._nom = newValue;
	}


	public String getRacine() {
		return _racine;
	}

	private void setRacine(String racine) {
		_racine = racine;
	}

}
