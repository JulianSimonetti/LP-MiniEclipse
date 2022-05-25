package org.formation.eeos.minieclipse.stockage.service;

import java.io.Serializable;
import java.util.List;

import org.formation.eeos.minieclipse.model.metier.Package;
/**
 * Pojo de la classe metier {@link Package}
 *
 */
public class PackagePOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5837823616718743342L;
	private String namespace;
	private List<PackagePOJO> packagesPOJO;
	private List<FichierPOJO> fichiersPOJO;
	
	
	public PackagePOJO(String namespace) {
		this.setNamespace(namespace);
	}
	
	public String getNamespace() {
		return namespace;
	}

	private void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<FichierPOJO> getFichiersPOJO() {
		return fichiersPOJO;
	}

	private void setFichiersPOJO(List<FichierPOJO> fichiersPOJO) {
		this.fichiersPOJO = fichiersPOJO;
	}



	public List<PackagePOJO> getPackagesPOJO() {
		return packagesPOJO;
	}



	private void setPackagesPOJO(List<PackagePOJO> packagesPOJO) {
		this.packagesPOJO = packagesPOJO;
	}

}
