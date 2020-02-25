package decorator.application;

import decorator.model.*;
import decorator.domain.*;
import decorator.domain.classe.Horseman;
import decorator.domain.classe.Infantryman;
import decorator.domain.equipment.sword.*;
import decorator.domain.equipment.shield.*;

public class Main {
    public static void main(String args[]) {
        // UnitSimple hm = new Horseman("Seth");
        // UnitSimple im = new Infantryman("Eirika");
        // im.parry(hm.strike());
        // try {
        //     im.addSword();
        //     hm.addShield();
        //     hm.parry(im.strike());
        //     im.addShield(); 
        //     im.addSword(); // Lève une exception car deux armes maximum
        // }catch(ImpossibleExtensionException e) {}
          

        GameMaster gameMaster = new GameMaster();

        PlayerInterface player1 = new IronShield(new IronSword( new Infantryman("Eirika")));
        PlayerInterface player2 = new Horseman("Seth");

        gameMaster.fight(player1,player2);
    }
}