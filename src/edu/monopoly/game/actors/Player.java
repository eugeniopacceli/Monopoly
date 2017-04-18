package edu.monopoly.game.actors;

import edu.monopoly.exceptions.UnexpectedNegativeNumberException;
import edu.monopoly.game.board.cells.BoardCell;
import edu.monopoly.game.board.cells.PropertyCell;

/**
 * Defines the players of the game
 */
public class Player extends GameActor {

    private boolean isActive; // Flags if this player is still in the game
    private BoardCell boardCell; // The current position this player is in
    private int statsLaps; // Statistics
    private double statsRentReceived;
    private double statsRentPaid;
    private double statsPropertyBought;
    private int statsSkipTurn;

    /**
     * Constructor, receives a player id and it's initial money
     * @param id the player id
     * @param money the money
     */
    public Player(String id, double money) {
        super(id,money);
        this.setActive(true);
        this.setBoardcell(null);
        this.statsLaps = 0;
        this.statsRentReceived = 0;
        this.statsRentPaid = 0;
        this.statsPropertyBought = 0;
        this.statsSkipTurn = 0; 
    }
    
    /**
     * Buys a property
     * @param prop property to be bought
     * @throws UnexpectedNegativeNumberException 
     */
    public void buyProperty(PropertyCell prop) throws UnexpectedNegativeNumberException {
        this.incrementStatsPropertyBought(prop.getBuyValue()); // Computes statistics
        this.subtractAmount(prop.getBuyValue());
        prop.setOwner(this);
    }
    
    /**
     * Pays rent to a player or loses the game
     * @param prop property to be rented
     * @throws UnexpectedNegativeNumberException 
     */
    public void payRentTo(PropertyCell prop) throws UnexpectedNegativeNumberException {
        this.incrementStatsRentPaid(prop.getRentValue()); // Computes statistics
        if(prop.getRentValue() <= this.getMoney()){ // If this has the money, the owner of the property will receive the rent
            prop.getOwner().addAmount(prop.getRentValue());
            if(prop.getOwner() instanceof Player){ // Computes owner rent statistics
                ((Player)prop.getOwner()).incrementRentReceived(prop.getRentValue());
            }
        }
        this.subtractAmount(prop.getRentValue());
        if(this.getMoney() < 0){ // If this player lacks the money, it will lose the game now
            this.setActive(false);
        }
    }
    
    /**
     * Setters and getters
     * 
     */
    
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        for(PropertyCell p : this.getProperties()){
            p.setOwner(Bank.getInstanceOf());
        }
    }

    @Override
    public String toString() {
        return "{Player} \t[Id]       = " + this.getId() + "\n"
                + "\t[Money]    = " + this.getMoney() + "\n"
                + "\t[IsActive] = " + this.isActive();

    }

    public BoardCell getBoardcell() {
        return boardCell;
    }

    public void setBoardcell(BoardCell boardCell) {
        this.boardCell = boardCell;
    }

    public int getStatsLaps() {
        return statsLaps;
    }

    public double getStatsRentReceived() {
        return statsRentReceived;
    }

    public double getStatsRentPaid() {
        return statsRentPaid;
    }

    public double getStatsPropertyBought() {
        return statsPropertyBought;
    }

    public int getStatsSkipTurn() {
        return statsSkipTurn;
    }
    
    public void incrementStatsLaps() {
        this.statsLaps++;
    }
    
    /**
     * Statistics setters.
     * Can only add, never subtract a value. Those methods are used by the Player class itself.
     * @throws UnexpectedNegativeNumberException 
     * 
     */

    private void incrementRentReceived(double factor) throws UnexpectedNegativeNumberException{
        if(factor < 0){
            throw new UnexpectedNegativeNumberException();
        }
        this.statsRentReceived += factor;
    }
    
    private void incrementStatsRentPaid(double factor) throws UnexpectedNegativeNumberException{
        if(factor < 0){
             throw new UnexpectedNegativeNumberException();           
        }
        this.statsRentPaid += factor;
    }
    
    private void incrementStatsPropertyBought(double factor) throws UnexpectedNegativeNumberException{
        if(factor < 0){
            throw new UnexpectedNegativeNumberException();            
        }
        this.statsPropertyBought += factor;
    }
    
    public void incrementStatsSkipTurn() {
        this.statsSkipTurn++;
    }
}
