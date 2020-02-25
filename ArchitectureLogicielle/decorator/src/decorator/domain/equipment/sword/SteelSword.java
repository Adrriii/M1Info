package decorator.domain.equipment.sword;

import decorator.domain.*;
import decorator.domain.equipment.*;

public class SteelSword extends Sword {

    public SteelSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 10;
    }

}