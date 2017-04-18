package edu.monopoly.game.actors;

import edu.monopoly.exceptions.UnexpectedNegativeNumberException;
import edu.monopoly.game.board.cells.PropertyCell;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a abstract game actor, which interacts with the game, can manipulate
 * money and own game properties.
 */
public abstract class GameActor {

    protected double money;
    protected String id;
    protected List<PropertyCell> properties; // The properties this actor owns
   
    /**
     * Constructor which receives an id and some money
     * @param id this actor's id
     * @param money  this actor's initial money
     */
    protected GameActor(String id, double money) {
        this.money = money;
        this.id = id;
        properties = new ArrayList<>();
    }

    /**
     * Getters and setters
     * 
     */

    public double getMoney() {
        return this.money;
    }

    public List<PropertyCell> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyCell> properties) {
        this.properties = properties;
    }
    
    /**
     * Adds an amount to this actor's cash
     * @param money to be added
     * @throws UnexpectedNegativeNumberException, if the value received is < 0
     */
    public void addAmount(double money) throws UnexpectedNegativeNumberException{
        if(money < 0){
            throw new UnexpectedNegativeNumberException();
        }
        this.money += money;
    }
    
    /**
     * Subtracts an amount of this actor's cash
     * @param money to be subtracted
     * @throws UnexpectedNegativeNumberException, if the value received is < 0
     */
    public void subtractAmount(double money) throws UnexpectedNegativeNumberException{
        if(money < 0){
            throw new UnexpectedNegativeNumberException();
        }
        this.money -= money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
