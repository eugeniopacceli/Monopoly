package edu.monopoly.game.board;

import edu.monopoly.game.board.cells.BoardCell;
import java.io.PrintStream;
import java.util.List;

import edu.monopoly.game.actors.Bank;

public class Board {

    private List<BoardCell> cells;
    private Bank bank;

    public Board() {
        this.bank = null;
        this.cells = null;
    }
    
    public Board(List<BoardCell> cells, Bank bank){
        this.bank = bank;
        this.cells = cells;
    }

    public List<BoardCell> getCells() {
        return this.cells;
    }

    public void setCells(List<BoardCell> cells) {
        this.cells = cells;
    }

    public void printBoard(PrintStream output) {
        for (BoardCell c : cells) {
            output.println(c.toString());
        }
    }
    
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

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

}
