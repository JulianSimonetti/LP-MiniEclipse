package org.formation.eeos.minieclipse.stockage.access;

import java.util.List;
/**
 * Interface des Dao implementant le CRUD
 * @author Farid
 *
 * @param <T>
 */

public interface DaoAccess<T> {
    public List<T> lireTous();
    public void ecrireTous(List<T> t);
    public T lire(int cle);
    public void modifier(int cle, T data);
    public void supprimer(int cle);
    public void ajouter(T data);
}
