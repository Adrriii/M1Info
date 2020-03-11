package soldiers.domain.unit;

import java.util.ArrayList;
import java.util.Iterator;

import soldiers.domain.*;
import soldiers.domain.equipment.*;

public class UnitSimple {

	protected ArrayList<UnitSimple> units;

    protected Entity entity;

	public UnitSimple(Entity entity) {
		this.entity = entity;
		units = new ArrayList<>();
	}

    public void add(UnitSimple unit) {
        units.add(unit);
    }

    public void remove(UnitSimple unit) {
        units.remove(unit);
    }

    public UnitSimple GetChild(int n) {
        return units.get(n);
    }

    public ArrayList<UnitSimple> GetChildren() {
        return units;
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
        int total = entity.strike();

        Iterator<UnitSimple> i = units.iterator();

        while(i.hasNext()) {
            total += i.next().strike();
		}
		
        return total;
    }

    public int parry(int force) {
        int remaining = force;
        int lastremaining = remaining;

        while(units.size() > 0 && remaining > 0) {
            int share = (int) Math.ceil(remaining / units.size());

            Iterator<UnitSimple> i = units.iterator();

            while(i.hasNext() && remaining > 0) {
                if(share > remaining) share = remaining;

                UnitSimple defender = i.next();

                if(defender.alive()) {
                    defender.parry(share);
                    remaining -= share;
                }
            }

            if(lastremaining == remaining) break; // Could not parry, everyone probably dead.
        }
		
        return entity.parry(remaining);
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

	public int nbEquipments() {
		int result = 0;
		for (Iterator<Equipment> it = getWeapons(); it.hasNext(); it.next())
			++result;
		return result;
	}

	public int nbType(String type) {
		int result = 0;
		for (Iterator<Equipment> it = getWeapons(); it.hasNext(); )
			if(it.next().getType().equals(type)) ++result;
		return result;
	}
    
}