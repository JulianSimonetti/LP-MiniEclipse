package org.formation.eeos.minieclipse.model.metier;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.model.technique.ConfigProjet;
import org.formation.eeos.minieclipse.model.technique.Dossier;
import org.formation.eeos.minieclipse.model.technique.Fichier;
import org.formation.eeos.minieclipse.model.technique.exceptions.PackageExistantException;


public class Projet implements NotificationChange {

	private PropertyChangeSupport event = new PropertyChangeSupport(this);
	private String nom;
	private Fichier main;
	private Dossier projectLocation;
	private List<Package> lesPackages;
	private ConfigProjet config;
	
	public Projet(String nomProjet, Dossier racine,ConfigProjet configuration) {
		nom = nomProjet;
		projectLocation = racine;
		setConfig(configuration);
	}

	public Dossier getRacine(){
		return this.projectLocation;
	}

	/**
	 * Permet d'ajouter un package au projet existant.
	 * @param p le package en question
	 * @throws PackageExistantException si le package est déjà existant dans le projet.
	 */
	public void ajouterPackage(Package p) {
		this.lesPackages.add(p);
	}
	
	/**
	 * Permet d'ajouter un dossier au projet existant.
	 * @param d le fichier en question
	 * @throws IOException 
	 */
	public void ajouterDossier(Dossier d) throws IOException {
		if(this.projectLocation.getDossiers().contains(d)) {return;}
		this.projectLocation.ajouter(d);
	}
	
	public void changeRacine(Dossier d) {
		setRacine(d);
		event.firePropertyChange("racineProperty", d, d);
	}

	public String getNom() {
		return nom;
	}
	
	public void renommer(String nouveau) throws TransformerException {
		setNom(nouveau);
		getConfig().renommerProjet(nom);
	}
	
	private void setRacine(Dossier racine) {
		this.projectLocation = racine;
	}

	private void setNom(String nom) {
		this.nom = nom;
		event.firePropertyChange("nom", this.nom, nom);
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		event.addPropertyChangeListener(l);
	    }
	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
	        event.removePropertyChangeListener(l);
	        /*for(int i=0;i<event.getPropertyChangeListeners().length;i++) {
	        	event.getPropertyChangeListeners()[i] = null;
	        }*/
	    }


	public PropertyChangeSupport getEvent() {
		return event;
	}



	public void setEvent(PropertyChangeSupport event) {
		this.event = event;
	}



	public Fichier getMain() {
		return main;
	}



	public void setMain(Fichier main) {
		this.main = main;
	}



	public ConfigProjet getConfig() {
		return config;
	}



	public void setConfig(ConfigProjet config) {
		this.config = config;
	}

	public List<Package> getLesPackages() {
		return lesPackages;
	}

	public void setLesPackages(List<Package> lesPackages) {
		this.lesPackages = lesPackages;
	}




}
