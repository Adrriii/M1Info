package decorator.application;

import decorator.model.*;
import decorator.model.equipment.sword.*;
import decorator.model.equipment.shield.*;

public class Main {
    public static void main(String args[]) {
        GameMaster gameMaster = new GameMaster();

        PlayerInterface player1 = new Player();
        PlayerInterface player2 = new Player();

        player1 = new IronSword(player1);

        player2 = new IronShield(player2);
        player2 = new SteelSword(player2);

        gameMaster.fight(player1,player2);
    }
}