package soldiers.domain.unit.classe;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;
import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;

public class Creature extends UnitSimple {

    public Creature(String nickname) {
        super(new EntityUnit(nickname, 40, 5, 2));
    }

    @Override
	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        throw new ImpossibleExtensionException();
    }
}