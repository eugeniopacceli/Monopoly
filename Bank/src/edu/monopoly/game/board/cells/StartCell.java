package edu.monopoly.game.board.cells;

public class StartCell extends BoardCell {

    private static double passValue = 500.0;

    public StartCell(int position) {
        super(position);
    }

    public static double getPassValue() {
        return passValue;
    }

    public static void setPassValue(double passValue) {
        StartCell.passValue = passValue;
    }

    @Override
    public String toString() {
        return "{StartCell}\t[Position] = " + this.getPosition() + "\n"
                + "\t\t[PassVaue] = " + passValue;
    }
}
