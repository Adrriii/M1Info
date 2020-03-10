package soldiers.domain.equipment.sword;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class WoodenSword extends Sword {

    @Override
    public String getName() {
        return "Wooden Sword";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionConst(this, s, 2, 0);
    }

}