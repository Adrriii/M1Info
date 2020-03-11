package soldiers.domain.equipment.sword;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class SteelSword extends Sword {

    @Override
    public String getName() {
        return "Steel Sword";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionConst(this, s, 7, 0);
    }

}