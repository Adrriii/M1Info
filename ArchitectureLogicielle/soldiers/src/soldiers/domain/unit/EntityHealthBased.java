package soldiers.domain.unit;

public class EntityHealthBased extends EntityUnit {

	public EntityHealthBased(String name, int healthPoints, int force) {
		super(name, healthPoints, force);
	}

	@Override
	public int strike() {
		return super.strike() * getHP() / initialHealth();
	}
}
