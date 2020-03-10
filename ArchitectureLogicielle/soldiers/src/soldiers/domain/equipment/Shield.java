/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldiers.domain.equipment;

import soldiers.domain.*;

public abstract class Shield extends EquipmentAbstract {

	@Override
	public Shield clone() {
		return (Shield) super.clone();
	}

	abstract public String getName();

	abstract public Entity createExtension(Entity s);

}
