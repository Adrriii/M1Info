package decorator.model.equipment.sword;

import decorator.model.*;
import decorator.model.equipment.*;

public class SteelSword extends Sword {

    public SteelSword(PlayerInterface player) {
        super(player);
        
        ForceModifier = 10;
    }

}