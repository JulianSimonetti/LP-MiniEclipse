package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;
/**
 * Pojo de la classe metier Parametre
 *
 */
public class ParametrePOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String type;

	public ParametrePOJO() {
		this(null,null);
	}
	
	public ParametrePOJO(String nom, String type) {
		setNom(nom);
		setType(type);
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param nom the nom to set
	 */
	private void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param type the type to set
	 */
	private void setType(String type) {
		this.type = type;
	}
	
	
}
