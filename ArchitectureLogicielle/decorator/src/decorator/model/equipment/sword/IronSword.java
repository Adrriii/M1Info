package decorator.model.equipment.sword;

import decorator.model.*;

public class IronSword extends Sword {

    public IronSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 6;
    }

}