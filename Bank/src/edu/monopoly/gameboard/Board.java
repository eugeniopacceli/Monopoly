package edu.monopoly.gameboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.monopoly.actors.Bank;

public class Board
{    
    private List<BoardCell> cells;
    private String boardPath;
    private Bank bank;    

    public Board(String boardPath)
    {
        this.boardPath = boardPath;
        this.bank = Bank.getInstanceOf();
        this.cells = new ArrayList<BoardCell>(); 
        this.buildBoard();        
    }

    private void buildBoard()
    {    	
		Scanner input;
		try 
		{
			input = new Scanner(new File(this.boardPath));
			String line = input.nextLine();
	        while(input.hasNextLine())
	        {
	        	line = input.nextLine();	        	
	        	BoardCell cell = BoardCell.createCell(bank, line);	        	
	        	this.getCells().add(cell);
	        }
	        
		} catch (FileNotFoundException e) {
			System.err.println("Falha ao abrir o arquivo de Tabuleiro!");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}	

	public List<BoardCell> getCells() {
		return this.cells;
	}

	public void setCells(List<BoardCell> cells) {
		this.cells = cells;
	}
	
	public void printBoard(PrintStream output){
		
			
		for(BoardCell c : cells)
			output.println(c.toString());
	}
	
}
