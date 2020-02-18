package decorator.model.equipment.sword;

import decorator.model.*;
import decorator.model.equipment.*;

public class WoodenSword extends Sword {

    public WoodenSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 2;
    }

}