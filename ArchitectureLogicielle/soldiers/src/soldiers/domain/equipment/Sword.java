/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldiers.domain.equipment;

import soldiers.domain.*;

public abstract class Sword extends EquipmentAbstract {

	@Override
	public Sword clone() {
		return (Sword) super.clone();
	}

	abstract public String getName();

	abstract public Entity createExtension(Entity s);

}
