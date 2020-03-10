package soldiers.domain.equipment;

public abstract class EquipmentAbstract implements Equipment {

	public boolean equipped = false;

	@Override
	public EquipmentAbstract clone() {
		try {
			return (EquipmentAbstract) super.clone();
		} catch (Exception e) {
		}
		return this;
	}

	@Override
	public boolean isEquipped() {
		return equipped;
	}

	@Override
	public void setEquipped(boolean value) {
		equipped = value;
	}
}
