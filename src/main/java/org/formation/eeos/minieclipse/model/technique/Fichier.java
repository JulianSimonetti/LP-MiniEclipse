package org.formation.eeos.minieclipse.model.technique;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import org.formation.eeos.minieclipse.model.metier.Classe;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;

public class Fichier {

	private Dossier _racine;
	private String nom;
	private String contenu;
	private Vector<Classe> classes;
	private Path filePath;

	public Fichier(String nom, String contenu, Path lePath) {
		setNomFichier(nom);
		setContenu(contenu);
		setClasses(new Vector<Classe>());
		setFilePath(lePath);
	}

	public String getNomFichier() {
		return nom;
	}

	public void setNomFichier(String nomFichier) {
		this.nom = nomFichier;
	}
	
	
	public void ajouter(Classe classe) {
		classes.add(classe);
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}	

	public Dossier getDossier() {
		return _racine;
	}

	public void setDossier(Dossier dossier) {
		this._racine = dossier;
	}

	public void modifier(String contenu) throws IOException {
		setContenu(contenu);
		UtilitaireFichier.writeToFile(this.filePath.toString(), contenu);
	}
	
	public String cheminComplet() {
		return String.join("", this.getDossier().getChemin(), this.nom);
	}

	public void renommer(String nom) throws IOException {
		String nouveau = String.join(File.separator, this.filePath.toString(), nom);
		if(this.filePath.toFile().renameTo(UtilitaireFichier.getNIOPath(nouveau).toFile())) {
			setNomFichier(nom);
			setFilePath(UtilitaireFichier.getNIOPath(nouveau));
		};
		
		//UtilJulian.removeFile(ancien);
		//UtilJulian.createFile(nouveau);
		//UtilJulian.writeToFile(nouveau, getContenu());
	}

	public void deplacer(Dossier emplacement) throws IOException {
	//	String ancien = String.join("/", getDossier().getChemin(), getNomFichier());
		//String nouveau = String.join("/", emplacement.getChemin(), getNomFichier());
		//UtilJulian.removeFile(ancien);
		//UtilJulian.createFile(nouveau);
		//UtilJulian.writeToFile(nouveau, getContenu());
		setDossier(emplacement);
	}

	public void sauvegarder() throws IOException {
		String tempPath = String.join("/", getDossier().getChemin(), getNomFichier());
		UtilitaireFichier.createFile(tempPath);
		UtilitaireFichier.writeToFile(tempPath, getContenu());
	}

	public Vector<Classe> getClasses() {
		return classes;
	}

	public void setClasses(Vector<Classe> classes) {
		this.classes = classes;
	}

	public Path getFilePath() {
		return filePath;
	}

	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}
}
