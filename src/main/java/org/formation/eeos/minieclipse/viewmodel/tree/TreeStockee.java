package org.formation.eeos.minieclipse.viewmodel.tree;

import java.nio.file.Path;

import org.formation.eeos.minieclipse.WindowFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;

public abstract class TreeStockee extends TreeParent {

	/*
	 * Construit le contexte menu commun à chaque treeItem du project explorer (commun du projet/dossier/fichier
	 */
	@Override
	public ContextMenu getContextMenu() {
		TreeStockee handle = this;
		ContextMenu c = new ContextMenu();
		Menu nouveau = new Menu("Nouveau");
		MenuItem newProject = new MenuItem("Projet");
		MenuItem newFile = new MenuItem("Fichier");
		MenuItem newDossier = new MenuItem("Dossier");
		MenuItem newClass = new MenuItem("Classe");
		MenuItem renommer = new MenuItem("Renommer");
		MenuItem supprimer = new MenuItem("Supprimer");
		newClass.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				WindowFactory.get().openCreateClass(handle);				
			}
		});
		newFile.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				WindowFactory.get().openCreateFile();
			}
		});
		newDossier.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				WindowFactory.get().openCreateRepertory();
			}
		});
		supprimer.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
			}
		});
		renommer.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				WindowFactory.get().rename(handle);
			}
		});
		nouveau.getItems().add(newProject);
		nouveau.getItems().add(new SeparatorMenuItem());
		nouveau.getItems().add(newFile);
		nouveau.getItems().add(newDossier);
		nouveau.getItems().add(newClass);
		c.getItems().add(nouveau);
		c.getItems().add(renommer);
		c.getItems().add(supprimer);
		return c;
	}
	
	public TreeItem<TreeStockee> enfantSansFichier() {
		return null;
	}
	
	public Path getPath() {
		return null;
	}

}
