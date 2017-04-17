package edu.monopoly.game.actors;

import edu.monopoly.exceptions.UnexpectedNegativeNumberException;
import edu.monopoly.game.board.cells.BoardCell;
import edu.monopoly.game.board.cells.PropertyCell;

public class Player extends GameActor {

    private int id;
    private boolean isActive;
    private BoardCell boardcell;
    private int statsLaps;
    private double statsRentReceived;
    private double statsRentPaid;
    private double statsPropertyBuyed;
    private int statsSkipTurn;

    public Player(String id, double money) {
        super(id,money);
        this.setActive(true);
        this.setBoardcell(null);
        this.statsLaps = 0;
        this.statsRentReceived = 0;
        this.statsRentPaid = 0;
        this.statsPropertyBuyed = 0;
        this.statsSkipTurn = 0; 
    }
    
    public void buyProperty(PropertyCell prop) throws UnexpectedNegativeNumberException {
        this.incrementStatsPropertyBuyed(prop.getBuyValue());
        this.subtractAmount(prop.getBuyValue());
        prop.setOwner(this);
    }
    
    public void payRentTo(PropertyCell prop) throws UnexpectedNegativeNumberException {
        this.incrementStatsRentPaid(prop.getRentValue());
        if(prop.getRentValue() <= this.getMoney()){
            prop.getOwner().addAmount(prop.getRentValue());
            if(prop.getOwner() instanceof Player){
                ((Player)prop.getOwner()).incrementRentReceived(prop.getRentValue());
            }
        }
        this.subtractAmount(prop.getRentValue());
        if(this.getMoney() < 0){
            this.setActive(false);
        }
    }
    
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
        return boardcell;
    }

    public void setBoardcell(BoardCell boardcell) {
        this.boardcell = boardcell;
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

    public double getStatsPropertyBuyed() {
        return statsPropertyBuyed;
    }

    public int getStatsSkipTurn() {
        return statsSkipTurn;
    }
    
    public void incrementStatsLaps() {
        this.statsLaps++;
    }
    
    public void incrementRentReceived(double factor) throws UnexpectedNegativeNumberException{
        if(factor < 0){
            throw new UnexpectedNegativeNumberException();
        }
        this.statsRentReceived += factor;
    }
    
    public void incrementStatsRentPaid(double factor) throws UnexpectedNegativeNumberException{
        if(factor < 0){
             throw new UnexpectedNegativeNumberException();           
        }
        this.statsRentPaid += factor;
    }
    
    public void incrementStatsPropertyBuyed(double factor) throws UnexpectedNegativeNumberException{
        if(factor < 0){
            throw new UnexpectedNegativeNumberException();            
        }
        this.statsPropertyBuyed += factor;
    }
    
    public void incrementStatsSkipTurn() {
        this.statsSkipTurn++;
    }
}
