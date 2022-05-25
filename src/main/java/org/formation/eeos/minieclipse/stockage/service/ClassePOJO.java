package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;
import java.util.List;

import org.formation.eeos.minieclipse.model.metier.Classe;

/**
 * Pojo de la classe metier {@link Classe}
 *
 */
public class ClassePOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	private PackagePOJO pack;
	private String nom;
	private List<AttributPOJO> attributs;
	private List<ConstructeurPOJO> constructeurs;
	private List<MethodePOJO> methodes;
	private String superclasse;

	public ClassePOJO() {
		this(null, null, null, null, null, null);
	}


	public ClassePOJO(PackagePOJO pack, String nom, List<AttributPOJO> attributs, List<ConstructeurPOJO> constructeurs,
			List<MethodePOJO> methodes, String superclasse) {
		setPack(pack);
		setNom(nom);
		setAttributs(attributs);
		setConstructeurs(constructeurs);
		setMethodes(methodes);
		setSuperclasse(superclasse);
	}


	public PackagePOJO getPack() {
		return pack;
	}

	private void setPack(PackagePOJO pack) {
		this.pack = pack;
	}

	public String getNom() {
		return nom;
	}

	private void setNom(String nom) {
		this.nom = nom;
	}

	public List<AttributPOJO> getAttributs() {
		return attributs;
	}

	private void setAttributs(List<AttributPOJO> attributs) {
		this.attributs = attributs;
	}

	public List<ConstructeurPOJO> getConstructeurs() {
		return constructeurs;
	}

	private void setConstructeurs(List<ConstructeurPOJO> constructeurs) {
		this.constructeurs = constructeurs;
	}

	public List<MethodePOJO> getMethodes() {
		return methodes;
	}

	private void setMethodes(List<MethodePOJO> methodes) {
		this.methodes = methodes;
	}

	public String getSuperclasse() {
		return superclasse;
	}

	public void setSuperclasse(String superclasse) {
		this.superclasse = superclasse;
	}

}
