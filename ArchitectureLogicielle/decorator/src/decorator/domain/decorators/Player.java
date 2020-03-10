package decorator.domain.decorators;

public class Player implements PlayerInterface {
    protected int HP;
    protected String nickname;

    public Player(String nickname) {
        this.HP = 100;
        this.nickname = nickname;
    }

    @Override
    public void setHP(int hp) {
        HP = hp;
    }

    @Override
    public int getHP() {
        return HP;
    }

    @Override
    public String getNickname() {
        return nickname;
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
        if(force <= 0) return;
        this.HP -= force;
        System.out.printf("%s : %d (%d)\n",this.nickname, HP, -force);
    }
}