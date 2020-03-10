package soldiers.domain.equipment.sword;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class IronSword extends Sword {

    @Override
    public String getName() {
        return "Iron Sword";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionConst(this, s, 3, 0);
    }

}