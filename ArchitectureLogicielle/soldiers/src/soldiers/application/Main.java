package soldiers.application;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.*;
import soldiers.domain.equipment.axe.LumberAxe;
import soldiers.domain.equipment.sword.*;
import soldiers.domain.equipment.wand.ApprenticeWand;
import soldiers.domain.equipment.shield.*;
import soldiers.domain.unit.*;
import soldiers.domain.unit.classe.*;
import soldiers.model.GameMaster;

public class Main {
    public static void main(String args[]) {
        Unit seth = new Horseman("Seth");
        Unit eirika = new Infantryman("Eirika");
        Unit garcia = new Strongman("Garcia");

        try {
          eirika.add(seth);
        } catch(UnsupportedOperationException e) {
          System.out.println("exception add ok");
        }

        Equipment sw = new IronSword();
        Equipment sh = new IronShield();
        Equipment sw2 = new IronSword();
        try { 
          eirika.parry(seth.strike());
          eirika.addEquipment(sw);
          seth.addEquipment(sw); // Lève une exception car l'arme est déjà attachée
        }catch(ImpossibleExtensionException e) {
          System.out.println("exception 1 ok");
        }
      
        try {
          eirika.removeEquipment(sw);
         seth.addEquipment(sw);
         seth.addEquipment(sh);    
         seth.addEquipment(sw2); // Lève une exception car deux armes maximum
        }catch(ImpossibleExtensionException e) {
          System.out.println("exception 2 ok");
        }

        Army good = new Army("Good");
        good.add(seth);
        good.add(eirika);
        good.add(garcia);

        try {
          garcia.addEquipment(new LumberAxe());
          garcia.addEquipment(new LumberAxe());
        }catch(ImpossibleExtensionException e) {
          System.out.println("problem");
        }

        try {
          garcia.addEquipment(new IronShield());
        }catch(ImpossibleExtensionException e) {
          System.out.println("exception 3 ok");
        }

        Army evil = new Army("Evil");
        evil.add(new Creature("Entombed"));
        evil.add(new Creature("Entombed"));

        Mage conjurer = new Mage("Conjurer");
        try {
          conjurer.addEquipment(new ApprenticeWand());
        }catch(ImpossibleExtensionException e) {
          System.out.println("problem");
        }
        evil.add(conjurer);
        
        GameMaster gameMaster = new GameMaster();
        gameMaster.fight(good, evil);
    }
}