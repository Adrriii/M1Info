package decorator.model.equipment.shield;

import decorator.model.equipment.*;
import decorator.model.*;

public class Shield extends Item {
    
    public Shield(PlayerInterface player) {
        super(player);

        modifiesParry = true;

        ArmorModifier = 0;

        MaxDurability = 0;
        
        Durability = 0;
    }
}