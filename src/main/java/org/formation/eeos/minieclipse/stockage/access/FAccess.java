package org.formation.eeos.minieclipse.stockage.access;

import java.util.List;
/**
 * classe mere des Faccess
 * @author farid
 *
 */

public abstract class FAccess<T> implements DaoAccess<T> {

	protected String fichier;
	
	public FAccess() {
	
	}
	/**
	 * 
	 * méthode privée d'écriture de la collection
	 * 
	 */
	protected void write()
	{
		
	}

	/**
	 * méthode privée de lecture de la collection dans, le fichier
	 */
	protected void read() {
		
	}
	
	
	@Override
	public List<T> lireTous() {
		return null;
	}
	@Override
	public void ecrireTous(List<T> t) {
		write();
	}
	@Override
	public T lire(int cle) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void modifier(int cle, T data) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void supprimer(int cle) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ajouter(T data) {
	
	}

	public String getFichier() {
		return fichier;
	}

	public void setFichier(String fichier) {
		this.fichier = fichier;
	}

}
