package decorator.domain.classe;

import decorator.domain.*;
import decorator.domain.classe.mount.Horse;

public class Horseman extends Player {

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