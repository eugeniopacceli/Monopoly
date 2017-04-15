package edu.monopoly.game.actors;

public class Player extends GameActor {

    private int id;
    private boolean isActive;

    public Player(int id, double money) {
        super(money);
        this.setId(id);
        this.setActive(true);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
        if (this.money < 0) {
            this.isActive = false;
        }
    }

    @Override
    public String toString() {
        return "{Player} \t[Id]       = " + this.getId() + "\n"
                + "\t[Money]    = " + this.getMoney() + "\n"
                + "\t[IsActive] = " + this.isActive();

    }
}
