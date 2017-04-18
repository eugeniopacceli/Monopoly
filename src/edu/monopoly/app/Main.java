package edu.monopoly.app;

import edu.monopoly.exceptions.InsufficientGameInformationException;
import edu.monopoly.exceptions.InvalidCellTypeException;
import edu.monopoly.exceptions.InvalidDiceRollException;
import edu.monopoly.exceptions.UnexpectedNegativeNumberException;
import edu.monopoly.game.GameEmulator;
import edu.monopoly.io.GameReader;
import edu.monopoly.io.GameReaderImpl;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Monopoly app bootstrap
 */
public class Main {

    // String constants
    public static final String BOARDPATH = "tabuleiro.txt";
    public static final String PLAYINGPATH = "jogadas.txt";
    public static final String OUTPUT = "estatisticas.txt";
    public static final String EXCEPTIONERRORMESSAGE = "[AN EXCEPTION HAS OCCURRED]";

    public static void main(String[] args) {
        GameReader gameReader = new GameReaderImpl(BOARDPATH, PLAYINGPATH); // object that reads the inputs
        GameEmulator game;
        PrintStream outputStream;
        try {
            outputStream = new PrintStream(new FileOutputStream(OUTPUT));
            game = new GameEmulator(gameReader.generatePlayersList(),
                    gameReader.generateCommandsList(),
                    gameReader.generateBoard(),
                    outputStream); // Instances a game
            game.play(); // Run it
            outputStream.close(); // Closes the output stream used by the game emulator
        } catch (InvalidCellTypeException | 
                 InvalidDiceRollException |
                 IOException |
                 InsufficientGameInformationException |
                 UnexpectedNegativeNumberException ex) { // Possible exceptions
            System.err.println(EXCEPTIONERRORMESSAGE);
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
