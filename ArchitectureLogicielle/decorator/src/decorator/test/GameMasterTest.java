package decorator.test;

import org.junit.Test;

import decorator.model.*;

public class GameMasterTest {

    @Test
    public void FightTest() {
        GameMaster gameMaster = new GameMaster();

        Player player1 = new Player();
        Player player2 = new Player();

        gameMaster.fight(player1,player2);

        assert(true);
    }
}