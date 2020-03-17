
package soldiers.domain.unit;

import java.util.ArrayList;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.Equipment;

public class Army extends Unit {

	public Army(String name) {
		super(new AbstractEntity(name));
	}

	@Override
	public void add(Unit unit) {
        units.add(unit);
    }

	@Override
    public void remove(Unit unit) {
        units.remove(unit);
    }

	@Override
    public Unit GetChild(int n) {
        return units.get(n);
    }

	@Override
    public ArrayList<Unit> GetChildren() {
        return units;
    }

    @Override
    public void addEquipment(Equipment w) throws ImpossibleExtensionException {
        for(Unit unit : GetChildren()) {
            try {
                unit.addEquipment(w);
                return;
            } catch(ImpossibleExtensionException e) {};
        }
        throw new ImpossibleExtensionException();
    }

    @Override
    public boolean removeEquipment(Equipment w) {
        for(Unit unit : GetChildren()) {
            if(unit.removeEquipment(w)) {
                return true;
            }
        }
        return false;
    }

}
