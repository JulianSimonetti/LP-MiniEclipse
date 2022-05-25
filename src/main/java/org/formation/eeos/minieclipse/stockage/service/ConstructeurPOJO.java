package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;
import java.util.List;

import org.formation.eeos.minieclipse.model.metier.Constructeur;
/**
 * Pojo de la classe metier {@link Constructeur}
 *
 */
public class ConstructeurPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String visibilite;
	private List<ParametrePOJO> params;
	public ConstructeurPOJO() {
		this(null,null,null);
	}
	
	public ConstructeurPOJO(String nom, String visibilite, List<ParametrePOJO> params) {
		setNom(nom);
		setParams(params);
		setVisibilite(visibilite);
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @return the visibilite
	 */
	public String getVisibilite() {
		return visibilite;
	}
	/**
	 * @return the params
	 */
	public List<ParametrePOJO> getParams() {
		return params;
	}
	/**
	 * @param nom the nom to set
	 */
	private void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @param visibilite the visibilite to set
	 */
	private void setVisibilite(String visibilite) {
		this.visibilite = visibilite;
	}
	/**
	 * @param params the params to set
	 */
	private void setParams(List<ParametrePOJO> params) {
		this.params = params;
	}
	
}
