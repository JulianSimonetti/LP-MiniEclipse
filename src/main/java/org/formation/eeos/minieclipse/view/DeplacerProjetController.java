package org.formation.eeos.minieclipse.view;

import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Controleur de la fenêtre de déplacement de projet
 *
 */
public class DeplacerProjetController {
	
	private TreeParent noeud;
	@FXML
	private Text projectName;

	@FXML
	private Button accepter;
	@FXML
	private Button annuler;

	public DeplacerProjetController() {
		// TODO Auto-generated constructor stub
	}
	@FXML
	public void initialize() {
		
	}
	/**
	 * Permet au controller de connaître le Noeud sur lequel il a été appelé
	 * @param noeud
	 */
	public void setNoeud(TreeParent noeud) {
		this.noeud = noeud;
		setProjectName();
	}
	/**
	 * Set la valeur textuel du nom de projet sur la fenêtre
	 */
	public void setProjectName() {
		projectName.setText(String.join("", "\'",noeud.getNom(),"\'") );
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
	 * methode de deplacement
	 */
	public void deplacer() {
		//TODO a faire
	}
	/**
	 * Rend le bouton renommer inutilisable si le champ textuel est vide
	 */
	public void bindButtonToTextField() {
		//TODO trouver a quoi le bind
		/*BooleanBinding bind= .textProperty().isEmpty();
		accepter.disableProperty().bind(bind);*/
	}
}
