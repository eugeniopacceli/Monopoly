package edu.monopoly.io;

import edu.monopoly.game.actors.Player;
import edu.monopoly.exceptions.InvalidCellTypeException;
import edu.monopoly.exceptions.InvalidDiceRollException;
import edu.monopoly.game.commands.Command;
import edu.monopoly.game.board.Board;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * An interface to define a GameReader.
 * A GameReader is an object that receives the input files, as defined in TP1.pdf,
 * and is capable of generating the list of commands to be ran in a game, the board
 * with all of it's cells, and the players map (by id).
 */
public interface GameReader {    
    public void setSources(String boardInputAddr, String diceRollsInput); // Receives the input files

    public List<Command> generateCommandsList() throws InvalidDiceRollException, IOException;
    public Board generateBoard() throws InvalidCellTypeException, IOException;
    public Map<String, Player> generatePlayersList() throws IOException;
}
