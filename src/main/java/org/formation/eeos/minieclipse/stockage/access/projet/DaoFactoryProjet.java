package org.formation.eeos.minieclipse.stockage.access.projet;

import org.formation.eeos.minieclipse.stockage.access.DaoAccess;
import org.formation.eeos.minieclipse.stockage.access.DaoFactory;
import org.formation.eeos.minieclipse.stockage.service.ProjetPOJO;
import org.formation.eeos.minieclipse.model.metier.Projet;
import org.formation.eeos.minieclipse.model.technique.exceptions.*;

/**
 * Factory de data access object permettant de choisir quel type de Dao va etre appeler depuis un fichier de propriete
 * @author farid
 *
 */

public class DaoFactoryProjet extends DaoFactory {
	private static DaoFactoryProjet instance = null;
	
	private DaoFactoryProjet() {
		super("projects.properties");
	}
	
	public static DaoFactoryProjet getInstance() {
		if(DaoFactoryProjet.instance == null) {
			DaoFactoryProjet.instance = new DaoFactoryProjet();
		}
		return DaoFactoryProjet.instance;
	}
	
	@Override
	public DaoAccess<ProjetPOJO> getDao() throws Exception {
		if(props==null) {throw new NoPropsException("Il n'y a pas de DAO valable pour ici");}
        DaoAccess<ProjetPOJO> ret = null;
        String choix = props.getProperty("dao");
        Class<?> laClasse = null;
        laClasse = Class.forName(String.join(".", PACKAGE, choix)).asSubclass(DaoAccess.class);
        ret = (DaoAccess<ProjetPOJO>) laClasse.getConstructor().newInstance();
        return ret;
    }
	
}
