package edu.monopoly.actors;

public class Bank extends GameActor
{
	private static Bank bank;
	
	private Bank(){
		super(Double.POSITIVE_INFINITY);
	}
	
	public static Bank getInstanceOf()
	{	
		if(bank == null)
			bank = new Bank();		
		
		return bank;
	}
	
	@Override
	public String toString()
	{
		return "{BANK}";
	}
}
