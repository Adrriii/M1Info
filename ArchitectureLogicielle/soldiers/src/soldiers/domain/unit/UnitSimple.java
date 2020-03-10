package soldiers.domain.unit;

import java.util.Iterator;

import soldiers.domain.*;
import soldiers.domain.equipment.*;

public class UnitSimple {

    protected Entity entity;

	public UnitSimple(Entity entity) {
		this.entity = entity;
	}

    public void setHP(int hp) {
        entity.setHP(hp);
    }

    public int getHP() {
        return entity.getHP();
    }

    public String nickname() {
        return entity.nickname();
    }

    public boolean alive() {
        return entity.alive();
    }

    public int strike() {
        return entity.strike();
    }

    public int parry(int force) {
        return entity.parry(force);
    }

	public void addEquipment(Equipment w) throws ImpossibleExtensionException {
		if(w.isEquipped()) throw new ImpossibleExtensionException();
		entity = w.createExtension(entity);
		w.setEquipped(true);
	}

	public void removeEquipment(Equipment w) {
		Entity current = entity;
		Entity previous = entity;
		while (current instanceof EntityExtension
				&& ((EntityExtension) current).getOwner() != w) {
			previous = current;
			current = ((EntityExtension) current).parent();
		}
		if (((EntityExtension) current).getOwner() == w) {
			((EntityExtension) previous)
					.reparent(((EntityExtension) current).parent());
					((EntityExtension) current).getOwner().setEquipped(false);
		}
	}

	public Iterator<Equipment> getWeapons() {
		return new Iterator<Equipment>() {
			Entity current = entity;

			@Override
			public boolean hasNext() {
				return current instanceof EntityExtension;
			}

			@Override
			public Equipment next() {
				Equipment tmp = ((EntityExtension) current).getOwner();
				current = ((EntityExtension) current).parent();
				return tmp;
			}
		};
	}

	public int nbWeapons() {
		int result = 0;
		for (Iterator<Equipment> it = getWeapons(); it.hasNext(); it.next())
			++result;
		return result;
	}
    
}