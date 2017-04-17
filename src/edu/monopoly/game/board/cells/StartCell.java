package edu.monopoly.game.board.cells;

public class StartCell extends BoardCell {

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

    @Override
    public String toString() {
        return "{StartCell}\t[Position] = " + this.getPosition() + "\n"
                + "\t\t[PassVaue] = " + passValue;
    }
}
