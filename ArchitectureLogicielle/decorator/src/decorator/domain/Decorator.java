package decorator.domain;

public class Decorator extends PlayerInterface {

    protected PlayerInterface player;

    public Decorator(PlayerInterface player) {
        this.player = player;
    }

    public PlayerInterface parent() {
        return player;
    }

    public void newparent(PlayerInterface parent) {
        player = parent;
    }
    
    @Override
    public String getNickname() {
        return player.getNickname();
    }

    @Override
    public boolean isAlive() {
        return player.isAlive();
    }

    @Override
    public int strike() {
        return player.strike();
    }

    @Override
    public void parry(int force) {
        player.parry(force);
    }
}