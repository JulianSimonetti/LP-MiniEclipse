package org.formation.eeos.minieclipse.view;

import org.formation.eeos.minieclipse.viewmodel.tree.TreeStockee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controleur de la vue permettant la création de Classe
 *
 */
public class CreationClasseController {
	
	@FXML
	private Button btnCreer;
	@FXML
	private Button btnParcourirSource;
	@FXML
	private Button btnAnnuler;
	@FXML
	private Button btnParcourirPackage;
	@FXML
	private Button btnParcourirSuperclasse;
	@FXML
	private Button btnAjouterInterface;
	@FXML
	private Button brnRetirerInterface;
	@FXML
	private TextField txtDossierSource;
	@FXML
	private TextField txtNomPackage;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtSuperclasse;
	@FXML
	private RadioButton radioPublic;
	@FXML
	private RadioButton radioPrivate;
	@FXML
	private RadioButton radioProtected;
	@FXML
	private RadioButton radioPackage;
	@FXML
	private CheckBox checkAbstract;
	@FXML
	private CheckBox checkFinal;
	@FXML
	private CheckBox checkStatic;
	@FXML
	private CheckBox checkMain;
	@FXML
	private CheckBox checkConstructeurs;
	@FXML
	private CheckBox checkMethAbstraites;
	@FXML
	private CheckBox checkCommentaires;
	/*@FXML
	private ListView<T> listInterfaces;*/
	
	
	@FXML
	public void initialize() {
		
	}

	/**
	 * Rend le bouton renommer inutilisable si le champ textuel est vide
	 */
	public void bindButtonToTextField() {
		
	}
	/**
	 * methode de creation de classe
	 */

	@FXML
	public void createClassBtn(ActionEvent e) {
		
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

	@FXML
	public void btnParcourirSourceAction(ActionEvent e) {
		
	}
	
	@FXML
	public void btnParcourirPackageAction(ActionEvent e) {
		
	}
	
	@FXML
	public void cancelBtn(ActionEvent e) {
		
	}
public void defaultPackage(TreeStockee t) {
		
	}


}
