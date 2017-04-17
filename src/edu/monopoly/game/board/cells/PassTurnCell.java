package edu.monopoly.game.board.cells;

public class PassTurnCell extends BoardCell {

    public PassTurnCell(int position) {
        super(position);
    }

    @Override
    public String toString() {
        return "{PassTurnCell} \t[Position] = " + this.getPosition();
    }
}
