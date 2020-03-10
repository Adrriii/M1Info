package soldiers.domain.unit.classe;

import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;
import soldiers.domain.unit.classe.mount.Horse;

public class Horseman extends UnitSimple {

    protected Mount mount;

    public Horseman(String nickname) {
        this(nickname, "Horse");
    }

    public Horseman(String nickname, String horseName) {
        super(new EntityUnit(nickname, 100, 1));

        mount = new Horse(horseName);
    }

    @Override
    public int parry(int force) {
        if(mount.alive())
            return super.parry(mount.parryMounted(force));
        else 
        return super.parry(force);
    }
}