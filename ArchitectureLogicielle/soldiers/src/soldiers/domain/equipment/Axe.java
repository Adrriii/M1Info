/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldiers.domain.equipment;

import soldiers.domain.*;

public abstract class Axe extends EquipmentAbstract {

	@Override
	public Axe clone() {
		return (Axe) super.clone();
	}

	@Override
	public String getType() {
		return "Axe";
	}

	abstract public String getName();

	abstract public Entity createExtension(Entity s);

}
