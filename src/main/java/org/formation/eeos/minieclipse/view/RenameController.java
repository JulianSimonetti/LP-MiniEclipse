package org.formation.eeos.minieclipse.view;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controleur de la vue permettant de renommer un element
 *
 */
public class RenameController {

	private TreeParent noeud;
	@FXML
	private TextField textNewName;
	@FXML
	private Button accepter;

	public RenameController() {
		//vide volontairement
	}
	@FXML
	public void initialize() {
		
	}

	public TreeParent getNoeud() {
		return noeud;
	}
	/**
	 * Rend le bouton renommer inutilisable si le champ textuel est vide
	 */
	public void bindButtonToTextField() {
		BooleanBinding bind= textNewName.textProperty().isEmpty();
		accepter.disableProperty().bind(bind);
	}
	/**
	 * Set la valeur textuel du noeud sur le champ textuel
	 */
	public void setTextField() {
		textNewName.setText(noeud.getNom());
	}
	/**
	 * Récupere le noeud qui à été demander de renommer et set la fenêtre de traitement
	 * @param noeud
	 */
	public void setNoeud(TreeParent noeud) {
		this.noeud = noeud;
		setTextField();
		bindButtonToTextField();
	}
	/**
	 * renomme et ferme la fenêtre
	 */
	public void renommer() {
		noeud.rename(textNewName.getText());
		close();
	}
	/**
	 * ferme la fenetre sur laquelle le textfield est set
	 */
	public void close() {
		//peut etre un meilleur moyen de recuperer la fenetre actuelle, a creuser
		   Stage stage = (Stage) textNewName.getScene().getWindow();
		    stage.close();
	}

	
}
