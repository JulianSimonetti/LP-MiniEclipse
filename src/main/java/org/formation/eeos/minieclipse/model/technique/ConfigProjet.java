package org.formation.eeos.minieclipse.model.technique;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.formation.eeos.minieclipse.model.technique.exceptions.NotAProjectException;
import org.formation.eeos.minieclipse.model.technique.util.UtilitaireFichier;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigProjet {
	private String nom;
	private Document XMLDocProjet;
	private Path racine;
	private Path mainClasse;
	private Path configFile;
	private Path classePath;
	private Path output;
	private final String TAGNOMPROJET = "nom";
	private final String TAGNOMMAINCLASSE = "baseclass";
	private final String TAGNOMCLASSPATH = "classpathentry";
	private final String NOMCONFIGURATIONPROJET = ".projet";
	
	/**
	 *  Permet l'ouverture d'un projet qui est normalement déjà créer et déjà configuré correctement
	 * @param laRacine est la localisation du projet en question
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 */
	public ConfigProjet(Path laRacine) throws ParserConfigurationException, IOException, SAXException, NotAProjectException {
		boolean exist = UtilitaireFichier.leFichierExiste(String.join(File.separator, laRacine.toAbsolutePath().toString(), NOMCONFIGURATIONPROJET));
		if(!exist) {throw new NotAProjectException("Nous ne trouvons pas le fichier de configuration de ce projet.");}
		String projetConfigLoc = String.join(File.separator, laRacine.toString(), NOMCONFIGURATIONPROJET);
		setRacine(laRacine);
		setConfigurationFile(Paths.get(projetConfigLoc));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(getConfigurationFile().toFile());
		setXMLDocument(doc);
		doc.getDocumentElement().normalize();
		Node temp = doc.getElementsByTagName(TAGNOMMAINCLASSE).item(0);
		if(temp != null && temp.getFirstChild() != null) {setMainClasse(UtilitaireFichier.getNIOPath(temp.getFirstChild().getTextContent()));}
		NodeList elements = doc.getElementsByTagName(TAGNOMCLASSPATH);
		setNom(doc.getElementsByTagName(TAGNOMPROJET).item(0).getFirstChild().getTextContent());
		for(int i=0;i<elements.getLength();i++){
			Node element = elements.item(i);
			if(element.getAttributes() == null) {continue;}
			setClassePath(Paths.get(element.getAttributes().getNamedItem("path").getNodeValue()));
			setOutputPath(Paths.get(element.getAttributes().getNamedItem("output").getNodeValue()));
		}
	}
	
	/**
	 * Ce constructeur en comparaison avec le constructeur par défaut permet la création d'un fichier de configuration dans un dossier déjà établi.
	 * @param leNom
	 * @param laRacine
	 * @param classesPath
	 * @param outputClass
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public ConfigProjet(String leNom, Path laRacine, Path classesPath, Path outputClass) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = UtilitaireFichier.creerXmlFichier();
		Element nom = doc.createElement("nom");
		nom.setTextContent(leNom);
		Node racine = doc.createElement("racine");
		Attr in = doc.createAttribute("path");
		Attr out = doc.createAttribute("output");
		in.setValue(classesPath.toAbsolutePath().toString());
		out.setValue(outputClass.toAbsolutePath().toString());
		Element entry = doc.createElement("classpathentry");
		entry.getAttributes().setNamedItem(in);
		entry.getAttributes().setNamedItem(out);
		racine.appendChild(nom);
		racine.appendChild(entry);
		doc.appendChild(racine);
		setXMLDocument(doc);
		setRacine(laRacine);
        setClassePath(classesPath);
        setOutputPath(outputClass);
        setConfigurationFile(Paths.get(String.join(File.separator, laRacine.toString(), NOMCONFIGURATIONPROJET)));
        sauvegarder(getConfigurationFile().toFile());
	}
	
	public void renommerProjet(String newNom) throws TransformerException {
		this.XMLDocProjet.getElementsByTagName("nom").item(0).setTextContent(newNom);
		sauvegarder(this.configFile.toFile());
	}
	
	public void sauvegarder(File fichier) throws TransformerException  {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(this.XMLDocProjet);
        StreamResult streamResult = new StreamResult(fichier);
        transformer.transform(domSource, streamResult);
	}
	
	public Document getXMLDocument() {
		return this.XMLDocProjet;
	}
	
	private void setXMLDocument(Document p) {
		this.XMLDocProjet = p;
	}

	public Path getConfigurationFile() {
		return configFile;
	}

	public void setConfigurationFile(Path configurationFile) {
		this.configFile = configurationFile;
	}
	
	public Path getClassePath() {
		return this.classePath;
	}
	
	private void setClassePath(Path p) {
		this.classePath = p;
	}
	
	public Path getOutPutPath() {
		return this.output;
	}
	
	private void setOutputPath(Path p) {
		this.output = p;
	}

	public Path getRacine() {
		return racine;
	}

	public void setRacine(Path racine) {
		this.racine = racine;
	}

	public Path getMainClasse() {
		return mainClasse;
	}

	public void setMainClasse(Path mainClasse) {
		this.mainClasse = mainClasse;
	}
	
	public void changerMainClasse(Path nouveauFilePath) {
		this.mainClasse = nouveauFilePath;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
