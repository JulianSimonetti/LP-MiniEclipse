package org.formation.eeos.minieclipse.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.event.DocumentEvent.EventType;
import javax.xml.parsers.ParserConfigurationException;
import org.formation.eeos.minieclipse.WindowFactory;
import org.formation.eeos.minieclipse.model.technique.exceptions.NotAProjectException;
import org.formation.eeos.minieclipse.model.technique.util.Compiler;
import org.formation.eeos.minieclipse.model.technique.util.Executer;
import org.formation.eeos.minieclipse.model.technique.util.UtilSupp;
import org.formation.eeos.minieclipse.view.components.CodeEditor;
import org.formation.eeos.minieclipse.view.components.TabFile;
import org.formation.eeos.minieclipse.viewmodel.ProjetVueModele;
import org.formation.eeos.minieclipse.viewmodel.tree.FichierPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.ProjetPresentation;
import org.formation.eeos.minieclipse.viewmodel.tree.TreeParent;
import org.xml.sax.SAXException;

import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;

public class MainController {

	private HashMap<FichierPresentation, TabFile> lesTabsOuvertes;
	@FXML
	private TextArea laZoneZone;
	
	@FXML
	private TextArea consoleTextArea;
	
	@FXML
	private Button primaryButton;

	@FXML
	private TabPane tabSourceEditor;

	@FXML
	private Button runAppId;
	
	@FXML
	private Button modelButton;

	@FXML
	private Button viewButton;

	@FXML
	private TreeView<TreeParent> treeProjets;
	
	@FXML
	private TreeView <TreeParent> treeViewOutline;

	public void initialize() {
		lesTabsOuvertes = new HashMap<FichierPresentation, TabFile>();
		Image img = new Image("216311_media_play_icon.png");
		ImageView view = new ImageView(img);
		view.setFitHeight(30);
		view.setPreserveRatio(true);
		runAppId.setPrefSize(30, 30);
		runAppId.setGraphic(view);
		treeProjets.setCellFactory(pv -> new TreeCell<TreeParent>() {
			@Override
			protected void updateItem(TreeParent element, boolean empty) {
				super.updateItem(element, empty);
				if (empty) {
					setText(null);
				} else {
					if (element != null) {
						setText(element.getNom());
						setContextMenu(element.getContextMenu());
					}
				}
			}
		});
		treeViewOutline.setCellFactory(pv->new TreeCell<>() {
			@Override
			protected void updateItem(TreeParent element, boolean empty) {
				super.updateItem(element, empty);
				if (empty) {
					setText(null);
				} else if (element != null) {
					setText(element.getNom());
					setContextMenu(element.getContextMenu());
				}
			}});
		treeProjets.setShowRoot(false);
		treeProjets.setContextMenu(getContextProjectsExplo());
		
		TreeItem<TreeParent> root = new TreeItem<TreeParent>(null);
		treeProjets.setRoot(root);
		ProjetVueModele.get().getLesPresentations().forEach((p) -> {
			TreeItem<TreeParent> element = p.children();
			treeProjets.getRoot().getChildren().add(element);
		});
			
		ProjetVueModele.get().getLesPresentations().addListener(new ListChangeListener<>() {
			@Override
			public void onChanged(Change<? extends ProjetPresentation> c) {
				while(c.next()) {
					if(c.wasAdded()) {
						treeProjets.getRoot().getChildren().add(c.getList().get(c.getFrom()).children());
					}
					if(c.wasRemoved()) {
						treeProjets.getRoot().getChildren().remove(c.getFrom());
					}
				}
			}
		});
		
		tabSourceEditor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				TabFile d = (TabFile)newValue;
				
				if(d != null && d.getFichierCorrespondant().getLesClasses().size()>0) {
					TreeItem<TreeParent> leRoot = new TreeItem<TreeParent>(null);
					treeViewOutline.setRoot(leRoot);
					treeViewOutline.setShowRoot(false);
					d.getFichierCorrespondant().getLesClasses().forEach((uneClasse)->{
						 TreeItem<TreeParent> classRoot = uneClasse.children();
						 treeViewOutline.getRoot().getChildren().add(classRoot);
					});
				} else {
					treeViewOutline.setRoot(null);
				}
			}
		});
		
		// Ajoute un évènement pour gérer le double clic sur un élement de la project explorer.
		treeProjets.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
					&& !treeProjets.selectionModelProperty().get().isEmpty()) {
				TreeParent item = treeProjets.getSelectionModel().getSelectedItem().getValue();
				if (item.getClass() != FichierPresentation.class) {
					return;
				}
				FichierPresentation element = (FichierPresentation) item;
				if(lesTabsOuvertes.containsKey(item)) {
					tabSourceEditor.getSelectionModel().select(lesTabsOuvertes.get(item));
				} else {
					CodeEditor in = new CodeEditor(element.getContenu().getValue());
					TabFile tabFile = new TabFile(element, item.getNom(), in);
					tabFile.textProperty().bindBidirectional(element.nomProperty());
					tabFile.setOnCloseRequest(e->{
						lesTabsOuvertes.remove(item);
					});
					tabSourceEditor.getTabs().add(tabFile);
					tabSourceEditor.getSelectionModel().select(tabFile);
					lesTabsOuvertes.put(element, tabFile);
				}
			}
		});
	}

	/**
	 * Permet de créer un context menu par défaut pour l'explorateur de projet si aucun élement TreeParent n'est sélectionné.
	 * @return
	 */
	private ContextMenu getContextProjectsExplo() {
		ContextMenu ret = new ContextMenu();
		Menu nouveau = new Menu("Nouveau");
		MenuItem newProject = new MenuItem("Projet");
		newProject.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				WindowFactory.get().openCreateProject();
			}
		});
		nouveau.getItems().add(newProject);
		ret.getItems().add(nouveau);
		return ret;
	}
	
	@FXML
	public void openProjet(ActionEvent e) {
		Alert error = new Alert(AlertType.ERROR);
		error.initModality(Modality.APPLICATION_MODAL);
		error.setContentText("Une erreur est survenue lors de l'ouverture du projet");
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(((MenuItem)e.getTarget()).getParentPopup().getScene().getWindow());
		try {
			ProjetVueModele.get().ouvrirUnProjet(selectedDirectory.toPath());
		} catch (ParserConfigurationException | IOException | SAXException e1) {
			error.show();
			return;
		} catch (NotAProjectException NoAException) {
			error.setContentText(NoAException.getMessage());
			error.show();
			return;
		}
	}
	
	/**
	 * Permet de lancer le programme d'un projet.
	 * @param e
	 */
	@FXML
	public void runApp(ActionEvent e) {
		TreeItem<TreeParent> item = treeProjets.getSelectionModel().selectedItemProperty().getValue();
		if(item == null) {
			Alert error = new Alert(AlertType.ERROR);
			error.initModality(Modality.APPLICATION_MODAL);
			error.setContentText("Veuillez sélectionner un projet");
			error.showAndWait();
			
			return;
		}else {
			ProjetPresentation projetPresentation = (ProjetPresentation)item.getValue();
			ProjetVueModele.get().compile(projetPresentation, consoleTextArea.textProperty());
		}
	}
}
