package soldiers.application;

import soldiers.domain.ImpossibleExtensionException;
import soldiers.domain.equipment.*;
import soldiers.domain.equipment.sword.*;
import soldiers.domain.equipment.shield.*;
import soldiers.domain.unit.*;
import soldiers.domain.unit.classe.*;
import soldiers.model.GameMaster;

public class Main {
    public static void main(String args[]) {
        UnitSimple seth = new Horseman("Seth");
        UnitSimple eirika = new Infantryman("Eirika");
        eirika.add(seth);
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
        good.add(eirika);

        Army evil = new Army("Evil");
          
        GameMaster gameMaster = new GameMaster();
        gameMaster.fight(good, evil);
    }
}