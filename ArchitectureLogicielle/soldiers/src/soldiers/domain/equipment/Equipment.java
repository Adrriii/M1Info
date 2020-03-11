package soldiers.domain.equipment;

import soldiers.domain.Entity;

public interface Equipment extends Cloneable {
	String getName();

	String getType();

	Entity createExtension(Entity s);

	Equipment clone();

	boolean isEquipped();

	void setEquipped(boolean value);
}
