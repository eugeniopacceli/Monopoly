package edu.monopoly.game.board.cells;

/**
 * An abstract class representing all the board cells.
 * They have in common the game position they are in.
 */
public abstract class BoardCell {

    private int position;

    protected BoardCell(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
