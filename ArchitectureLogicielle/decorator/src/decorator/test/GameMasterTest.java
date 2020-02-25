package decorator.test;

import org.junit.Test;

import decorator.model.*;
import decorator.domain.*;

public class GameMasterTest {

    @Test
    public void FightTest() {
        GameMaster gameMaster = new GameMaster();

        Player player1 = new Player("test1");
        Player player2 = new Player("test2");

        gameMaster.fight(player1,player2);

        assert(true);
    }
}