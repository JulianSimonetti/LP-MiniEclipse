package org.formation.eeos.minieclipse.model.metier;

/**
 *  Representation metier d'un attribut rempli depuis JavaReflect
 *
 */

public class Attribut{
	private String nom;
	private Type type;
	/**
	 * Constructeur complet
	 * @param nom
	 * @param visibilite
	 * @param type
	 * @param modificateur
	 */
	public Attribut(String n) {
		this.nom = n;
		setType(type);
	}
	/**
	 * Constructeur si visibilit non renseigne
	 * Constructeur si visibilit� non renseign�e
	 * @param nom
	 * @param type
	 * @param modificateur
	 */
	public Attribut(String nom, Type type) {
		this(nom);
		this.type = type;		
	}
	
	public String getNom() {
		return this.nom;
	}

	public Type getType() {
		return type;
	}

	private void setType(Type type) {
		this.type = type;
	}
}
