package decorator.domain.entities.equipment;

import decorator.domain.decorators.PlayerInterface;
import decorator.domain.decorators.equipment.Item;
import decorator.domain.entities.ImpossibleExtensionException;

public abstract class Equipment {

    public boolean equipped = false;

    public Item item;

    abstract public Item createItem(PlayerInterface player) throws ImpossibleExtensionException;
}