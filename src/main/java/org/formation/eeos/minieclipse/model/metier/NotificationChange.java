package org.formation.eeos.minieclipse.model.metier;

import java.beans.PropertyChangeListener;

public interface NotificationChange {
public void addPropertyChangeListener(PropertyChangeListener l);
public void removePropertyChangeListener(PropertyChangeListener l);
}
