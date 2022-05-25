package org.formation.eeos.minieclipse.model.technique.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.formation.eeos.minieclipse.model.metier.Attribut;
import org.formation.eeos.minieclipse.model.metier.Classe;
import org.formation.eeos.minieclipse.model.metier.Constructeur;
import org.formation.eeos.minieclipse.model.metier.Methode;
import org.formation.eeos.minieclipse.model.metier.Package;
import org.formation.eeos.minieclipse.model.metier.Parametre;
import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.metier.Type;
import org.formation.eeos.minieclipse.model.metier.TypeMethode;
import org.formation.eeos.minieclipse.model.metier.Visibilite;
import org.formation.eeos.minieclipse.model.technique.ConfigProjet;
import org.formation.eeos.minieclipse.model.technique.Dossier;
import org.formation.eeos.minieclipse.model.technique.Fichier;
import org.formation.eeos.minieclipse.model.technique.exceptions.NotAProjectException;
import org.formation.eeos.minieclipse.stockage.service.ProjetPOJO;
import org.xml.sax.SAXException;

import javafx.beans.property.StringProperty;

/**
 * @author Farid
 */

public class UtilSupp {
	private static UtilSupp instance = null;
	
	
	private UtilSupp() {
		
	}
	
	public static Class<?> getClasses(Path repertoire, String nom) throws ClassNotFoundException, MalformedURLException {
		Class<?> ret = null;
        // Créer un class loader à partir d'un répertoire.
        ClassLoader cl = new URLClassLoader(new URL[] {
        // Créer une url, exemple : D:/PROJETS/Eclipse/jdv/target/classes/
            repertoire.toUri().toURL()
        });
        // Ici on spécifiz le nom de la classe à charger, cela inclut son package à partir du répertoire, exemple : org.vue.Lion ?
        ret = cl.loadClass(nom);
		return ret;
	}
	
	public static List<Classe> getClassesList(Projet p){
		List<Classe> lesClasses = new Vector<>();
		return null;
	}
	
	public static List<Path> getJavasFromSource(Path source) throws IOException{
		File[] p = source.toFile().listFiles();
		List<Path> ret = new Vector<>();
		if(p!=null) {
			for(int i=0;i<p.length;i++) {
				if(p[i].isFile() && p[i].getName().endsWith(".java")) {
					ret.add(p[i].toPath());
				} else {
					ret.addAll(getJavasFromSource(p[i].toPath()));
				}
			}
		}
		return ret;
	}
	
	public static void Compiler(Projet projet, StringProperty returnMessage) {
		Path source = projet.getConfig().getClassePath();
		Path output = projet.getConfig().getOutPutPath();
		CompilExec compileExecution = new CompilExec(projet.getConfig().getRacine().toString(), source.toString(), "", output.toString(), "", "");
		compileExecution.compile(returnMessage);
	}
	public static void Executer(Projet projet) {
		Path source = projet.getConfig().getClassePath();
		Path output = projet.getConfig().getOutPutPath();
		CompilExec compileExecution = new CompilExec(projet.getConfig().getRacine().toString(), source.toString(), "", output.toString(), "", "");
		compileExecution.execute();
	}

	
	public static List<Fichier> getJavas(Dossier dossier){
		 List<Fichier> elements = new Vector<>();
		/* try(Stream<Path> walk = Files.walk(dossier)){
			walk.filter(Files::isRegularFile).forEach((fichier)->{
				
			});
		} */
		dossier.getFichiers().forEach((e)->{
			if(e.getNomFichier().endsWith(".java")) {
				elements.add(e);
			}
		});
		dossier.getDossiers().forEach((e)->{
			elements.addAll(getJavas(e));
		});
		return elements;
	}
	
	/**
	 * 
	 * @param leProjet
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClassNotFoundException
	 */
	public static void InitProject(Projet leProjet) throws MalformedURLException, IOException, URISyntaxException {
		Path source = leProjet.getConfig().getClassePath();
		Path output = leProjet.getConfig().getOutPutPath();
		
		List<Fichier> ret = getJavas(leProjet.getRacine());
		for(int i=0;i<ret.size();i++) {
			Fichier java = ret.get(i);
			String namespace = java.getFilePath().toString().replace(source.toString()+File.separator, "").replace(File.separator, ".");
        	namespace = namespace.substring(0, namespace.lastIndexOf(".java"));
			try {
				Class<?> sec = getClasses(output, namespace);
				java.ajouter(ClassToClasse(sec));
			} catch (ClassNotFoundException e) {
				//TODO
			}
			
		}
	}
	
