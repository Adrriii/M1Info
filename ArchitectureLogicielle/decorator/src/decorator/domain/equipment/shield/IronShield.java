package decorator.domain.equipment.shield;

import decorator.domain.equipment.*;
import decorator.domain.*;

public class IronShield extends Shield {
    
    public IronShield(PlayerInterface player) {
        super(player);

        ArmorModifier = 5;

        MaxDurability = 50;
        
        Durability = 50;
    }
}