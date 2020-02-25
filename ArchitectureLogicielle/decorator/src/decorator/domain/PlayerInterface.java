package decorator.domain;

public abstract class PlayerInterface {

    abstract public String getNickname();

    abstract public boolean isAlive();

    abstract public int strike();

    abstract public void parry(int force);

}