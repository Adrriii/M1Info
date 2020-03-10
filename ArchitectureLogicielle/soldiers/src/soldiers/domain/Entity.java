package soldiers.domain;

public interface Entity {

    abstract public void setHP(int hp);

    abstract public int getHP();

    abstract public boolean alive();

    abstract public int strike();

    abstract public int parry(int force);

    abstract public void heal();

    abstract public int initialHealth();

    abstract public String nickname();

}