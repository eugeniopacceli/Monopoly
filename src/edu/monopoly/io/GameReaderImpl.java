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
 * Implementation of GameReader
 */
public class GameReaderImpl implements GameReader{
    protected String boardInputAddr; // Input file address
    protected String diceRollsInput; // Input file address
    protected HashMap<String, Player> players; // Players map
    protected double playerStartAmount; // Initial amount of money for the players

    /**
     * Empty and convenient constructor
     */

    public GameReaderImpl(){
        this.boardInputAddr = this.diceRollsInput = null;
        players = new HashMap<>();
        playerStartAmount = 0.0;
    }
    
    public GameReaderImpl(String boardInputAddr, String diceRollsInput) {
        this.setSources(boardInputAddr, diceRollsInput);
        players = new HashMap<>();
        playerStartAmount = 0.0;
    }
    
    @Override
    public void setSources(String boardInputAddr, String diceRollsInput) {
        this.boardInputAddr = boardInputAddr;
        this.diceRollsInput = diceRollsInput;
    }

    /**
     * Receives a dice roll (player move) line from the input file, processes it,
     * and generates a PlayCommand accordingly.
     * @param parameters the line read from file
     * @return An equivalent PlayCommand
     * @throws InvalidDiceRollException for invalid dice number
     */
    private PlayCommand generateDiceRoll(String parameters) throws InvalidDiceRollException {
        String[] args = parameters.split(";"); // Separates the string by ';'
        String playerId = args[1];
        int diceNumber = Integer.parseInt(args[2]);

        if(diceNumber < 1 || diceNumber > 6){
            throw new InvalidDiceRollException();
        }
        
        if(!players.containsKey(playerId)){
            players.put(playerId, new Player(playerId, this.playerStartAmount));
        }
        
        return new PlayCommand(players.get(playerId),diceNumber);
    }
    
    /**
     * Receives a cell line from an board input file line, generates a BoardCell accordingly.
     * @param bank the bank of the game
     * @param parameters the cell line from the input file
     * @return an BoardCell equivalent to the line read
     * @throws InvalidCellTypeException for invalid cell type received from file
     */
    private BoardCell generateCell(Bank bank, String parameters) throws InvalidCellTypeException {
        String[] args = parameters.split(";"); // Separates the string by ';'
        int cellPosition = Integer.parseInt(args[1]);
        int cellType = Integer.parseInt(args[2]);
        int propertyType;
        double propertyValue;
        double rentValue;

        BoardCell cell;

        switch (cellType) {
            case 1:
                cell = new StartCell(cellPosition);
                break;
            case 2:
                cell = new PassTurnCell(cellPosition);
                break;
            case 3:
                propertyType = Integer.parseInt(args[3]); // Remaining info from the line if the cell
                propertyValue = Double.parseDouble(args[4]); // is a PropertyCell (according to TP1.pdf)
                rentValue = Double.parseDouble(args[5]);

                cell = new PropertyCell(cellPosition,
                        PropertyType.values()[propertyType],
                        propertyValue,
                        (rentValue/100)*propertyValue, // Rent is in % of the total value
                        bank);
                break;
            default:
                throw new InvalidCellTypeException();
        }

        return cell;
    }

    /**
     * Given the dice rolls input file, read all of it and generates all the commands accordingly.
     * @return A List of Commands, representing each line of the received file
     * @throws InvalidDiceRollException for invalid dice number
     * @throws IOException for invalid file
     */
    @Override
    public List<Command> generateCommandsList() throws InvalidDiceRollException, IOException{
        Scanner input = new Scanner(new File(this.diceRollsInput));
        ArrayList<Command> comms = new ArrayList<>();
        Command comm;
        String line = input.nextLine();
        String[] firstLine = line.split("%"); // Separates the string by '%'
        this.playerStartAmount = Double.parseDouble(firstLine[2]); // Extracts the start amount from the first line
        while (input.hasNextLine()) {
            line = input.nextLine();

            if(line.toUpperCase().equals("DUMP")){
                comm = new DumpCommand();
            }else{
                comm = generateDiceRoll(line);
            }
            comms.add(comm);
        }
        input.close(); // Closes the input reader
        return comms;
    }

    /**
     * Given the board input file, read all of it, and builds the board object, along with all of it's cells
     * @return A Board object populated by a List of BoardCell's
     * @throws InvalidCellTypeException for invalid cell type read from file
     * @throws IOException for invalid file
     */
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
        cells.sort((a,b) -> { return a.getPosition() - b.getPosition();}); // Sorts the cells list
                                                                           // according to their game position,
                                                                           // so they match.
        input.close(); // Closes the input reader
        return new Board(cells, Bank.getInstanceOf());
    }

    /**
     * Given the dice rolls input file, generates the Players map (id -> player).
     * Extracts an player id for each dice roll and creates it, if not yet created.
     * @return a map of players, by id.
     * @throws IOException for invalid received file
     */
    @Override
    public Map<String, Player> generatePlayersList() throws IOException {
        Scanner input = new Scanner(new File(this.diceRollsInput));
        String line = input.nextLine();
        String[] firstLine = line.split("%");  // Separates the string by '%'
        this.playerStartAmount = Double.parseDouble(firstLine[2]); // Extracts the start amount from the first line
        while (input.hasNextLine()) {
            line = input.nextLine();
            String[] args = line.split(";"); // Separates the string by ';'
            
            if(args.length < 2){ // May be the DUMP command
                continue;
            }
            
            String playerId = args[1];

            if(!players.containsKey(playerId)){ // Adds the player to the map if it is not known yet
                players.put(playerId, new Player(playerId, this.playerStartAmount));
            }
        }
        input.close(); // Closes the input reader
        return this.players;
    }
}
