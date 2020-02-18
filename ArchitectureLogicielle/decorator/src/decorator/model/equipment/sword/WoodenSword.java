package decorator.model.equipment.sword;

import decorator.model.*;

public class WoodenSword extends Sword {

    public WoodenSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 2;
    }

}