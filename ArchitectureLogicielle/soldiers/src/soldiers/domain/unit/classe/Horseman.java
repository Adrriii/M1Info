package soldiers.domain.unit.classe;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;
import soldiers.domain.unit.*;
import soldiers.domain.unit.classe.mount.Horse;

public class Horseman extends UnitSimple {

    protected Mount mount;

    public Horseman(String nickname) {
        this(nickname, "Horse");
    }

    public Horseman(String nickname, String horseName) {
        super(new EntityUnit(nickname, 150, 1));

        mount = new Horse(horseName);
    }

    @Override
    public int parry(int force) {
        if(mount.alive())
            return super.parry(mount.parryMounted(force));
        else 
        return super.parry(force);
    }

    @Override
	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        if(w.getType().equals("Wand")) throw new ImpossibleExtensionException();
        if(w.getType().equals("Axe")) throw new ImpossibleExtensionException();
        if(nbEquipments() >= 2) throw new ImpossibleExtensionException();
        if(w.getType().equals("Sword") && nbType("Sword") >= 1) throw new ImpossibleExtensionException();
        if(w.getType().equals("Shield") && nbType("Shield") >= 1) throw new ImpossibleExtensionException();
        super.addEquipment(w);
    }
}