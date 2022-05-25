package org.formation.eeos.minieclipse.stockage.access.projet;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.technique.ConfigProjet;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;
import org.formation.eeos.minieclipse.stockage.access.DaoAccess;
import org.formation.eeos.minieclipse.stockage.service.ProjetPOJO;
import org.xml.sax.SAXException;

/**
 * Classe data access object permettant la creation des objet {@link Projet} du modele métier depuis l'environnement defini
 * @author farid
 *
 */

public class DaoDur implements DaoAccess<ProjetPOJO> {
	private List<ProjetPOJO> elts;
	
	public DaoDur() throws IOException, ParserConfigurationException, SAXException, TransformerException {
		init();
	}
	
	private void init() throws IOException, ParserConfigurationException, SAXException, TransformerException {
		elts = new Vector<>();
		//Dossier racine = new Dossier("D:/PROJETS/EclipseProject/jdv");
		//Dossier source = new Dossier("D:/PROJETS/EclipseProject/jdv/src");
		//Dossier classe = new Dossier("D:/PROJETS/EclipseProject/jdv/target/classes");
		//ConfigProjet d = new ConfigProjet("jdv", Paths.get("D:\\Salut"), Paths.get("D:\\Salut\\src\\main\\java"), Paths.get("D:\\Salut\\target\\classes"));
		//Projet essaie = new Projet("jdv", UtilSupp.getAllFoldersAndSubFiles(d.getRacine()),d);
		//elts.add(UtilSupp.ProjetToPojo(essaie));
	}

	@Override
	public List<ProjetPOJO> lireTous() {
		return elts;
	}

	@Override
	public void ecrireTous(List<ProjetPOJO> t) {
		elts = t;
	}

	@Override
	public ProjetPOJO lire(int cle) {
		return elts.get(cle);
	}

	@Override
	public void modifier(int cle, ProjetPOJO data) {
		elts.set(cle, data);
		
	}

	@Override
	public void supprimer(int cle) {
		elts.remove(cle);
		
	}

	@Override
	public void ajouter(ProjetPOJO data) {
		elts.add(data);
	}

}
