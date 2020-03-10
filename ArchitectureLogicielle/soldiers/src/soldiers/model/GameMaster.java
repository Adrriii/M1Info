package soldiers.model;

import soldiers.domain.unit.*;

public class GameMaster {

    public void fight(UnitSimple s1, UnitSimple s2) {
        UnitSimple attack = s1;
        UnitSimple defend = s2;

        while(s1.alive() && s2.alive()) {
            int force = attack.strike();
            int dealt = defend.parry(force);

            System.out.println(attack.nickname() + " strikes " + defend.nickname() + " with an attack of " + force);
            System.out.println(defend.nickname() + " is dealt " + dealt + " damage !");
            

            UnitSimple tmpSwap = attack;
            attack = defend;
            defend = tmpSwap;
        }
        
        if (s1.alive())  {
            System.out.println(s1.nickname() + " Won");
        }
        else {
            System.out.println(s2.nickname() + " Won");
        }
    }
}