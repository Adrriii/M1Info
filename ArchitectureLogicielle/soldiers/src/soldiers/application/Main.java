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
        UnitSimple hm = new Horseman("Seth");
        UnitSimple im = new Infantryman("Eirika");
        Equipment sw = new IronSword();
        Equipment sh = new IronShield();
        Equipment sw2 = new IronSword();
        try { 
          im.parry(hm.strike());
          im.addEquipment(sw);
          hm.addEquipment(sw); // Lève une exception car l'arme est déjà attachée
        }catch(ImpossibleExtensionException e) {
          System.out.println("exception 1");
        }
      
        try {
         im.removeEquipment(sw);
         hm.addEquipment(sw);
         hm.addEquipment(sh);    
         hm.addEquipment(sw2); // Lève une exception car deux armes maximum
        }catch(ImpossibleExtensionException e) {
          System.out.println("exception 2");
        }
          
        GameMaster gameMaster = new GameMaster();
        gameMaster.fight(hm, im);
    }
}