package decorator.application;

import decorator.model.*;
import decorator.domain.entities.*;
import decorator.domain.entities.classe.*;
import decorator.domain.entities.equipment.*;

public class Main {
    public static void main(String args[]) {
        UnitSimple hm = new Horseman("Seth");
        UnitSimple im = new Infantryman("Eirika");
        Equipment sw = new Sword();
        Equipment sh = new Shield();
        Equipment sw2 = new Sword();
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