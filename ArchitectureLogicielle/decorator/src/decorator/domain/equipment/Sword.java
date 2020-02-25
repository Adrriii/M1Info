package decorator.domain.equipment;

import decorator.domain.*;

public class Sword extends Item {
    
    public Sword(PlayerInterface player) {
        super(player);

        modifiesAttack = true;
        
        ForceModifier = 0;
    }
}