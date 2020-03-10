package decorator.domain.decorators.equipment;

import decorator.domain.decorators.*;

public class Sword extends Item {
    
    public Sword(PlayerInterface player) {
        super(player);

        modifiesAttack = true;
        
        ForceModifier = 0;
    }
}