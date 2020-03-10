package decorator.domain.decorators.equipment.shield;

import decorator.domain.decorators.equipment.*;
import decorator.domain.decorators.*;

public class IronShield extends Shield {
    
    public IronShield(PlayerInterface player) {
        super(player);

        ArmorModifier = 5;

        MaxDurability = 50;
        
        Durability = 50;
    }
}