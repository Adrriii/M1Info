package soldiers.test;

import org.junit.Test;

import soldiers.domain.*;
import soldiers.domain.equipment.*;
import soldiers.domain.equipment.sword.*;
import soldiers.domain.equipment.shield.*;
import soldiers.domain.equipment.wand.*;
import soldiers.domain.equipment.axe.*;
import soldiers.domain.unit.*;
import soldiers.domain.unit.classe.*;
import soldiers.model.GameMaster;

public class TestGameBehavior {

    @Test
    public void UnitSimpleInUnitSimple() {

        Unit seth = new Horseman("Seth");
        Unit eirika = new Infantryman("Eirika");

        try {
            eirika.add(seth);
            assert(false);
        } catch(UnsupportedOperationException e) {
            assert(true);
        };
        
    }

    @Test
    public void AlreadyEquipped() {

        Unit seth = new Horseman("Seth");
        Unit eirika = new Infantryman("Eirika");

        Equipment sw = new IronSword();
        
        try { 
            eirika.parry(seth.strike());
            eirika.addEquipment(sw);
            seth.addEquipment(sw); // Lève une exception car l'arme est déjà attachée
            assert(false);
        } catch(ImpossibleExtensionException e) {
            assert(true);
        }
    }

    @Test
    public void InfantryTwoWeapons() {

        Unit seth = new Horseman("Seth");
        Unit eirika = new Infantryman("Eirika");

        Equipment sw = new IronSword();
        Equipment sh = new IronShield();
        Equipment sw2 = new IronSword();
        try { 
            eirika.parry(seth.strike());
            eirika.addEquipment(sw);
        }catch(ImpossibleExtensionException e) {
            
        }
      
        try {
            eirika.removeEquipment(sw);
            seth.addEquipment(sw);
            seth.addEquipment(sh);    
            seth.addEquipment(sw2); // Lève une exception car deux armes maximum
            assert(false);
        }catch(ImpossibleExtensionException e) {
            assert(true);
        }
    }

    public void StrongmanTwoHandedAndShield() {

        Unit seth = new Horseman("Seth");
        Unit eirika = new Infantryman("Eirika");
        Unit garcia = new Strongman("Garcia");

        Equipment sw = new IronSword();
        Equipment sh = new IronShield();

        try { 
            eirika.parry(seth.strike());
            eirika.addEquipment(sw);
        } catch(ImpossibleExtensionException e) {
            
        }
      
        try {
            eirika.removeEquipment(sw);
            seth.addEquipment(sw);
            seth.addEquipment(sh);
        } catch(ImpossibleExtensionException e) {
            
        }

        Army good = new Army("Good");
        good.add(seth);
        good.add(eirika);
        good.add(garcia);

        try {
            garcia.addEquipment(new LumberAxe());
            garcia.addEquipment(new LumberAxe());
        }catch(ImpossibleExtensionException e) {
            
        }

        try {
            garcia.addEquipment(new IronShield());
            assert(false);
        }catch(ImpossibleExtensionException e) {
            assert(true);
        }
    }
 
    @Test
    public void testBehavior() {

        Unit seth = new Horseman("Seth");
        Unit eirika = new Infantryman("Eirika");
        Unit garcia = new Strongman("Garcia");

        Equipment sw = new IronSword();
        Equipment sh = new IronShield();

        try { 
            eirika.parry(seth.strike());
            eirika.addEquipment(sw);
        } catch(ImpossibleExtensionException e) {
            assert(false);
        }
      
        try {
            eirika.removeEquipment(sw);
            seth.addEquipment(sw);
            seth.addEquipment(sh);
        } catch(ImpossibleExtensionException e) {
            assert(false);
        }

        Army good = new Army("Good");
        good.add(seth);
        good.add(eirika);
        good.add(garcia);

        try {
            garcia.addEquipment(new LumberAxe());
            garcia.addEquipment(new LumberAxe());
        } catch(ImpossibleExtensionException e) {
            assert(false);
        }

        try {
          garcia.addEquipment(new IronShield());
        } catch(ImpossibleExtensionException e) { };

        Army evil = new Army("Evil");
        evil.add(new Creature("Entombed"));
        evil.add(new Creature("Entombed"));

        Mage conjurer = new Mage("Conjurer");
        try {
            conjurer.addEquipment(new ApprenticeWand());
        }catch(ImpossibleExtensionException e) {
            assert(false);
        }
        evil.add(conjurer);
        
        GameMaster gameMaster = new GameMaster();
        gameMaster.fight(good, evil);
        assert(true);
    }
}