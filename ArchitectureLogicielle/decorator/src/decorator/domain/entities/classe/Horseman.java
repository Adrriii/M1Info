package decorator.domain.entities.classe;

import decorator.domain.entities.*;
import decorator.domain.entities.classe.mount.Horse;

public class Horseman extends UnitSimple {

    protected Mount mount;

    public Horseman(String nickname) {
        super(nickname);
        mount = new Horse("Horse");
    }

    @Override
    public void parry(int force) {
        if(mount.isAlive())
            super.parry(mount.parryMounted(force));
        else 
            super.parry(force);
    }
}