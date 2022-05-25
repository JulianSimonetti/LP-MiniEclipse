package org.formation.eeos.minieclipse.stockage.access.projet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.stockage.access.FAccess;
import org.xml.sax.SAXException;

/**
 * Classe data access object permettant la creation des objet {@link Projet} du modele metier depuis le fichier defini
 * @author farid
 *
 */

public class FAccessProjet<T> extends FAccess<T> {
	
	private List<T> elts;
	
	public FAccessProjet() {
		super();
		setFichier(DaoFactoryProjet.getInstance().getProps().getProperty("nom"));
		read();
	}
	
	@Override
	protected void write()
	{
		try (ObjectOutputStream fic = new ObjectOutputStream(new FileOutputStream(fichier))) {
			fic.writeObject(elts);
		} catch (IOException | NullPointerException e) {
			System.out.println("Erreur write()");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void read() {
		try (ObjectInputStream fic = new ObjectInputStream(new FileInputStream(fichier))) {
			elts = (List<T>) fic.readObject();
		}
		catch (Exception e) {
			DaoDur dao;
			try {
				dao = new DaoDur();
				elts = (List<T>) dao.lireTous();
			} catch (IOException | ParserConfigurationException | SAXException | TransformerException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	@Override
	public List<T> lireTous() {
		if(this.elts==null) {read();}
		return this.elts;
	}
	@Override
	public T lire(int cle) {
		return this.elts.get(cle);
	}
	@Override
	public void modifier(int cle, T data) {
		this.elts.set(cle, data);
		write();
		
	}
	@Override
	public void supprimer(int cle) {
		this.elts.remove(cle);
		write();
		
	}
	@Override
	public void ajouter(T data) {
		this.elts.add(data);
		write();
	}

	@Override
	public void ecrireTous(List<T> t) {
		this.elts = t;
		write();
	}
	
}
