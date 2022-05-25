package org.formation.eeos.minieclipse.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.technique.Dossier;
import org.formation.eeos.minieclipse.model.technique.Fichier;
import org.formation.eeos.minieclipse.model.technique.exceptions.NotAProjectException;
import org.formation.eeos.minieclipse.model.technique.exceptions.ProjetDejaExistantException;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;
import org.formation.eeos.minieclipse.viewmodel.service.ServiceProjet;
import org.formation.eeos.minieclipse.viewmodel.tree.DossierPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.FichierPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.ProjetPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeStockee;
import org.xml.sax.SAXException;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class ProjetVueModele{
	private static ProjetVueModele instance = null;
	private ObservableList<ProjetPresentation> lesProjets;
	
	private ProjetVueModele() {
		setLesPresentations(FXCollections.observableList(new Vector<>()));
		ServiceProjet.get().getObservations().addListener(new ListChangeListener<>() {
			@Override
			public void onChanged(Change<? extends Projet> c) {
				while(c.next()) {
					if(c.wasAdded()) {
						c.getAddedSubList().forEach((element)->{
							ajouter(element);
						});
					} else if(c.wasRemoved()) {
						c.getRemoved().forEach((element)->{
							//supprimer(element);
						});
					}
				}
			}
		});
		MajLesPresentations();
	}
	
	public static ProjetVueModele get() {
		if(instance == null) {
			instance = new ProjetVueModele();
		}
		return instance;
	}
	
	private void ajouter(Projet e) {
		ProjetPresentation ret = new ProjetPresentation(e);
		this.lesProjets.add(ret);

	}
	
	private void supprimer(ProjetPresentation e) {
		this.lesProjets.remove(e);
	}
	
	public void refresh() throws IOException, ClassNotFoundException, URISyntaxException {
		ServiceProjet.get().refresh();
		if(lesProjets.size()>0) {
			lesProjets.clear();
		}
	}
	
	/**
	 * Reconstruit l'arborescence d'un projet
	 * @param projet
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void refresh(ProjetPresentation projet) throws ClassNotFoundException, IOException, URISyntaxException {
		refresh();
	}
	
	/**
	 * 
	 * @param projectFolder
	 * @throws FileNotFoundException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void ouvrirUnProjet(Path projectFolder) throws NotAProjectException, ParserConfigurationException, IOException, SAXException {
		ServiceProjet.get().ouvrir(projectFolder);		
	}
	
	/**
	 * 
	 * @param nom
	 * @param directory
	 * @param compilationFileInSameDirectory
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ProjetDejaExistantException
	 */
	public void creerProjet(String nom, String directory, boolean compilationFileInSameDirectory) throws IOException, ParserConfigurationException, SAXException, TransformerException, ProjetDejaExistantException {
		ServiceProjet.get().creerUnProjet(nom, directory, compilationFileInSameDirectory);
	}
	
	public void creerDossier(TreeStockee parent, String nom) throws IOException {
		DossierPresentation dossier = new DossierPresentation(new Dossier(UtilitaireFichier.getNIOPath(String.join(File.separator, parent.chemin(), nom))));
		parent.ajouterDossier(dossier);
		try {
			refresh();
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void creerFichier(String emplacement, String nom) throws IOException {
		ServiceProjet.get().creerUnFichier(nom, emplacement);
		try {
			refresh();
		} catch (ClassNotFoundException | URISyntaxException e) {
			e.printStackTrace();
		}

	}
	
	public void fermer(ProjetPresentation element) {
		ServiceProjet.get().close(element.getHandle());
	}
	
	private void MajLesPresentations() {
			ServiceProjet.get().getObservations().forEach((element)->{
				ajouter(element);
		});
	}

	// Demande la compilation d'un projet à partir de sa vue
	public void compile(ProjetPresentation pp, StringProperty returnMsg) {
		Projet projet= pp.getHandle();
		UtilSupp.Compiler(projet, returnMsg);
	}
	
	// Demande l'execution du projet à partir de sa vue
	public void execute(ProjetPresentation pp) {
		Projet projet= pp.getHandle();
		UtilSupp.Executer(projet);
	}
	
	public ObservableList<ProjetPresentation> getLesPresentations() {
		return this.lesProjets;
	}

	private void setLesPresentations(ObservableList<ProjetPresentation> observableList) {
		this.lesProjets = observableList;
	}
	
}
