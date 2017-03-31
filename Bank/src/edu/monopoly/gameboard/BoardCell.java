package edu.monopoly.gameboard;

import edu.monopoly.actors.Bank;
import edu.monopoly.gameboard.cell.PassTurnCell;
import edu.monopoly.gameboard.cell.PropertyCell;
import edu.monopoly.gameboard.cell.PropertyType;
import edu.monopoly.gameboard.cell.StartCell;
import edu.monopoly.util.IPrintable;

public abstract class BoardCell
{   
    private int position;  
    
    protected BoardCell(int position)
    {
    	this.position = position;
    }
	
	public static BoardCell createCell(Bank bank, String parameters) throws Exception
	{			
		String[] args = parameters.split(";");
		
		//int linePosition = Integer.parseInt(args[0]);
		int cellPosition = Integer.parseInt(args[1]);
		int cellType = Integer.parseInt(args[2]);	  
	
		int propertyType;
		int propertyValue;
		int rentValue;
		
		BoardCell cell;		
		
		switch(cellType)
		{
			case 1: 
				cell = new StartCell(cellPosition);	        			
				break;
			case 2:
				cell = new PassTurnCell(cellPosition);
				break;
			case 3:
				propertyType = Integer.parseInt(args[3]); 
				propertyValue = Integer.parseInt(args[4]);
				rentValue = Integer.parseInt(args[5]);	        			
				
				cell = new PropertyCell(cellPosition,
										PropertyType.values()[propertyType],
						                propertyValue, 
						                rentValue,
						                bank);
				break;
			default:				
				throw new Exception("Erro ao criar célula: Tipo não definido.");				
		}
		
		return cell;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
