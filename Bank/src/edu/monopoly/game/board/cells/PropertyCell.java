package edu.monopoly.game.board.cells;

import edu.monopoly.game.actors.Bank;
import edu.monopoly.game.actors.GameActor;
import edu.monopoly.game.actors.Player;

public class PropertyCell extends BoardCell {

    private PropertyType propertyType;
    private double buyValue;
    private double rentValue;
    private GameActor owner;

    public PropertyCell(int position,
            PropertyType type,
            double buyValue,
            double rentValue,
            GameActor owner) {
        super(position);
        this.setPropertyType(type);
        this.setBuyValue(buyValue);
        this.setRentValue(rentValue);
        this.owner = owner;
    }

    public GameActor getOwner() {
        return this.owner;
    }

    public void buyProperty(Player buyer, PropertyCell property) {
        if (buyer.getMoney() >= property.getBuyValue()) {
            buyer.setMoney(buyer.getMoney() - property.getBuyValue());
            property.owner = buyer;
        }
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public double getRentValue() {
        return rentValue;
    }

    public void setRentValue(double rentValue) {
        this.rentValue = rentValue;
    }

    public double getBuyValue() {
        return this.buyValue;
    }

    public void setBuyValue(double buyValue) {
        this.buyValue = buyValue;
    }

    @Override
    public String toString() {
        return "{PropertyCell} \t[Position]     = " + this.getPosition() + "\n"
                + "\t\t[PropertyType] = " + this.getPropertyType().name() + "\n"
                + "\t\t[BuyValue]     = " + this.getBuyValue() + "\n"
                + "\t\t[RentValue]    = " + this.getRentValue() + "\n"
                + "\t\t[Owner]        = " + this.getOwner().toString();
    }
}
