package soldiers.domain.unit.classe;

import soldiers.domain.unit.EntityUnit;
import soldiers.domain.unit.UnitSimple;

public class Infantryman extends UnitSimple {

    public Infantryman(String nickname) {
        super(new EntityUnit(nickname, 100, 1));
    }

}