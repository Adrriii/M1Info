package decorator.model;

public class GameMaster {

    public void fight(PlayerInterface s1, PlayerInterface s2) {
        PlayerInterface attack = s1;
        PlayerInterface defend = s2;

        while(s1.isAlive() && s2.isAlive()) {
            defend.parry(attack.strike());
            PlayerInterface tmpSwap = attack;
            attack = defend;
            defend = tmpSwap;
        }
        
        if (s1.isAlive())  {
            System.out.println("Player 1  Won");
        }
        else {
            System.out.println("Player 2  Won");
        }
    }
}