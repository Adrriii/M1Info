package decorator.model.classe;

import decorator.model.*;
import decorator.model.classe.mount.Horse;

public class Horseman extends Player {

    protected Mount mount;

    public Horseman() {
        mount = new Horse();
    }

    @Override
    public void parry(int force) {
        if(mount.isAlive())
            super.parry(mount.parryMounted(force));
        else 
            super.parry(force);
    }
}