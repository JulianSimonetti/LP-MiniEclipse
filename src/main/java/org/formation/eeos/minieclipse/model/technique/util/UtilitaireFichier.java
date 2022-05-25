package org.formation.eeos.minieclipse.model.technique.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.stream.Stream;

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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utilitaire pour les accès fichiers. Utilise Java NIO.
 * Pour les méthodes pouvant demander un encodage, celui par défaut est UTF-8.
 * @author Julian Simonetti
 *
 */
public interface UtilitaireFichier {
	
	/**
	 * Retourne l'objet NIO Path pour un chemin donné
	 * @param path La String du chemin
	 * @return L'objet Path
	 */
	public static Path getNIOPath(String path) {
		return Paths.get(path);
	}
	
	/**
	 * Créer les dossiers et sous dossiers dans un path s'ils n'existent pas déjà sur le disque.
	 * @param path : est le chemin vers lequel nous souhaitons créer les dossiers
	 * @throws IOException : si impossibilité de créer les dossiers en question.
	 */
	public static void createDirectories(String path) throws IOException {
		Files.createDirectories(Paths.get(path));
	}
	
	public static boolean leFichierExiste(String path){
		File f = getNIOPath(path).toFile();
		boolean ret = false;
		if(f.exists() && f.isFile()) {
			ret = true;
		}
		return ret;
	}
	
	
	
	/**
	 * Vérifie si un fichier ou un dossier de chemin donné existe.
	 * Méthode publique car peut s'avérer utile sans pour autant 
	 * chercher à interagir avec ledit fichier ou dossier.
	 * @param path La String du chemin
	 * @return Booléen, le fichier ou dossier existe/n'existe pas
	 */
	public static boolean existsFileOrFolder(String path) {
		return Files.exists(getNIOPath(path));
	}
	
	public static boolean existDossier(String path) {
		return Files.isDirectory(getNIOPath(path), LinkOption.NOFOLLOW_LINKS);
	}
	
	/**
	 * Crée le fichier de chemin+nom donné.
	 * Anticipe l'existence prématurée du fichier et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @throws IOException
	 */
	public static void createFile(String path) throws IOException {
		if (!existsFileOrFolder(path)) Files.createFile(getNIOPath(path));
	}
	
	/**
	 * Supprime le fichier de chemin+nom donné.
	 * Anticipe l'absence du fichier et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException
	 * @param path La String du chemin
	 * @throws IOException
	 */
	public static void removeFile(String path) throws IOException {
		if (existsFileOrFolder(path)) Files.delete(getNIOPath(path));
	}

	/**
	 * Lit le contenu d'un fichier textuel.
	 * L'encodage par défaut est choisi, à savoir UTF-8.
	 * Anticipe l'absence du fichier et retourne <code>null</code> le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @return Le contenu du fichier sous forme de String, null si le fichier n'existe pas.
	 * @throws IOException
	 */
	public static String readFromFile(String path) throws IOException {
		return existsFileOrFolder(path) ? Files.readString(getNIOPath(path), StandardCharsets.UTF_8) : null;
	}
	
	/**
	 * Lit le contenu d'un fichier textuel.
	 * Un encodage est a préciser.
	 * Anticipe l'absence du fichier et retourne <code>null</code> le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @param encodage L'encodage de lecture
	 * @return Le contenu du fichier sous forme de String, null si le fichier n'existe pas.
	 * @throws IOException
	 */
	public static String readFromFile(String path, String encodage) throws IOException, IllegalCharsetNameException, UnsupportedCharsetException, IllegalArgumentException {
		return existsFileOrFolder(path) ? Files.readString(getNIOPath(path), Charset.forName(encodage)) : null;
	}
	
	/**
	 * Écrit dans un fichier textuel.
	 * L'encodage par défaut est choisi, à savoir UTF-8.
	 * Anticipe l'absence du fichier et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @param contenu Le contenu du fichier sous forme de String
	 * @throws IOException
	 */
	public static void writeToFile(String path, String contenu) throws IOException {
		if (existsFileOrFolder(path)) {
			Files.writeString(getNIOPath(path), 
                contenu, 
                 StandardCharsets.UTF_8,
                 StandardOpenOption.CREATE,
                 StandardOpenOption.TRUNCATE_EXISTING);
		}
	}
	
