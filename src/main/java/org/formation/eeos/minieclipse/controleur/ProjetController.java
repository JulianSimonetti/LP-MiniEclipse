package org.formation.eeos.minieclipse.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.technique.ConfigProjet;
import org.formation.eeos.minieclipse.model.technique.Dossier;
import org.formation.eeos.minieclipse.model.technique.exceptions.NotAProjectException;
import org.formation.eeos.minieclipse.model.technique.exceptions.ProjetDejaExistantException;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;
import org.formation.eeos.minieclipse.stockage.access.DaoAccess;
import org.formation.eeos.minieclipse.stockage.access.projet.DaoFactoryProjet;
import org.formation.eeos.minieclipse.stockage.service.ProjetPOJO;
import org.xml.sax.SAXException;

public class ProjetController {
	private static ProjetController instance = null;
	private DaoAccess<ProjetPOJO> access = null;
	private List<Projet> lesProjets;

	private ProjetController() {
		setLesProjets(new ArrayList<Projet>());
		try {
			access = DaoFactoryProjet.getInstance().getDao();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		init();
	}

	public static ProjetController get() {
		if (instance == null) {
			instance = new ProjetController();
		}
		return instance;
	}

	private void init() {
		access.lireTous().forEach((project) -> {
			Projet p;
			try {
				p = UtilSupp.ProjetPojoToProjet(project);
				UtilSupp.Compiler(p, null);
				try {
					UtilSupp.InitProject(p);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lesProjets.add(p);
			} catch (ParserConfigurationException | IOException | SAXException | NotAProjectException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void refresh(Projet projet) throws IOException, ClassNotFoundException, URISyntaxException, TransformerException {
		projet.renommer(projet.getConfig().getNom());
		projet.changeRacine(UtilSupp.getAllFoldersAndSubFiles(projet.getConfig().getRacine()));
		UtilSupp.Compiler(projet, null);
		UtilSupp.InitProject(projet);
	}
	
	public void refresh() throws IOException, ClassNotFoundException, URISyntaxException, TransformerException {
		for(int i=0;i<this.lesProjets.size();i++) {
			refresh(lesProjets.get(i));
		}
	}
	
	public void ajouter(Projet p) {
		lesProjets.add(p);
		UtilSupp.Compiler(p, null);
		try {
			UtilSupp.InitProject(p);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Projet p) {
		this.lesProjets.remove(p);
	}
	
	public void sauvegarder() {
		List<ProjetPOJO> ret = new Vector<ProjetPOJO>();
		this.lesProjets.forEach((element)->{
			ret.add(UtilSupp.ProjetToPojo(element));
		});
		this.access.ecrireTous(ret);
	}
	
	public List<Projet> getLesProjets() {
		return lesProjets;
	}

	public void setLesProjets(List<Projet> vector) {
		this.lesProjets = vector;
	}

}
