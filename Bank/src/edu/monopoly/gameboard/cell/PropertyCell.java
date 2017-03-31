package edu.monopoly.gameboard.cell;

import java.io.PrintStream;

import edu.monopoly.actors.Bank;
import edu.monopoly.actors.GameActor;
import edu.monopoly.actors.Player;
import edu.monopoly.gameboard.BoardCell;

public class PropertyCell extends BoardCell
{
    private PropertyType propertyType;
    private double buyValue;
    private double rentValue;
    private GameActor owner;

    public PropertyCell(int position,
    					PropertyType type, 
    					double buyValue,
    					double rentValue,
    					Bank bank)
    {
    	super(position);
        this.setPropertyType(type);
        this.setBuyValue(buyValue);
        this.setRentValue(rentValue);
        this.owner = bank;
    }

    public GameActor getOwner()
    {
        return this.owner;
    }

    public void buyProperty(Player buyer, PropertyCell property)
    {
        if(buyer.getMoney() >= property.getBuyValue())
        {
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
	public String toString()
	{
		return  "{PropertyCell} \t[Position]     = " + this.getPosition()       + "\n"
				           + "\t\t[PropertyType] = " + this.getPropertyType()   + "\n"
				           + "\t\t[BuyValue]     = " + this.getBuyValue()       + "\n"
				           + "\t\t[RentValue]    = " + this.getRentValue()      + "\n"
				           + "\t\t[Owner]        = " + this.getOwner().toString();
	}		
}
