package edu.monopoly.exceptions;

public class InvalidDiceRollException extends Exception {

    public InvalidDiceRollException() {
        super("The number received for a dice roll is in bound of [1...6]");
    }
}