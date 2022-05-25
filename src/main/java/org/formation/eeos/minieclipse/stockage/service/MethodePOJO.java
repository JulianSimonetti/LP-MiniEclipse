package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;
import java.util.List;

import org.formation.eeos.minieclipse.model.metier.Methode;
import org.formation.eeos.minieclipse.model.metier.Visibilite;
/**
 * Pojo de la classe metier {@link Methode}
 *
 */
public class MethodePOJO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nom;
	private String typeRetour;
	private List<ParametrePOJO> param;
	
	public MethodePOJO() {
		this(null,null,null);
	}
	public MethodePOJO(List<ParametrePOJO> param,String typeRetour, String nom) {
		setNom(nom);
		setParam(param);
		setTypeRetour(typeRetour);
	}

	public String getNom() {
		return nom;
	}

	private void setNom(String nom) {
		this.nom = nom;
	}

	public String getTypeRetour() {
		return typeRetour;
	}

	private void setTypeRetour(String typeRetour) {
		this.typeRetour = typeRetour;
	}

	public List<ParametrePOJO> getParam() {
		return param;
	}

	private void setParam(List<ParametrePOJO> param) {
		this.param = param;
	}

}
