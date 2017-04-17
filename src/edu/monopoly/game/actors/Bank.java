package edu.monopoly.game.actors;

public class Bank extends GameActor {

    private static Bank bank;

    private Bank() {
        super("Bank",Double.POSITIVE_INFINITY);
    }

    public static Bank getInstanceOf() {
        if (bank == null) {
            bank = new Bank();
        }

        return bank;
    }

    @Override
    public String toString() {
        return "{BANK}";
    }
}