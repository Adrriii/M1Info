package soldiers.domain.equipment;

import soldiers.domain.*;

public class ExtensionConst extends EntityExtension {
	private int strikeCst;
	private int parryCst;

	public ExtensionConst(Equipment owner, Entity s, int strikeCst,
	int parryCst) {
		super(owner, s);
		this.strikeCst = strikeCst;
		this.parryCst = parryCst;
	}

	@Override
	public int parry(int force) {
		return super.parry(Math.max(0, force - parryCst));
	}

	@Override
	public int strike() {
		return strikeCst + super.strike();
	}
}
