package edu.monopoly.actors;

public abstract class GameActor
{
	protected double money;	
	
	protected GameActor(double money)
	{
		this.money = money;
	}
	
	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;		
	}
}
