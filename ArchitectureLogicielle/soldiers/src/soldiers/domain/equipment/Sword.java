package soldiers.domain.equipment;

import soldiers.domain.*;

public abstract class Sword extends EquipmentAbstract {

	@Override
	public Sword clone() {
		return (Sword) super.clone();
	}

	@Override
	public String getType() {
		return "Sword";
	}

	abstract public String getName();

	abstract public Entity createExtension(Entity s);

}
