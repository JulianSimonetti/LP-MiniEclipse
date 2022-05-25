package org.formation.eeos.minieclipse.view;

import java.io.IOException;

import org.formation.eeos.minieclipse.WindowFactory;
import org.formation.eeos.minieclipse.viewmodel.ProjetVueModele;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeStockee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class FileCreationController {
	@FXML
	private TreeView<TreeStockee> treeArborescence;
	
	@FXML
	private Button btnCreer;
	
	@FXML
	private Button btnAnnuler;
	
	@FXML
	private TextField txtNomFichier;
	
	@FXML
	private TextField txtParent;
	
	public void initialize() {
		treeArborescence.setRoot(null);
		treeArborescence.setShowRoot(false);
		treeArborescence.setCellFactory(pv -> new TreeCell<TreeStockee>() {
			@Override
			protected void updateItem(TreeStockee element, boolean empty) {
				super.updateItem(element, empty);
				if(empty) {
					setText(null);
				} else {
					if(element != null) {
						setText(element.getNom());
					}
				}
			}
		});
		treeArborescence.setOnMouseClicked(new EventHandler<>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY) && treeArborescence.getSelectionModel().getSelectedItem() != null) {
					txtParent.setText(treeArborescence.getSelectionModel().getSelectedItem().getValue().chemin());
				}
			}
		});
		TreeItem<TreeStockee> leParent = new TreeItem<TreeStockee>(null);
		ProjetVueModele.get().getLesPresentations().forEach((element)->{
			leParent.getChildren().add(element.enfantSansFichier());
		});
		treeArborescence.setRoot(leParent);
		treeArborescence.setShowRoot(false);
	}
	
	@FXML
	public void createFile(ActionEvent e){
		TreeStockee leNoeud = treeArborescence.getSelectionModel().getSelectedItem().getValue();
		if(txtNomFichier.getText().trim() == "") {
			WindowFactory.get().AlertMessage("Erreur", "Vous devez choisir un nom", AlertType.WARNING);
			return;
		}
		if(leNoeud == null) {
			WindowFactory.get().AlertMessage("Erreur", "Vous devez choisir un élement parent", AlertType.WARNING);
			return;
		}
		try {
			ProjetVueModele.get().creerFichier(txtParent.getText(), txtNomFichier.getText().trim());
		} catch (IOException e1) {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setTitle("Erreur");
			dialog.setContentText("Une erreur est survenue dans la création du fichier");
			return;
		}
		Node  source = (Node)  e.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void cancel(ActionEvent e) {
		Node  source = (Node)  e.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
}
