package org.formation.eeos.minieclipse.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.controleur.ProjetController;
import org.formation.eeos.minieclipse.model.technique.exceptions.ProjetDejaExistantException;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;
import org.formation.eeos.minieclipse.viewmodel.ProjetVueModele;
import org.formation.eeos.minieclipse.viewmodel.service.ServiceProjet;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controleur de la vue permettant la création de Projet
 *
 */
public class CreationProjetController {
	
	@FXML
	public TextField txtNom;
	
	@FXML
	public TextField txtDossierParent;
	
	@FXML 
	public RadioButton radioMemeDossier;
	
	@FXML
	public RadioButton radioSepare;
	
	public CreationProjetController() {
		
	}
	@FXML
	public void initialize() {
		
	}
	
	@FXML
	public void parcourirDossier(ActionEvent e) {
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(stage);
		txtDossierParent.setText(selectedDirectory.toPath().toAbsolutePath().toString());
	}
	
	/**
	 * Cette action permet de
	 * @param e évènement relative au clic sur le bouton
	 */
	@FXML
	public void addProject(ActionEvent e) {
		if(txtNom.getText().trim() == "" || txtDossierParent.getText().trim() == "") {
			Alert error = new Alert(AlertType.WARNING);
			error.initModality(Modality.APPLICATION_MODAL);
			error.setTitle("Information manquante");
			error.setContentText("Une information est manquante à la création du projet");
			error.show();
			return;
		}
		boolean sameFolder = radioMemeDossier.isSelected();
		try {
			ProjetVueModele.get().creerProjet(txtNom.getText(), txtDossierParent.getText(), sameFolder);
		} catch (IOException | ParserConfigurationException | SAXException | TransformerException e1) {
			Alert error = new Alert(AlertType.ERROR);
			error.initModality(Modality.APPLICATION_MODAL);
			error.setTitle("Erreur");
			error.setContentText("Une erreur est survenue lors de la création du projet");
			error.show();
			return;
		} catch (ProjetDejaExistantException ex) {
			Alert error = new Alert(AlertType.ERROR);
			error.initModality(Modality.APPLICATION_MODAL);
			error.setTitle("Erreur de nom");
			error.setContentText("Un projet avec le même nom existe déjà !");
			error.showAndWait();
			return;
		}
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		stage.close();
	}

	
	@FXML
	public void closeAndCancel(ActionEvent e) {
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		stage.close();
	}
	
	}
