package soldiers.domain.unit.classe;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;
import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;

public class Strongman extends UnitSimple {

    public Strongman(String nickname) {
        super(new EntityUnit(nickname, 130, 1));
    }

    @Override
	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        if(w.getType().equals("Wand")) throw new ImpossibleExtensionException();

        if(nbEquipments() >= 2) throw new ImpossibleExtensionException();
        
        super.addEquipment(w);
    }
}