package edu.monopoly.game.actors;

/*
* The Bank Singleton. The Bank is a special game actor that
* has an infinite amount of money and mantains the same state
* for all games and games's states.
*/
public class Bank extends GameActor {

    private static Bank bank;

    /*
    * The bank has infinite money and shall only be instantiated by getInstanceOf()
    */
    private Bank() {
        super("Bank",Double.POSITIVE_INFINITY);
    }

    /**
     * The Bank needs to be instantiated only once for this entire program execution
     * @returns the only bank instance possible.
     */
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
