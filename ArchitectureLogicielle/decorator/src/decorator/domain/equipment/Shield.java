package decorator.domain.equipment;

import decorator.domain.*;

public class Shield extends Item {
    
    public Shield(PlayerInterface player) {
        super(player);

        modifiesParry = true;

        ArmorModifier = 0;

        MaxDurability = 0;
        
        Durability = 0;
    }
}