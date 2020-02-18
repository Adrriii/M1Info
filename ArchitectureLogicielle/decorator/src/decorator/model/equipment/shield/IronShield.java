package decorator.model.equipment.shield;

import decorator.model.equipment.*;
import decorator.model.*;

public class IronShield extends Item {
    
    public IronShield(PlayerInterface player) {
        super(player);

        ArmorModifier = 5;

        MaxDurability = 50;
        
        Durability = 50;
    }
}