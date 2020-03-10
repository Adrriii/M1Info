package soldiers.domain.equipment.shield;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class WoodenShield extends Shield {

    @Override
    public String getName() {
        return "Wooden Shield";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionConst(this, s, 0, 1);
    }
}