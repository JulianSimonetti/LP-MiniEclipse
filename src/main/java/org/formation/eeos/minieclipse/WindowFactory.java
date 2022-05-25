package org.formation.eeos.minieclipse;

import java.io.IOException;
import java.util.Optional;

import org.formation.eeos.minieclipse.model.technique.Dossier;
import org.formation.eeos.minieclipse.view.CreationClasseController;
import org.formation.eeos.minieclipse.view.CreationDossierController;
import org.formation.eeos.minieclipse.view.DeplacerProjetController;
import org.formation.eeos.minieclipse.view.FileCreationController;
import org.formation.eeos.minieclipse.view.RenameController;
import org.formation.eeos.minieclipse.viewmodel.tree.DossierPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.ProjetPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeStockee;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Factory fournissant les fenetres d'interaction de l'application
 *
 */
public class WindowFactory {
	private static WindowFactory instance = null;
	private Scene scene;
	
	private WindowFactory() {
		
	}
	
	public static WindowFactory get() {
		if(WindowFactory.instance == null) {
			WindowFactory.instance = new WindowFactory();
		}
		return WindowFactory.instance;
	}
	
	public void openCreateClass(TreeStockee doss) {
		FXMLLoader fxml = openFXMLLoader("newClasse.fxml");
		Stage stage = openStage(fxml, "Créer un fichier", Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		CreationClasseController leControl = ((CreationClasseController)(fxml.getController()));
		leControl.defaultPackage(doss);
		stage.show();
	}
	

	public Stage openStage(FXMLLoader n , String title, Modality modalite, StageStyle style) {
		Stage stage = null;
		try {
			Parent root = (Parent) n.load();
			stage = new Stage();
			stage.initModality(modalite);
			stage.initStyle(style);
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stage;
	}
	
	public FXMLLoader openFXMLLoader(String fxmlRessource) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlRessource));
		return fxmlLoader;
	}
	
	public void openDeleteFolder(TreeParent element) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setContentText("Supprimer ce dossier ?");
		Optional<ButtonType> result = dialog.showAndWait();
		if(result.get().OK == ButtonType.OK) {
			
		}
	}
	
	/**
	 * Ouvre la fenetre de création de fichier
	 */
	public void openCreateFile() {
		FXMLLoader fxmlLoader = openFXMLLoader("newFile.fxml");
		Stage stage = openStage(fxmlLoader, "Créer un fichier", Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		stage.show();
	}
	/**
	 * Ouvre la fenetre de création de package
	 */
	public void openCreatePackage() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newPackage.fxml"));
		Parent root1 = null;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Créer un package");
			scene = new Scene(root1);			
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ouvre la fenetre de création de dossier
	 */
	public void openCreateRepertory() {
		FXMLLoader fxml = openFXMLLoader("newRepertoire.fxml");
		Stage stage = openStage(fxml, "Créer un dossier", Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		stage.show();
		/* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newRepertoire.fxml"));
		Parent root1 = null;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Créer un dossier");
			CreationDossierController controller = fxmlLoader.getController();
			controller.defaultPackage(defaut);
			scene = new Scene(root1);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		} */
	}
	/**
	 * Ouvre la fenetre de création d'interface
	 */
	public void openCreateInterface() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newInterface.fxml"));
		Parent root1 = null;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Créer un package");
			scene = new Scene(root1);			
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ouvre la fenetre de création d'enumération
	 */
	public void openCreateEnum() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newEnum.fxml"));
		Parent root1 = null;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Créer une enumeration");
			scene = new Scene(root1);			
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ouvre la fenetre de création d'enumération
	 */
	public void openCreateProject() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newProject.fxml"));
		Parent root1 = null;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Créer un projet");
			scene = new Scene(root1);			
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ouvre la fenetre permettant de renommer un Noeud
	 */
	public void rename(TreeParent n) {
		FXMLLoader fxmlLoader = openFXMLLoader("rename.fxml");
		Stage stage = openStage(fxmlLoader, "Renommer", Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		((RenameController)(fxmlLoader.getController())).setNoeud(n);
		stage.show();
	}

	public void openDeplacerProjet(ProjetPresentation e) {
		FXMLLoader fxmlLoader = openFXMLLoader("moveProject.fxml");
		Stage stage = openStage(fxmlLoader, "Deplacer le projet", Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		DeplacerProjetController controller = fxmlLoader.getController();
		controller.setNoeud(e);
		stage.show();
	}
	
	public void openDeplacerDossier() {
		
	}
	
	public boolean confirmation(String title, String message) {
		boolean re = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(message);
		Optional<ButtonType> ret = alert.showAndWait();
		if(ret.isPresent() && ret.get() == ButtonType.OK) {
			re = true;
		}
		return re;
	}
	
	public void AlertMessage(String title, String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();
	}
	

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
