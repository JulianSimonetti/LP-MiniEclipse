package org.formation.eeos.minieclipse.model.technique;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;
import org.formation.eeos.minieclipse.model.metier.Classe;
import org.formation.eeos.minieclipse.model.metier.NotificationChange;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;

public class Dossier implements NotificationChange {
	private PropertyChangeSupport event = new PropertyChangeSupport(this);
	private Path chemin;
	private String nom;
	private Vector<Fichier> Fichiers;
	private Vector<Dossier> Dossiers;
	
	public Dossier(Path path) {
		setFichiers(new Vector<>());
		setDossiers(new Vector<>());
		chemin = path;
		nom = chemin.getFileName().toString();
	}

	public Vector<Dossier> getDossiers() {
		return Dossiers;
	}
	
	public void deplacer(String path) throws IOException {
		UtilitaireFichier.moveFile(this.getChemin(), path);
		
	}
	
	public Path getPath() {
		return this.chemin;
	}
	
	public String getChemin() {
		return chemin.toFile().getPath();
	}

	public void ajouter(Dossier dossier) throws IOException {
		UtilitaireFichier.createFolder(dossier.getPath().toString());
		Dossiers.add(dossier);
		event.firePropertyChange("appendFolder", dossier, dossier);
	}
	
	public void ajouter(Fichier fichier) {
		Fichiers.add(fichier);
	}
	
	public void supprimer(Dossier dossier) throws IOException {
		List<Dossier> ex = this.getDossiers();
		UtilitaireFichier.removeFolder(dossier.getPath().toString());
		Dossiers.remove(dossier);
		event.firePropertyChange("removeFolder", dossier, dossier);
	}
	
	public void supprimer(Fichier fichier) throws IOException {
		UtilitaireFichier.removeFile(fichier.getFilePath().toString());
		this.Fichiers.remove(fichier);
	}
	
	
	public void renommer(String nouveauNom) {
		setNom(nouveauNom);
		event.firePropertyChange("nom", getNom(), nouveauNom);
	}

	public void setDossiers(Vector<Dossier> dossiers) {
		this.Dossiers = dossiers;
	}

	public Vector<Fichier> getFichiers() {
		return Fichiers;
	}

	public void setFichiers(Vector<Fichier> fichiers) {
		this.Fichiers = fichiers;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public PropertyChangeSupport getEvent() {
		return event;
	}

	public void setEvent(PropertyChangeSupport event) {
		this.event = event;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.event.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.event.removePropertyChangeListener(l);
		
	}	
	
	
}
