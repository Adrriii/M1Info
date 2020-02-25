package decorator.domain.equipment.shield;

import decorator.domain.equipment.*;
import decorator.domain.*;

public class WoodenShield extends Shield {
    
    public WoodenShield(PlayerInterface player) {
        super(player);

        ArmorModifier = 1;

        MaxDurability = 15;
        
        Durability = 15;
    }
}