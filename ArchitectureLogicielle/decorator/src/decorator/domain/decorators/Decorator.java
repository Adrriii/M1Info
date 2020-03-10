package decorator.domain.decorators;

public class Decorator implements PlayerInterface {

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
    public void setHP(int hp) {
        player.setHP(hp);
    }

    @Override
    public int getHP() {
        return player.getHP();
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

    public boolean equals(Decorator equipment) {
        System.out.println(equipment.getClass().getSimpleName());
        return equipment.getClass().getSimpleName().equals(this.getClass().getSimpleName());
    }
}