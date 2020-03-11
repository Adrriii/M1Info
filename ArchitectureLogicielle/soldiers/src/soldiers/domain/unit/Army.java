
package soldiers.domain.unit;

import java.util.ArrayList;

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

}
