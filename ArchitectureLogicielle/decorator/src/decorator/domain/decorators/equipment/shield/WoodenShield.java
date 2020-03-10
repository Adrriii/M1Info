package decorator.domain.decorators.equipment.shield;

import decorator.domain.decorators.equipment.*;
import decorator.domain.decorators.*;

public class WoodenShield extends Shield {
    
    public WoodenShield(PlayerInterface player) {
        super(player);

        ArmorModifier = 1;

        MaxDurability = 15;
        
        Durability = 15;
    }
}