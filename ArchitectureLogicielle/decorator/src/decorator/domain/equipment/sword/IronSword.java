package decorator.domain.equipment.sword;

import decorator.domain.*;
import decorator.domain.equipment.*;

public class IronSword extends Sword {

    public IronSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 6;
    }

}