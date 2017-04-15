/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.monopoly.io;

import edu.monopoly.game.actors.Bank;
import edu.monopoly.game.commands.PlayCommand;
import edu.monopoly.game.actors.Player;
import edu.monopoly.exceptions.InvalidCellTypeException;
import edu.monopoly.exceptions.InvalidDiceRollException;
import edu.monopoly.game.commands.Command;
import edu.monopoly.game.commands.DumpCommand;
import edu.monopoly.game.board.Board;
import edu.monopoly.game.board.cells.BoardCell;
import edu.monopoly.game.board.cells.PassTurnCell;
import edu.monopoly.game.board.cells.PropertyCell;
import edu.monopoly.game.board.cells.PropertyType;
import edu.monopoly.game.board.cells.StartCell;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author eugen
 */
public class GameReaderImpl implements GameReader{
    protected String boardInputAddr;
    protected String diceRollsInput;
    protected HashMap<Integer, Player> players;
    protected double playerStartAmount;

    public GameReaderImpl(){
        this.boardInputAddr = this.diceRollsInput = null;
        players = new HashMap<>();
        playerStartAmount = 0.0;
    }
    
    public GameReaderImpl(String boardInputAddr, String diceRollsInput) {
        this.setSources(boardInputAddr, diceRollsInput);
        players = new HashMap<>();
    }
    
    @Override
    public void setSources(String boardInputAddr, String diceRollsInput) {
        this.boardInputAddr = boardInputAddr;
        this.diceRollsInput = diceRollsInput;
    }

    private PlayCommand generateDiceRoll(String parameters) throws InvalidDiceRollException {
        String[] args = parameters.split(";");
        Integer player = Integer.parseInt(args[1]);
        int diceNumber = Integer.parseInt(args[2]);

        if(diceNumber < 1 || diceNumber > 6){
            throw new InvalidDiceRollException();
        }
        
        if(!players.containsKey(player)){
            players.put(player, new Player(player, this.playerStartAmount));
        }
        
        return new PlayCommand(players.get(player),diceNumber);
    }
    
    private BoardCell generateCell(Bank bank, String parameters) throws InvalidCellTypeException {
        String[] args = parameters.split(";");
        int cellPosition = Integer.parseInt(args[1]);
        int cellType = Integer.parseInt(args[2]);

        int propertyType;
        int propertyValue;
        int rentValue;

        BoardCell cell;

        switch (cellType) {
            case 1:
                cell = new StartCell(cellPosition);
                break;
            case 2:
                cell = new PassTurnCell(cellPosition);
                break;
            case 3:
                propertyType = Integer.parseInt(args[3]);
                propertyValue = Integer.parseInt(args[4]);
                rentValue = Integer.parseInt(args[5]);

                cell = new PropertyCell(cellPosition,
                        PropertyType.values()[propertyType],
                        propertyValue,
                        rentValue,
                        bank);
                break;
            default:
                throw new InvalidCellTypeException();
        }

        return cell;
    }

    @Override
    public List<Command> generateCommandsList() throws InvalidDiceRollException, IOException{
        Scanner input = new Scanner(new File(this.diceRollsInput));
        ArrayList<Command> comms = new ArrayList<>();
        Command comm;
        String line = input.nextLine();
        String[] firstLine = line.split("%");
        this.playerStartAmount = Double.parseDouble(firstLine[2]);
        while (input.hasNextLine()) {
            line = input.nextLine();

            if(line.toUpperCase().equals("DUMP")){
                comm = new DumpCommand();
            }else{
                comm = generateDiceRoll(line);
            }
            comms.add(comm);
        }
        input.close();
        return comms;
    }

    @Override
    public Board generateBoard() throws InvalidCellTypeException, IOException {
        Scanner input = new Scanner(new File(this.boardInputAddr));
        ArrayList<BoardCell> cells = new ArrayList<>();
        String line = input.nextLine();
        while (input.hasNextLine()) {
            line = input.nextLine();
            BoardCell cell = generateCell(Bank.getInstanceOf(), line);
            cells.add(cell);
        }
        input.close();
        return new Board(cells, Bank.getInstanceOf());
    }

    @Override
    public Map<Integer, Player> generatePlayersList() throws InvalidCellTypeException, IOException {
        Scanner input = new Scanner(new File(this.diceRollsInput));
        String line = input.nextLine();
        String[] firstLine = line.split("%");
        this.playerStartAmount = Double.parseDouble(firstLine[2]);
        while (input.hasNextLine()) {
            line = input.nextLine();
            String[] args = line.split(";");
            
            if(args.length < 2){ // May be the DUMP command
                continue;
            }
            
            Integer player = Integer.parseInt(args[1]);

            if(!players.containsKey(player)){
                players.put(player, new Player(player, this.playerStartAmount));
            }
        }
        input.close();
        return this.players;
    }
}
