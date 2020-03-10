package decorator.domain.decorators.equipment.sword;

import decorator.domain.decorators.*;
import decorator.domain.decorators.equipment.*;

public class IronSword extends Sword {

    public IronSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 6;
    }

}