package org.formation.eeos.minieclipse.stockage.access;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import org.formation.eeos.minieclipse.model.technique.exceptions.NoPropsException;

/**
 * classe mere des DaoFactory
 * @author farid
 *
 */

public abstract class DaoFactory {
	protected static DaoFactory instance = null;
	public static String PACKAGE = "";
	protected Properties props = null;

	protected DaoFactory(String propsFile) {
		try (InputStream fic = this.getClass().getClassLoader().getResourceAsStream(propsFile)){
			props = new Properties();
			props.load(fic);
			PACKAGE = props.getProperty("destination");
		} catch (IOException | IllegalArgumentException | NullPointerException e) {
			System.out.print("Il n'existe pas de properties pour "+ this.getClass().getName());	
			System.out.print(e.getMessage());
		}
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public DaoAccess<?> getDao() throws Exception {
		return null;
    }
	
	public Properties getProps() {
		return props;
	}

}
