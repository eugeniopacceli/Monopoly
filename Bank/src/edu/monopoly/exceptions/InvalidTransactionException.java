package edu.monopoly.exceptions;

public class InvalidTransactionException extends Exception{
    
    public InvalidTransactionException() {    
        super("A transaction involved setting some player's money amount to a value lesser than 0");
    }
}
