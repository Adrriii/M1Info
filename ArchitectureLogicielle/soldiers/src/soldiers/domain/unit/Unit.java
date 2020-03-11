package soldiers.domain.unit;

import java.util.ArrayList;
import java.util.Iterator;

import soldiers.domain.*;
import soldiers.domain.equipment.*;

public abstract class Unit {

	protected ArrayList<Unit> units;

    protected Entity entity;

	protected int stacked;

	public Unit(Entity entity) {
		this.entity = entity;
		stacked = 0;
		units = new ArrayList<>();
	}

	public void add(Unit unit) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
    }

    public void remove(Unit unit) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
    }

    public Unit GetChild(int n) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
    }

    public ArrayList<Unit> GetChildren() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
    }

    public String nickname() {
        return entity.nickname();
    }

    public boolean alive() {
        return entity.alive();
    }

    public int strike() {
        int total = entity.strike();

        Iterator<Unit> i = units.iterator();

        while(i.hasNext()) {
			Unit unit = i.next();

			if (unit.alive()) {
				total += unit.strike();
			}
		}
		
        return total;
	}
	
    public void stack(int force) {
		stacked += force;
	}

	public int pop() {
		int dealt = parry(stacked);
		stacked = 0;
		return dealt;
	}

    public int parry(int force) {
        int remaining = force;
		int lastremaining = remaining;
		int real = 0;

        while(units.size() > 0 && remaining > 0) {
			int share = Math.max(1,(int) Math.ceil(remaining / units.size()));

            Iterator<Unit> i = units.iterator();

            while(i.hasNext() && remaining > 0) {
                if(share > remaining) share = remaining;

                Unit defender = i.next();

				if(defender.alive()) {
					defender.stack(share);
						
					remaining -= share;
				}
            }

            if(lastremaining == remaining) break; // Could not parry, everyone probably dead.
		}
		
		Iterator<Unit> i = units.iterator();

		while(i.hasNext()) {
			real += i.next().pop();
		}
		
        return real + entity.parry(remaining);
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