package soldiers.domain.unit.classe;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;
import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;

public class Mage extends UnitSimple {

    public Mage(String nickname) {
        super(new EntityUnit(nickname, 70, 1));
    }

    @Override
	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        if(w.getType().equals("Sword")) throw new ImpossibleExtensionException();
        if(w.getType().equals("Shield")) throw new ImpossibleExtensionException();
        if(w.getType().equals("Wand") && nbType("Wand") >= 1) throw new ImpossibleExtensionException();
        
        super.addEquipment(w);
    }
}