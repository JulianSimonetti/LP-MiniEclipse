package org.formation.eeos.minieclipse.model.metier;

public class TypeMethode extends Type {
	
	private Class<?> Type;
	
	public TypeMethode(Class<?> Type) {
		super();
	}

	public Class<?> getType() {
		return Type;
	}

	private void setType(Class<?> type) {
		Type = type;
	}

}
