package edu.monopoly.exceptions;

/**
 * This exception is used by an object of GameEmulator when one of the 
 * internal object references needed by it to work are missing.
 */
public class InsufficientGameInformationException extends Exception {

    public InsufficientGameInformationException(){
        super("Game Emulator lacks information to start a game.");
    }
}
