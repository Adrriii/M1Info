package soldiers.domain.equipment;

import soldiers.domain.*;

public class ExtensionDurability extends EntityExtension {
	private EntityExtension ext;
	private int durability;
	private int durabilityMax;

	public ExtensionDurability(Equipment owner, Entity s, EntityExtension ext,
	int durabilityMax) {
		super(owner, s);
		this.ext = ext;
		this.durability = durabilityMax;
		this.durabilityMax = durabilityMax;
	}

	@Override
	public int parry(int force) {
		if (this.durability <= 0) return super.parry(force);
		
		this.durability -= 1;
		return ext.parry(force);
	}

	@Override
	public int strike() {
		if (this.durability <= 0) return super.strike();
		
		this.durability -= 1;
		return ext.strike();
	}

	public void repair(int amount) {
		this.durability += Math.min(durabilityMax, durability + amount);
	}

	public void repairFull() {
		this.durability = this.durabilityMax;
	}
}
