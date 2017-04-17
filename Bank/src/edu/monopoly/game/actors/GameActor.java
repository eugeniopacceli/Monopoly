package edu.monopoly.game.actors;

import edu.monopoly.exceptions.UnexpectedNegativeNumberException;
import edu.monopoly.game.board.cells.PropertyCell;
import java.util.ArrayList;
import java.util.List;

public abstract class GameActor {

    protected double money;
    protected String id;
    protected List<PropertyCell> properties;
   
    protected GameActor(String id, double money) {
        this.money = money;
        this.id = id;
        properties = new ArrayList<>();
    }

    public double getMoney() {
        return this.money;
    }

    public List<PropertyCell> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyCell> properties) {
        this.properties = properties;
    }
    
    public void addAmount(double money) throws UnexpectedNegativeNumberException{
        if(money < 0){
            throw new UnexpectedNegativeNumberException();
        }
        this.money += money;
    }
    
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
