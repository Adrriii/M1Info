package soldiers.domain.equipment;

import soldiers.domain.*;

public abstract class Shield extends EquipmentAbstract {

	@Override
	public Shield clone() {
		return (Shield) super.clone();
	}

	@Override
	public String getType() {
		return "Shield";
	}

	abstract public String getName();

	abstract public Entity createExtension(Entity s);

}
