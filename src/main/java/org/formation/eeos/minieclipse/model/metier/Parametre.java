package org.formation.eeos.minieclipse.model.metier;

/**
 * un objet de la classe Parametre défini un paramètre d'un constructeur ou d'une methode
 * @author rayen
 *
 */

public class Parametre {

	private String nom;
	private Type type;
	/**
	 * 
	 * @param nom
	 * @param type
	 */
	public Parametre(String nom,Type type) {
		setNom(nom);
		setType(type);
	}
	/**
	 * 
	 * @return son nom 
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * 
	 * @param nom
	 */
	private void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * 
	 * @return le type d'un parametre
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type
	 */
	private void setType(Type type) {
		this.type = type;
	}

}
