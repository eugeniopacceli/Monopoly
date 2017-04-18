package edu.monopoly.game.board.cells;

import edu.monopoly.game.actors.GameActor;

/**
 * A BoardCell specialization. The property cell represents a game property.
 * It can be bought or rented, and has a type.
 */
public class PropertyCell extends BoardCell {

    private PropertyType propertyType; // An instance type
    private double buyValue;
    private double rentValue;
    private GameActor owner; // Current owner

    /**
     * The only possible constructor.
     * @param position the cell position in the game
     * @param type it's property type
     * @param buyValue the value to be paid when bought by someone
     * @param rentValue the rent value
     * @param owner the initial cell owner
     */
    public PropertyCell(int position,
            PropertyType type,
            double buyValue,
            double rentValue,
            GameActor owner) {
        super(position);
        this.setPropertyType(type);
        this.setBuyValue(buyValue);
        this.setRentValue(rentValue);
        this.setOwner(owner);
    }

    /**
     * Sets the cell owner:
     * Removes this cell from the old owner properties list, adds it to the
     * new owner property list. Updates this owner reference too.
     * @param owner the GameActor that owns this property
     */
    public void setOwner(GameActor owner) {
        if(this.getOwner() != null){
            this.getOwner().getProperties().remove(this);
        }
        this.owner = owner;
        owner.getProperties().add(this);
    }

    
    /**
     * Normal setters and getters.
     */
    
    public GameActor getOwner() {
        return this.owner;
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

    // Debuggable info
    @Override
    public String toString() {
        return "{PropertyCell} \t[Position]     = " + this.getPosition() + "\n"
                + "\t\t[PropertyType] = " + this.getPropertyType().name() + "\n"
                + "\t\t[BuyValue]     = " + this.getBuyValue() + "\n"
                + "\t\t[RentValue]    = " + this.getRentValue() + "\n"
                + "\t\t[Owner]        = " + this.getOwner().toString();
    }
    
    
}
