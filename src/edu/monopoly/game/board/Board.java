package edu.monopoly.game.board;

import edu.monopoly.game.board.cells.BoardCell;
import java.io.PrintStream;
import java.util.List;

import edu.monopoly.game.actors.Bank;

/**
 * Represents the game board, the object which holds all the cells, and the game bank
 */
public class Board {

    private List<BoardCell> cells; // Cells list
    private Bank bank; // Bank

    /**
     * Empty constructor
     */
    public Board() {
        this.bank = null;
        this.cells = null;
    }
    
    /**
     * Constructor which receives a cell list and a bank object
     * @param cells
     * @param bank 
     */
    public Board(List<BoardCell> cells, Bank bank){
        this.bank = bank;
        this.cells = cells;
    }

    /**
     * An debug function which prints all the cells in this board, using their
     * toString method, to a given output.
     * @param output , the output to receive the strings
     */
    public void printBoard(PrintStream output) {
        for (BoardCell c : cells) {
            output.println(c.toString());
        }
    }
    
    /**
     * Convenience function, finds a cell by it's position in the game 
     * @param i , the cell's game position
     * @return The cell found or null
     */
    public BoardCell findCellByPosition(int i){
        BoardCell cell = null;
        for(BoardCell c : this.getCells()){
            if(c.getPosition() == i){
                cell = c;
                break;
            }
        }
        return cell;
    }

    /**
     * Setters and getters
     * 
     */

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<BoardCell> getCells() {
        return this.cells;
    }

    public void setCells(List<BoardCell> cells) {
        this.cells = cells;
    }

}
