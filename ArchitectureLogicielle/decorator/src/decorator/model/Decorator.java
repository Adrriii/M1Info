package decorator.model;

public class Decorator extends PlayerInterface {

    private PlayerInterface player;

    public Decorator(PlayerInterface player) {
        this.player = player;
    }

    @Override
    public boolean isAlive() {
        return this.player.isAlive();
    }

    @Override
    public int strike() {
        return this.player.strike();
    }

    @Override
    public void parry(int force) {
        this.player.parry(force);
    }
}