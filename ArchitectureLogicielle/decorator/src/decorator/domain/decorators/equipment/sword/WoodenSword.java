package decorator.domain.decorators.equipment.sword;

import decorator.domain.decorators.*;
import decorator.domain.decorators.equipment.*;

public class WoodenSword extends Sword {

    public WoodenSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 2;
    }

}