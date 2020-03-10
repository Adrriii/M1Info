package soldiers.domain;

import soldiers.domain.equipment.Equipment;

public class EntityExtension implements Entity {

    protected Entity entity;
	private Equipment owner;

	public EntityExtension(Equipment owner, Entity s) {
		this.entity = s;
		this.owner = owner;
	}

	public Equipment getOwner() {
		return owner;
	}

    public Entity parent() {
        return entity;
    }

	public void reparent(Entity newParent) {
		entity = newParent;
	}

    @Override
    public void setHP(int hp) {
        entity.setHP(hp);
    }

    @Override
    public int getHP() {
        return entity.getHP();
    }

    @Override
    public boolean alive() {
        return entity.alive();
    }

    @Override
    public int strike() {
        return entity.strike();
    }

    @Override
    public int parry(int force) {
        return entity.parry(force);
    }

    @Override
    public void heal() {
        entity.heal();
    }
    
    @Override
    public int initialHealth() {
        return entity.initialHealth();
    }

    @Override
    public String nickname() {
        return entity.nickname();
    }
}