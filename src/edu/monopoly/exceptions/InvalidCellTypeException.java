package edu.monopoly.exceptions;

/**
 * This exception is thrown by GameReaderImpl when it reads a PropertyCell from the input
 * file and it's type is unknown
 */
public class InvalidCellTypeException extends Exception{
    public InvalidCellTypeException() {
        super("Invalid cell type read from file.");
    }
}
