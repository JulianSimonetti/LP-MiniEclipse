package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;

import org.formation.eeos.minieclipse.model.metier.Attribut;
/**
 * Pojo de la classe metier {@link Attribut}
 *
 */
public class AttributPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String visibilite;
	private String modificateur;
	private String type;
	private String nom;
	
	public AttributPOJO() {
		this(null,null,null,null);
	}
	
	public AttributPOJO(String visibilite, String modificateur, String type, String nom) {
		setVisibilite(visibilite);
		setModificateur(modificateur);
		setType(type);
		setNom(nom);
	}

	/**
	 * @return the visibilite
	 */
	public String getVisibilite() {
		return visibilite;
	}

	/**
	 * @return the modificateur
	 */
	public String getModificateur() {
		return modificateur;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param visibilite the visibilite to set
	 */
	private void setVisibilite(String visibilite) {
		this.visibilite = visibilite;
	}

	/**
	 * @param modificateur the modificateur to set
	 */
	private void setModificateur(String modificateur) {
		this.modificateur = modificateur;
	}

	/**
	 * @param type the type to set
	 */
	private void setType(String type) {
		this.type = type;
	}

	/**
	 * @param nom the nom to set
	 */
	private void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
