package decorator.domain.entities.equipment;

import decorator.domain.decorators.PlayerInterface;
import decorator.domain.decorators.equipment.sword.*;
import decorator.domain.entities.ImpossibleExtensionException;
import decorator.domain.decorators.equipment.Item;

public class Sword extends Equipment {

    @Override
    public Item createItem(PlayerInterface player) throws ImpossibleExtensionException {
        item = new IronSword(player);
        return item;
    }

}