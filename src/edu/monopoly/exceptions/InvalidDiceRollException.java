package edu.monopoly.exceptions;

/*
* This exception is thrown by GameReaderImpl when it reads from the input file an
* invalid dice roll number (1 < or > 6)
*/
public class InvalidDiceRollException extends Exception {

    public InvalidDiceRollException() {
        super("The number received for a dice roll is in bound of [1...6]");
    }
}