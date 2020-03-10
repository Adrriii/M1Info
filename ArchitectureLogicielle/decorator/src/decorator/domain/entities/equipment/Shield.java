package decorator.domain.entities.equipment;

import decorator.domain.decorators.PlayerInterface;
import decorator.domain.decorators.equipment.shield.*;
import decorator.domain.entities.ImpossibleExtensionException;
import decorator.domain.decorators.equipment.Item;

public class Shield extends Equipment {

    @Override
    public Item createItem(PlayerInterface player) throws ImpossibleExtensionException {
        item = new IronShield(player);
        return item;
    }
}