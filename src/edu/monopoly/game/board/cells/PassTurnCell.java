package edu.monopoly.game.board.cells;

/**
 * A BoardCell specialization. The pass turn cell is used to represent a player
 * must pass it's turn if it lands here.
 */
public class PassTurnCell extends BoardCell {

    public PassTurnCell(int position) {
        super(position);
    }

    // Debuggable info
    @Override
    public String toString() {
        return "{PassTurnCell} \t[Position] = " + this.getPosition();
    }
}
