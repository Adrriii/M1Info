package decorator.domain.decorators.equipment.sword;

import decorator.domain.decorators.*;
import decorator.domain.decorators.equipment.*;

public class SteelSword extends Sword {

    public SteelSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 10;
    }

}