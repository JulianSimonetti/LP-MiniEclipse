package org.formation.eeos.minieclipse.viewmodel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.controleur.ProjetController;
import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.technique.ConfigProjet;
import org.formation.eeos.minieclipse.model.technique.exceptions.NotAProjectException;
import org.formation.eeos.minieclipse.model.technique.exceptions.ProjetDejaExistantException;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceProjet {
	private static ServiceProjet instance = null;
	private ObservableList<Projet> lesProjets;
	


	private ServiceProjet() {
		this.lesProjets = FXCollections.observableList(new Vector<>());
		ProjetController.get().getLesProjets().forEach((projet)->{
			try {
				init(projet);
			} catch (ClassNotFoundException | IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void init(Projet projet) throws MalformedURLException, ClassNotFoundException, IOException, URISyntaxException {
		lesProjets.add(projet);
		UtilSupp.Compiler(projet, null);
		UtilSupp.InitProject(projet);
	}
	
	public ObservableList<Projet> getObservations() {
		return this.lesProjets;
	}

	/**
	 * Permet de créer un projet sur le workspace et sur le disque
	 * @param nom
	 * @param directory
	 * @param compilationFileInSameDirectory
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ProjetDejaExistantException
	 */
	public void creerUnProjet(String nom, String directory, boolean compilationFileInSameDirectory) throws IOException, ParserConfigurationException, SAXException, TransformerException, ProjetDejaExistantException {
		String leChemin = String.join(File.separator, directory, nom);
		if(UtilitaireFichier.existDossier(leChemin)) {throw new ProjetDejaExistantException("Le projet porte le même nom qu'un dossier");}
		String src = String.join(File.separator, UtilitaireFichier.getNIOPath(leChemin).toAbsolutePath().toString(), "src", "main", "java");
		String classes = src;
		UtilitaireFichier.createFolder(leChemin);
		UtilitaireFichier.createDirectories(src);
		if (!compilationFileInSameDirectory) { classes = String.join(File.separator, UtilitaireFichier.getNIOPath(leChemin).toAbsolutePath().toString(), "target", "classes");
			UtilitaireFichier.createDirectories(classes);
		}
		ConfigProjet configuration = new ConfigProjet(nom, UtilitaireFichier.getNIOPath(leChemin), UtilitaireFichier.getNIOPath(src), UtilitaireFichier.getNIOPath(classes));
		Projet leProjet = new Projet(nom, UtilSupp.getAllFoldersAndSubFiles(configuration.getRacine()),configuration);
		ajouter(leProjet);
	}
	
	 public void refresh(Projet projet) throws IOException {
		try {
			ProjetController.get().refresh(projet);
		} catch (ClassNotFoundException | IOException | URISyntaxException | TransformerException e) {
			e.printStackTrace();
		}
	}
	 
	public void creerUnFichier(String nom, String directory) throws IOException {
		String leChemin = String.join(File.separator, directory, nom);
		UtilitaireFichier.createFile(leChemin);
	}
	
	public void creerUnDossier(String nom, String directory) throws IOException {
		String leChemin = String.join(File.separator, directory, nom);
		UtilitaireFichier.createDirectories(leChemin);
	}
	 
	public void ajouter(Projet p) {
		ProjetController.get().ajouter(p);
		this.lesProjets.add(p);
		
	}
	
	public void refresh() {
		try {
			ProjetController.get().refresh();
			this.lesProjets.clear();
			ProjetController.get().getLesProjets().forEach((projet)->{
				try {
					init(projet);
				} catch (ClassNotFoundException | IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			});
		} catch (ClassNotFoundException | TransformerException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Projet projet) {
		ProjetController.get().close(projet);
		this.lesProjets.remove(projet);
	}
	
	public static ServiceProjet get() {
		if(instance == null) {
			instance = new ServiceProjet();
		}
		return instance;
	}
	
	public void ouvrir(Path leProjet) throws NotAProjectException, ParserConfigurationException, IOException, SAXException {
		ConfigProjet p = new ConfigProjet(leProjet);
		Projet leRet = new Projet(p.getNom(), UtilSupp.getAllFoldersAndSubFiles(p.getRacine()), p);
		ajouter(leRet);
	}
	
}
