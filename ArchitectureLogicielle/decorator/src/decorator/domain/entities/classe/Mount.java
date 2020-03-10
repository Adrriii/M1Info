package decorator.domain.entities.classe;

import decorator.domain.entities.*;

public class Mount extends UnitSimple {

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
        
        parry(tanked);
        force -= tanked;

        return force;
    }
    
}