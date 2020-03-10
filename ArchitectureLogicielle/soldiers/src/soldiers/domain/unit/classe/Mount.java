package soldiers.domain.unit.classe;

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
    
}