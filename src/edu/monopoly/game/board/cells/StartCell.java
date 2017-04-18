package edu.monopoly.game.board.cells;

/**
 * A BoardCell specialization. The start cell, in which all the players start
 * the game in.
 */
public class StartCell extends BoardCell {

    // The pass value of the start cell is specified as always 500.0.
    // However we left an open setter for convenience
    private double passValue = 500.0;

    public StartCell(int position) {
        super(position);
    }

    public double getPassValue() {
        return passValue;
    }

    public void setPassValue(double passValue) {
        this.passValue = passValue;
    }

    // Debuggable info
    @Override
    public String toString() {
        return "{StartCell}\t[Position] = " + this.getPosition() + "\n"
                + "\t\t[PassVaue] = " + passValue;
    }
}
