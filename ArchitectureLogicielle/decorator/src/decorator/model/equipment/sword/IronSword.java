package decorator.model.equipment.sword;

import decorator.model.*;
import decorator.model.equipment.*;

public class IronSword extends Sword {

    public IronSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 6;
    }

}