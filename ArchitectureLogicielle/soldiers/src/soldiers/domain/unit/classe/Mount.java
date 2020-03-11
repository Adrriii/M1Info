package soldiers.domain.unit.classe;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;
import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;

public class Mount extends UnitSimple {

    protected int Tanking;

    public Mount(String nickname) {
        super(new EntityUnit(nickname, 100, 10));

        Tanking = 0;
    }

    @Override
    public int strike() {
        return super.strike();
    }

    public int parryMounted(int force) {

        int tanked = Tanking;

        if(tanked > force) {
            tanked = force;
        }
        
        parry(tanked);
        force -= tanked;

        return force;
    }

    @Override
	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        if(w.getType().equals("Sword")) throw new ImpossibleExtensionException();
        if(w.getType().equals("Shield")) throw new ImpossibleExtensionException();
        if(nbEquipments() >= 2) throw new ImpossibleExtensionException();
        super.addEquipment(w);
    }
    
}