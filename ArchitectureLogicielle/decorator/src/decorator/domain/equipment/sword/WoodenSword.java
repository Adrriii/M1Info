package decorator.domain.equipment.sword;

import decorator.domain.*;
import decorator.domain.equipment.*;

public class WoodenSword extends Sword {

    public WoodenSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 2;
    }

}