	/**
	 * Écrit dans un fichier textuel.
	 * Un encodage est à préciser.
	 * Anticipe l'absence du fichier et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @param contenu Le contenu du fichier sous forme de String
	 * @param encodage L'encodage d'écriture
	 * @throws IOException
	 */
	public static void writeToFile(String path, String contenu, String encodage) throws IOException {
		if (existsFileOrFolder(path)) {
			Files.writeString(getNIOPath(path), 
                contenu, 
                 Charset.forName(encodage),
                 StandardOpenOption.CREATE,
                 StandardOpenOption.TRUNCATE_EXISTING);
		}
	}
	
	/**
	 * Déplace le fichier de chemin donné vers un autre chemin.
	 * Un nom de fichier différent en cible donnera un renommage.
	 * Méthode utilisable pour un simple renommage dans le cas où
	 * le répertoire est le même et que seul le nom du fichier change.
	 * Si un fichier cible de tel chemin existe déjà, il sera remplacé.
	 * @param path La String du chemin d'origine
	 * @param target La String du chemin cible
	 * @throws IOException
	 */
	public static void moveFile(String path, String target) throws IOException {
		Files.move(getNIOPath(path), getNIOPath(target), StandardCopyOption.REPLACE_EXISTING);
	}
	
	/**
	 * Copie le fichier de chemin donné vers un autre chemin.
	 * Un nom de fichier différent en cible est tout à fait acceptable.
	 * Anticipe l'absence de fichier source et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @param target La String de la cible
	 * @throws IOException
	 */
	public static void copyFile(String path, String target) throws IOException {
		if (existsFileOrFolder(path))
			Files.copy(getNIOPath(path), getNIOPath(target), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
	}
	
	
	
	/**
	 * Crée le dossier de chemin donné.
	 * Anticipe l'existence prématurée du dossier et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin
	 * @throws IOException 
	 */
	public static void createFolder(String path) throws IOException {
		if (!existsFileOrFolder(path)) Files.createDirectory(getNIOPath(path));
	}
	
	/**
	 * Crée le dossier de chemin donné.
	 * Si le chemin comporte des dossiers parents eux-mêmes non existants,
	 * ils seront automatiquement créés.
	 * La méthode Java NIO Files.createDirectories ignore d'elle-même l'opération
	 * si le dossier existe déjà.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin.
	 * @throws IOException 
	 */
	public static void createFolderWithParents(String path) throws IOException {
		Files.createDirectories(getNIOPath(path));
	}
	
	/**
	 * Supprime le dossier de chemin donné.
	 * La suppression se fait récursivement.
	 * Utilise Java NIO, mais a recours à Java IO 
	 * pour une suppression synchrone et bloquante des fichiers.
	 * Anticipe l'absence du dossier et ne fait rien le cas échéant.
	 * Toute autre anomalie lancera toujours une IOException.
	 * @param path La String du chemin.
	 * @throws IOException
	 */
	public static void removeFolder(String path) throws IOException {
		if (existsFileOrFolder(path)) {
			try (Stream<Path> stream = Files.walk(getNIOPath(path))) {
				stream.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(File::delete);
			}
		}
	}
	
	/**
	 * Permet de vérifier que le chemin donné est un dossier
	 * @param path est le chemin d'accès du fichier/dossier
	 * @return un boolean indiquant si c'est un dossier ou non.
	 */
	public static boolean estUnDossier(String path) {
		return Files.isDirectory(getNIOPath(path));
	}
	
	
	/**
	 * Déplace un dossier et son contenu vers un autre chemin.
	 * Peut renommer le dossier au passage.
	 * @param path La String du chemin
	 * @param target La String de la cible
	 * @throws IOException 
	 */
	public static void moveFolder(String path, String target) throws IOException {
		createDirectories(target);
		try (Stream<Path> stream = Files.walk(getNIOPath(path))){
			stream.forEach((element)->{
				if(element.toFile().isDirectory()) {
					try {
						moveFolder(element.toString(), target);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if(element.toFile().isFile()) {
					try {
						moveFile(element.toString(), target);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		//TODO trouver le meilleur moyen de déplacer/renommer un dossier non-vide
	}
	
	public static void deplacerDossier(String path, String target) throws IOException {
        File nio1 = getNIOPath(path).toFile();
        File nio2 = (Paths.get(target, nio1.getName())).toFile();
        createDirectories(nio2.toString());
        for(File f : nio1.listFiles()) {
            if(f.isDirectory()) {
                deplacerDossier(f.getPath(), String.join(File.separator, nio2.toString(), f.getName()));
            } else {
                moveFile(f.getPath(), String.join(File.separator, nio2.toString(), f.getName()));
            }
        }
        nio1.delete();
    }
	
	public static Document creerXmlFichier() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
        return doc;
	}
	
	
}