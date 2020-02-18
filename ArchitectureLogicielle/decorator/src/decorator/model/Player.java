package decorator.model;

public class Player extends PlayerInterface {
    protected int HP;

    public Player() {
        this.HP = 100;
    }

    @Override
    public boolean isAlive() {
        return this.HP > 0;
    }

    @Override
    public int strike() {
        return 1;
    }

    @Override
    public void parry(int force) {
        this.HP -= force;
    }
}