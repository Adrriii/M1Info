package decorator.domain.classe;

import decorator.domain.*;

public class Mount extends Player {

    protected int Tanking;

    public Mount(String nickname) {
        super(nickname);

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

        if(tanked > HP) {
            tanked = HP;
        }
        
        HP -= tanked;
        force -= tanked;

        return force;
    }
    
}