package decorator.model.classe;

import decorator.model.*;

public class Mount extends Player {

    protected int Tanking;

    public Mount() {

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
        
        HP -= tanked;
        force -= tanked;

        return force;
    }
    
}