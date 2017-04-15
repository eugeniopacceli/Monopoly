package edu.monopoly.game.board.cells;

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
