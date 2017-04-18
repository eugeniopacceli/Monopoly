package edu.monopoly.exceptions;

/**
 * This exception is thrown by Player and other game related objects when an 
 * negative value is received where it was not expected.
 */
public class UnexpectedNegativeNumberException extends Exception {
    
    public UnexpectedNegativeNumberException(){
        super("A negative number appeard into one of the game's transactions.");
    }
}
