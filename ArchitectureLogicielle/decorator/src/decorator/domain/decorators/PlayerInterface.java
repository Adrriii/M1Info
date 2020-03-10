package decorator.domain.decorators;

public interface PlayerInterface {

    abstract public void setHP(int hp);

    abstract public int getHP();

    abstract public String getNickname();

    abstract public boolean isAlive();

    abstract public int strike();

    abstract public void parry(int force);

}