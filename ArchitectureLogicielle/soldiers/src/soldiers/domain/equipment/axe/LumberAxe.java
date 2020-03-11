package soldiers.domain.equipment.axe;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class LumberAxe extends Axe {

    @Override
    public String getName() {
        return "Lumber Axe";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionConst(this, s, 6, 1);
    }

}