package edu.monopoly.app;

import edu.monopoly.exceptions.InsufficientGameInformationException;
import edu.monopoly.exceptions.InvalidCellTypeException;
import edu.monopoly.exceptions.InvalidDiceRollException;
import edu.monopoly.game.GameEmulator;
import edu.monopoly.io.GameReader;
import edu.monopoly.io.GameReaderImpl;
import java.io.IOException;

public class Main {

    public static final String BOARDPATH = "tabuleiro.txt";
    public static final String PLAYINGPATH = "jogadas.txt";
    public static final String EXCEPTIONERRORMESSAGE = "[AN EXCEPTION HAS OCCURRED]";

    public static void main(String[] args) {
        GameReader gameReader = new GameReaderImpl(BOARDPATH, PLAYINGPATH);
        GameEmulator game;
        try {
            game = new GameEmulator(gameReader.generatePlayersList(),
                    gameReader.generateCommandsList(),
                    gameReader.generateBoard());
            game.play();
            System.out.println(game.generateStatistics());
        } catch (InvalidCellTypeException | InvalidDiceRollException | IOException | InsufficientGameInformationException ex) {
            System.err.println(EXCEPTIONERRORMESSAGE);
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
