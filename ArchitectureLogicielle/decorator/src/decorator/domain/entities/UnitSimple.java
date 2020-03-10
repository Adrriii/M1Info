package decorator.domain.entities;

import decorator.domain.decorators.*;
import decorator.domain.decorators.equipment.Item;
import decorator.domain.entities.equipment.Equipment;

public class UnitSimple implements PlayerInterface {

    protected PlayerInterface player;

    public UnitSimple(String nickname) {
        player = new Player(nickname);
    }

    @Override
    public void setHP(int hp) {
        player.setHP(hp);
    }

    @Override
    public int getHP() {
        return player.getHP();
    }

    @Override
    public String getNickname() {
        return player.getNickname();
    }

    @Override
    public boolean isAlive() {
        return player.isAlive();
    }

    @Override
    public int strike() {
        return player.strike();
    }

    @Override
    public void parry(int force) {
        player.parry(force);
    }

    public void addEquipment(Equipment item) throws ImpossibleExtensionException {
        if (item.equipped) throw new ImpossibleExtensionException();
        item.equipped = true;
        player = item.createItem(player);
    }

    public void removeEquipment(Equipment eq) throws ImpossibleExtensionException {
        this.removeEquipment(eq, this.player);
    }

    public void removeEquipment(Equipment eq, PlayerInterface subject) throws ImpossibleExtensionException {
        if(subject.getClass().getSimpleName().equals("Player")) {
            return;
        }
        Item item = eq.item;
        Decorator dec = (Decorator) subject;

        // First iteration
        if(item == this.player && item.equals(eq.item)) {
            player = dec.parent();
            return;
        }

        if(dec.parent().getClass().getSimpleName().equals("Player")) {
            return;
        }

        // All iterations
        if(((Equipment) dec.parent()).item.equals(item)) { //equals
            dec.newparent(((Decorator) dec.parent()).parent());
            return;
        }
        
        removeEquipment(eq, dec.parent());
    }
    
}