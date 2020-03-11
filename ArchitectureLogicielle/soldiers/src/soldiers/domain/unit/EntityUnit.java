package soldiers.domain.unit;

import soldiers.domain.*;

public class EntityUnit implements Entity {
	private int healthPoints;
	private int force;
	private int naturalArmor;
	private int initialHealth;
	private String name;

	public EntityUnit(String name, int healthPoints, int force) {
		this(name, healthPoints, force, 0);
	}

	public EntityUnit(String name, int healthPoints, int force, int naturalArmor) {
		this.name = name;
		this.healthPoints = healthPoints;
		this.force = force;
		this.naturalArmor = naturalArmor;
		this.initialHealth = healthPoints;
	}

	@Override
	public boolean alive() {
		return getHP() > 0;
	}

	@Override
	public int parry(int force) {
		int dealt = healthPoints - Math.max(0, healthPoints - Math.max(0,force - naturalArmor));
		healthPoints -= dealt;
		return dealt;
	}

	@Override
	public int strike() {
		return alive() ? force : 0;
	}

	@Override
	public void heal() {
		healthPoints = initialHealth;
	}

	@Override
	public int initialHealth() {
		return initialHealth;
	}

	@Override
	public void setHP(int hp) {
		healthPoints = hp;
	}

	@Override
	public int getHP() {
		return healthPoints;
	}

	@Override
	public String nickname() {
		return name;
	}
}
