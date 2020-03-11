package soldiers.domain.unit.classe;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;
import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;

public class Infantryman extends UnitSimple {

    public Infantryman(String nickname) {
        super(new EntityUnit(nickname, 100, 1));
    }

    @Override
	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        if(nbEquipments() >= 2) throw new ImpossibleExtensionException();
        if(nbType("Shield") >= 1) {
            if(w.getType().equals("Shield")) throw new ImpossibleExtensionException();
            if(w.getType().equals("Sword") && nbType("Sword") >= 1) throw new ImpossibleExtensionException();
        } else {
            if(w.getType().equals("Sword") && nbType("Sword") >= 2) throw new ImpossibleExtensionException();
        }
        
        super.addEquipment(w);
    }
}