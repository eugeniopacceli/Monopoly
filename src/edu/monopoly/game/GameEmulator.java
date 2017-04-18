package edu.monopoly.game;

import edu.monopoly.game.commands.Command;
import edu.monopoly.game.commands.PlayCommand;
import edu.monopoly.game.actors.Player;
import edu.monopoly.exceptions.InsufficientGameInformationException;
import edu.monopoly.exceptions.UnexpectedNegativeNumberException;
import edu.monopoly.game.actors.Bank;
import edu.monopoly.game.board.Board;
import edu.monopoly.game.board.cells.BoardCell;
import edu.monopoly.game.board.cells.PassTurnCell;
import edu.monopoly.game.board.cells.PropertyCell;
import edu.monopoly.game.board.cells.StartCell;
import edu.monopoly.game.commands.CommandType;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class is responsible for executing the game instance.
 * Receives the input read by GameReader, runs the game and prints the
 * statistics to an output also received by constructor.
 */
public class GameEmulator {

    private Map<String, Player> players; // A map (id -> player) of players in the game
    private List<Command> comms; // List of game commands (plays and/or DUMP)
    private Board board; // The game board
    private PrintStream output; // The output stream, where all the information to be print are sent to
    private int commandsExecuted; // For the statistics, number of instructions executed so far

    /** An empty constructor, user shall use the setters or else an InsufficientGameInformationException
    * will occur
    */
    public GameEmulator() {
        players = null;
        comms = null;
        board = null;
        output = null;
        commandsExecuted = 0;
    }

    /**
     * An constructor which receives all the necessary information for the game to be executed.
     * @param players Map of players to participate in the match (id -> player)
     * @param comms All the game commands
     * @param board The game board
     * @param output The output stream that will receive the statistics
     */
    public GameEmulator(Map<String, Player> players, List<Command> comms, Board board, PrintStream output) {
        this.players = players;
        this.comms = comms;
        this.board = board;
        this.output = output;
    }

    /**
     * A function that checks if the game has a winner at a given time.
     * @return true if there is a winner in the game, false if there are more or 0 players active.
     */
    public boolean hasWinner() {
        int playersActive = 0;

        for (Player player : this.players.values()) {
            if (player.isActive()) {
                playersActive++;
            }
        }

        return playersActive == 1;
    }

