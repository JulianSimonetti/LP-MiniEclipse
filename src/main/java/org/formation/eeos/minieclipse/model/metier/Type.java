/**
 * 
 */
package org.formation.eeos.minieclipse.model.metier;

/**
 * @author soufiane mejahed
 *
 */

public abstract class Type {
	protected Class<?> Type;	
	
	public Type() {
		
	}

	public Class<?> getType() {
		return Type;
	}

	private void setType(Class<?> type) {
		Type = type;
	}
}
