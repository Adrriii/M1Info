/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldiers.domain.equipment;

import soldiers.domain.*;

public abstract class Wand extends EquipmentAbstract {

	@Override
	public Wand clone() {
		return (Wand) super.clone();
	}

	@Override
	public String getType() {
		return "Wand";
	}

	abstract public String getName();

	abstract public Entity createExtension(Entity s);

}