	/**
	 * Convertit une class<?> d'origine en Classe métier
	 * @param la classe fourni par JAVA
	 * @return la classe avec ses constructeurs, attributs et méthodes métiers.
	 */
	private static Classe ClassToClasse(Class<?> laClasse) {
		
		List<Constructeur> lesConstructeurs = new Vector<>();
		List<Methode> lesMethodes = new Vector<>();
		List<Attribut> lesAttributs = new Vector<>();
		for(int o=0;o<laClasse.getDeclaredConstructors().length ;o++) {
			Constructor<?> el = laClasse.getDeclaredConstructors()[o];
			List<Parametre> lesParametres = new Vector<>();
			for(int a=0;a<el.getParameters().length;a++) {
				Parameter param = el.getParameters()[a];
				Parametre m = new Parametre(param.getName(), new TypeMethode(param.getType()));
				lesParametres.add(m);
			}
			Constructeur constructeur = new Constructeur(el.getName(), lesParametres);
			lesConstructeurs.add(constructeur);
		}
		for(int i=0;i<laClasse.getDeclaredMethods().length;i++) {
			Method uneMethode = laClasse.getDeclaredMethods()[i];
			List<Parametre> lesParam = new Vector<>();
			for(int a=0;a<uneMethode.getParameters().length;a++) {
				lesParam.add(parameterToParametre(uneMethode.getParameters()[a]));
			}
			lesMethodes.add(new Methode(uneMethode.getName(), new TypeMethode(uneMethode.getReturnType()), lesParam));
		}
		return new Classe(laClasse.getName(), lesConstructeurs, lesMethodes, lesAttributs);
	}
	
	private static Parametre parameterToParametre(Parameter leParam) {
		Parametre ret = new Parametre(leParam.getName(), new TypeMethode(leParam.getType()));
		return ret;
	}
	
	/***
	 * 
	 * @param directory
	 * @return
	 * @throws IOException 
	 */
	
	public static List<Dossier> lesDossiersEtSousDossiers(Path dire) throws IOException {
		File racine = dire.toFile();
		List<Dossier> ret = new Vector<>();
		File[] fic = racine.listFiles();
		for(int i=0;i<fic.length;i++) {
			File e = fic[i];
			Dossier doss = new Dossier(e.toPath());
			ret.add(doss);
			if(e.isFile()) {
				//String p =UtilJulian.readFromFile(e.getPath());
				Fichier f = new Fichier(e.getName(), "contenu", e.toPath());
				doss.ajouter(f);
			} else {
				ret.addAll(lesDossiersEtSousDossiers(e.toPath()));
			}
		}
		return ret;
	}
	
	public static Dossier getAllFoldersAndSubFiles(Path directory) throws IOException {
		
		File racine = directory.toFile();
		Dossier ret = new Dossier(directory);
		File[] fic = racine.listFiles();
		for(int i=0;i<fic.length;i++) {
			if(fic[i].isDirectory()) {
				ret.ajouter(getAllFoldersAndSubFiles(fic[i].toPath()));
			} else if(fic[i].isFile() && !fic[i].isHidden()) {
				//String co = UtilJulian.readFromFile(fic[i].getAbsolutePath());
				Scanner scan = new Scanner(fic[i]).useDelimiter("\\Z");
				String content = "";
				if(scan.hasNext()) {
					content = scan.next();
				}
				Fichier fi = new Fichier(fic[i].getName(), content, fic[i].toPath());
				ret.ajouter(fi);
			}
		}
		return ret;
	}
	
	/***
	 * 
	 * @param <T>
	 * @param directory
	 * @return 
	 * @return
	 * @throws IOException 
	 */
	public static List<Dossier> getAllFoldersAndSubFolders(String directory) throws IOException{
		List<Dossier> ret = new Vector<>();
		File racine = UtilitaireFichier.getNIOPath(directory).toFile();
		File[] fic = racine.listFiles();
		for(int i=0;i<fic.length;i++) {
			if(fic[i].isDirectory()) {
				Dossier f = new Dossier(fic[i].toPath());
				f.ajouter(f);
			}
		}
		return ret;
	}
	
	public static void copy(Dossier dossier, String destination) throws IOException {
		String ancien = dossier.getChemin();
		UtilitaireFichier.copyFile(dossier.getChemin(), destination);
		//dossier.setChemin(destination);
		for(int i=0;i<dossier.getFichiers().size();i++) {
			dossier.getFichiers().get(i).deplacer(dossier);
		}
		for(int i = 0;i<dossier.getDossiers().size();i++) {
			copy(dossier.getDossiers().get(i), String.join("/", dossier.getChemin(), dossier.getNom()));
		}
		UtilitaireFichier.removeFolder(ancien);
	}
	
	public static Projet ProjetPojoToProjet(ProjetPOJO pojo) throws NotAProjectException, ParserConfigurationException, IOException, SAXException {
		ConfigProjet config = new ConfigProjet(UtilitaireFichier.getNIOPath(pojo.getRacine()));
		Projet ret = new Projet(pojo.getNom(), getAllFoldersAndSubFiles(config.getRacine()),config);
		return ret;
	}
	
	public static ProjetPOJO ProjetToPojo(Projet metier) {
		ProjetPOJO pj = new ProjetPOJO(metier.getNom(), metier.getConfig().getRacine().toString());
		return pj;
		
	}
	
	

}
