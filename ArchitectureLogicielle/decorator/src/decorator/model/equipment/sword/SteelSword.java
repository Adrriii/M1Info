package decorator.model.equipment.sword;

import decorator.model.*;

public class SteelSword extends Sword {

    public SteelSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 10;
    }

}