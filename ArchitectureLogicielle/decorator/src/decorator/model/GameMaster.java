package decorator.model;

import decorator.domain.PlayerInterface;

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
            System.out.println(s1.getNickname() + "  Won");
        }
        else {
            System.out.println(s2.getNickname() + "  Won");
        }
    }
}