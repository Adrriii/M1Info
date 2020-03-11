package soldiers.domain.equipment.wand;

import soldiers.domain.Entity;
import soldiers.domain.equipment.*;

public class ApprenticeWand extends Wand {

    @Override
    public String getName() {
        return "ApprenticeWand";
    }

    @Override
    public Entity createExtension(Entity s) {
        return new ExtensionConst(this, s, 2, 2);
    }

}