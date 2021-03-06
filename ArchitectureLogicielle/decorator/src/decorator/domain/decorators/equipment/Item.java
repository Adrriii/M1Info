package decorator.domain.decorators.equipment;

import decorator.domain.decorators.*;

public class Item extends Decorator {

    public boolean equipped = false;

    protected int ForceModifier;

    protected int ArmorModifier;

    protected int MaxDurability;
    protected int Durability;

    protected boolean modifiesAttack;
    protected boolean modifiesParry;

    public Item(PlayerInterface player) {
        super(player);

        modifiesAttack = false;
        modifiesParry = false;

        ForceModifier = 0;

        ArmorModifier = 0;

        MaxDurability = 0;
        
        Durability = 0;
    }

    @Override
    public int strike() {
        if (!modifiesAttack) return super.strike();

        return player.strike() + ForceModifier;
    }

    @Override
    public void parry(int force) {
        if (!modifiesParry) { player.parry(force); return; }

        Durability--;
        player.parry(force - ArmorModifier);
    }
    
}