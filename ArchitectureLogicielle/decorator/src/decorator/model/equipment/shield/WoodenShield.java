package decorator.model.equipment.shield;

import decorator.model.equipment.*;
import decorator.model.*;

public class WoodenShield extends Item {
    
    public WoodenShield(PlayerInterface player) {
        super(player);

        ArmorModifier = 1;

        MaxDurability = 15;
        
        Durability = 15;
    }
}