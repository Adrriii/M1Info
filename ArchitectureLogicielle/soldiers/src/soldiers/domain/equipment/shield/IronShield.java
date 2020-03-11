package soldiers.domain.equipment.shield;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class IronShield extends Shield {

    @Override
    public String getName() {
        return "Iron Shield";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionDurability(this, s, new ExtensionConst(this, s, 0, 5), 150);
    }
}