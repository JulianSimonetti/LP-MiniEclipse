package org.formation.eeos.minieclipse.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controleur de la vue permettant la création de Package
 *
 */
public class CreationPackageController {
	@FXML
	private Button btnCreer;
	@FXML
	private Button btnAnnuler;
	@FXML
	private TextField txtDossierSource;
	@FXML
	private TextField txtNomPackage;
	
	public CreationPackageController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * ferme la fenetre sur laquelle le btnAnnuler est set
	 */
	@FXML
	public void close(ActionEvent e) {
		Node  source = (Node)  e.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	/**
	 * Rend le bouton renommer inutilisable si le champ textuel est vide
	 */
	public void bindButtonToTextField() {
		//TODO trouver a quoi le bind
		/*BooleanBinding bind= .textProperty().isEmpty();
		btnCreer.disableProperty().bind(bind);*/
	}
	/**
	 * methode de creation de Package
	 */
	public void creerPackage() {
		
	}
}
