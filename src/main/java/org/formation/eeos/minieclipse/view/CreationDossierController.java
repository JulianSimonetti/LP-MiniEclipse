package org.formation.eeos.minieclipse.view;

import java.io.IOException;

import org.formation.eeos.minieclipse.viewmodel.ProjetVueModele;
import org.formation.eeos.minieclipse.viewmodel.tree.DossierPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeStockee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controleur de la vue permettant la création de Dossier
 *
 */
public class CreationDossierController {
	
	
	@FXML
	private TextField txtParent;
	
	@FXML
	private TreeView<TreeStockee> treeArborescence;
	
	@FXML
	private TextField txtNomDossier;
	
	public void initialize() {
		treeArborescence.setRoot(null);
		treeArborescence.setShowRoot(false);
		treeArborescence.setCellFactory(pv-> new TreeCell<TreeStockee>(){
			@Override
			protected void updateItem(TreeStockee element, boolean empty) {
				if(empty) {
					setText(null);
				} else {
					if(element != null) {
						setText(element.getNom());
					}
				}
			}
		});
		TreeItem<TreeStockee> t = new TreeItem<TreeStockee>(null);
		ProjetVueModele.get().getLesPresentations().forEach((element)->{
			t.getChildren().add(element.enfantSansFichier());
		});
		treeArborescence.setOnMouseClicked(e->{
			if(treeArborescence.getSelectionModel().getSelectedItem() != null) {
				txtParent.setText(treeArborescence.getSelectionModel().getSelectedItem().getValue().chemin());
			}
		});
		treeArborescence.setRoot(t);
		treeArborescence.setShowRoot(false);
	}
	
	@FXML
	public void createFolderBtn(ActionEvent e) {
		try {
			ProjetVueModele.get().creerDossier(treeArborescence.getSelectionModel().getSelectedItem().getValue(), txtNomDossier.getText());
		} catch (IOException e1) {
			Dialog<String> error = new Dialog<String>();
			error.initModality(Modality.APPLICATION_MODAL);
			error.setContentText("Impossible de créer un dossier à cette adresse.");
			error.show();
			return;
		}
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void btnAnnulerAction(ActionEvent e) {
		Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		stage.close();
	}

}
