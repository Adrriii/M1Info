package soldiers.domain.unit;

import soldiers.domain.*;

public class UnitSimple extends Unit {

    public UnitSimple(Entity entity) {
		super(entity);
	}

    public void setHP(int hp) {
        entity.setHP(hp);
    }

    public int getHP() {
        return entity.getHP();
    }
    
}