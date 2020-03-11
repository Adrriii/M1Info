package soldiers.domain.unit;

import soldiers.domain.*;

public class AbstractEntity implements Entity {
	private String name;
	private boolean destroyed;

	public AbstractEntity(String name) {
		this.name = name;
		destroyed = false;
	}

	@Override
	public boolean alive() {
		return !destroyed;
	}

	@Override
	public int parry(int force) {
		if(force > 0) destroyed = true;
		return force;
	}

	@Override
	public int strike() {
		return 0;
	}

	@Override
	public void heal() {
		
	}

	@Override
	public int initialHealth() {
		return 0;
	}

	@Override
	public void setHP(int hp) {
		
	}

	@Override
	public int getHP() {
		return 0;
	}

	@Override
	public String nickname() {
		return name;
	}
}