    /**
     * Prints all the game current statistics to a string
     * @return a string according to the specifications of TP1.pdf
     */
    public String generateStatistics() {
        StringBuilder sb = new StringBuilder(); // A string builder, to be used when there is much to append to a string
        StringBuilder statsLaps = new StringBuilder(); // Those are more efficient than '+' for a string, according to the Java docs 
        StringBuilder statsMoney = new StringBuilder();
        StringBuilder statsRentReceived = new StringBuilder();
        StringBuilder statsRentPaid = new StringBuilder();
        StringBuilder statsPropertyBuyed = new StringBuilder();
        StringBuilder statsPassTurn = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#0.00"); // Formats the output to the desired number format (dd.dd)
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US)); // We are using '.' instead of ','
        sb.append("1:");
        sb.append((int)Math.ceil((double)commandsExecuted / this.getPlayers().size())).append('\n'); // Approximate number of matches played
        for(Player p : this.getPlayers().values()){ // Computes each question of the statistics for each game player at a time.
            statsLaps.append(p.getId()).append('-').append(p.getStatsLaps()).append(';');
            statsMoney.append(p.getId()).append('-').append(df.format(p.getMoney())).append(';');
            statsRentReceived.append(p.getId()).append('-').append(df.format(p.getStatsRentReceived())).append(';');
            statsRentPaid.append(p.getId()).append('-').append(df.format(p.getStatsRentPaid())).append(';');
            statsPropertyBuyed.append(p.getId()).append('-').append(df.format(p.getStatsPropertyBuyed())).append(';');
            statsPassTurn.append(p.getId()).append('-').append(p.getStatsSkipTurn()).append(';');
        }
        statsLaps.deleteCharAt(statsLaps.lastIndexOf(";")); // There is no ';' at the end of each line, so let's fix it
        statsMoney.deleteCharAt(statsMoney.lastIndexOf(";"));
        statsRentReceived.deleteCharAt(statsRentReceived.lastIndexOf(";"));
        statsRentPaid.deleteCharAt(statsRentPaid.lastIndexOf(";"));
        statsPropertyBuyed.deleteCharAt(statsPropertyBuyed.lastIndexOf(";"));
        statsPassTurn.deleteCharAt(statsPassTurn.lastIndexOf(";"));
        
        sb.append("2:").append(statsLaps.toString()).append('\n');
        sb.append("3:").append(statsMoney.toString()).append('\n');
        sb.append("4:").append(statsRentReceived.toString()).append('\n');
        sb.append("5:").append(statsRentPaid.toString()).append('\n');
        sb.append("6:").append(statsPropertyBuyed.toString()).append('\n');
        sb.append("7:").append(statsPassTurn.toString());

        return sb.toString();
    }
    
    /**
     * Bootstraps the game emulator. Finds the start cell and puts all the players there
     * @throws InsufficientGameInformationException 
     */
    private void configureInternalGameStart() throws InsufficientGameInformationException{
        BoardCell start = null;
        if(this.players == null || this.comms == null || this.board == null){
            throw new InsufficientGameInformationException();
        }
        for(BoardCell c : board.getCells()){
            if(c instanceof StartCell){
                start = c;
                break;
            }
        }
        if(start == null){
            throw new InsufficientGameInformationException();
        }
        for(Player p : players.values()){
            p.setBoardcell(start);
        }
    }

    /**
     * This function is responsible to print a string to the desired output (received by constructor or setter)
     * @param toPrint a string to print to the received output
     * @throws InsufficientGameInformationException 
     */
    private void printToOutput(String toPrint) throws InsufficientGameInformationException{
        if(output == null){
            throw new InsufficientGameInformationException();
        }
        output.println(toPrint);
    }

    /**
     * This is an auxiliary function used to compute if a given player has passed by the start cell, therefore
     * completing a lap, for a dice roll, or not. If yes, according to game rules, it should be rewarded some money, and
     * the laps statistic must be updated.
     * @param player the player
     * @param oldCellPos old player location
     * @param diceRoll number rolled by the player
     * @throws UnexpectedNegativeNumberException 
     */
    private void verifyAndComputeLaps(Player player, int oldCellPos , int diceRoll) throws UnexpectedNegativeNumberException{
        List<BoardCell> cells = this.getBoard().getCells();
        boolean needsTeleport = (oldCellPos + diceRoll) >= cells.size();
        int playerNewCellPosition = (oldCellPos + diceRoll) % cells.size();

        if(needsTeleport){ // The list is not ciclic but the nature of the board is, so we must check at the beginning
                           // and end of the cells list if the player step is too big this round (the player goes back to the
                           // beginning of the list if it was at the end of it and rolled a sufficient dice number).
            BoardCell pivot;
            for(int i = oldCellPos + 1; i < cells.size(); i++){
               pivot = cells.get(i);
               if(pivot instanceof StartCell){
                    player.incrementStatsLaps();
                    player.addAmount(((StartCell) pivot).getPassValue());
               }
            }
            for(int i = 0; i <= playerNewCellPosition ; i++){
               pivot = cells.get(i);
               if(pivot instanceof StartCell){
                    player.incrementStatsLaps();
                    player.addAmount(((StartCell) pivot).getPassValue());
               }
            }
        }else{ // The cells ahead in the list are enough to land the player for this dice roll
            BoardCell pivot;
            for(int i = oldCellPos + 1; i <= oldCellPos + diceRoll; i++){
               pivot = cells.get(i);
               if(pivot instanceof StartCell){
                    player.incrementStatsLaps();
                    player.addAmount(((StartCell) pivot).getPassValue());
               }
            }
        }
    }
    
    /**
     * Given a player dice roll (next move), computes where it lands and what is to be done
     * according to the game's rules and the board cell the player will land in.
     * @param player the player
     * @param diceRoll number rolled by the player
     * @throws UnexpectedNegativeNumberException 
     */
    private void computePlayerMove(Player player, int diceRoll) throws UnexpectedNegativeNumberException{
        if(!player.isActive()){
            return; // Player already lost, does nothing then.
        }

        List<BoardCell> cells = this.getBoard().getCells();
        BoardCell oldCell = player.getBoardcell();
        int oldCellPos = cells.indexOf(oldCell);
        int playerNewCellPosition = (oldCellPos + diceRoll) % cells.size(); // The cell list should be ciclic and sorted, mod fixes our list representation.
        BoardCell newCell = cells.get(playerNewCellPosition);
        player.setBoardcell(newCell);
        
        verifyAndComputeLaps(player, oldCellPos, diceRoll); // Checks if the player completed a lap (to possibly receive a bonus)

        if(newCell instanceof PropertyCell){ // If a cell is a property cell, the player shall buy it, rent it, or do nothing
                                             // according to the game's rules
            PropertyCell propertyCell = (PropertyCell)newCell;
            if(propertyCell.getOwner() != player){
                if(propertyCell.getOwner() == Bank.getInstanceOf() && player.getMoney() >= propertyCell.getBuyValue()){
                    player.buyProperty(propertyCell); // Cell belongs to the bank, and the player has money to buy it, so the player must buy it
                } else if(propertyCell.getOwner() != Bank.getInstanceOf()){
                    player.payRentTo(propertyCell); // Cell belongs to other player, player shall pay the rent or lose the game
                }
            } // else if (propertyCell.getOwner() == player) nothing happens
        } else if(newCell instanceof PassTurnCell){
            player.incrementStatsSkipTurn(); // Bad luck
        }
    }

    /**
     * Begins the game execution sequence, given the sufficient information.
     * @throws InsufficientGameInformationException if a required object is null
     * @throws UnexpectedNegativeNumberException if a game transaction contains an invalid number
     */
    public void play() throws InsufficientGameInformationException, UnexpectedNegativeNumberException {
        this.configureInternalGameStart();
        this.commandsExecuted = 0;
        // [GAME LOOP]
        for(Command c : comms){ // for each command read by GameReaderImpl and passed to us
            if(this.hasWinner() || c.getCommandType().equals(CommandType.DUMP)){
                break; // Game ended or DUMP was received, stalls the machine
            }else if(c.getCommandType().equals(CommandType.DICEROLL)){ // Player rolls a dice
                PlayCommand play = (PlayCommand)c;
                this.computePlayerMove(play.getPlayer(), play.getNumber()); // Computes the consequences 
                this.commandsExecuted++;
            }
        }
        printToOutput(this.generateStatistics()); // At the end of the game, generates statistics.
    }

    /**
     * Setters and getters
     */

    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<Command> getComms() {
        return comms;
    }

    public Board getBoard() {
        return board;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

    public int getCommandsExecuted() {
        return commandsExecuted;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public void setComms(List<Command> comms) {
        this.comms = comms;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCommandsExecuted(int commandsExecuted) {
        this.commandsExecuted = commandsExecuted;
    }
}
