/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author eugen
 */
public interface GameReader {    
    public void setSources(String boardInputAddr, String diceRollsInput);

    public List<Command> generateCommandsList() throws InvalidDiceRollException, IOException;
    public Board generateBoard() throws InvalidCellTypeException, IOException;
    public Map<String, Player> generatePlayersList() throws InvalidCellTypeException, IOException;
}
