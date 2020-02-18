package decorator.model.equipment;

import decorator.model.*;

public class Sword extends Item {
    
    public Sword(PlayerInterface player) {
        super(player);

        modifiesAttack = true;
        
        ForceModifier = 0;
    }
}