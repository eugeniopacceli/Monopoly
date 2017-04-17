package edu.monopoly.game;

import com.sun.webkit.BackForwardList;
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

public class GameEmulator {

    private Map<String, Player> players;
    private List<Command> comms;
    private Board board;
    private PrintStream output;

    public GameEmulator() {
        players = null;
        comms = null;
        board = null;
        output = null;
    }

    public GameEmulator(Map<String, Player> players, List<Command> comms, Board board, PrintStream output) {
        this.players = players;
        this.comms = comms;
        this.board = board;
        this.output = output;
    }

    public boolean hasWinner() {
        int playersActive = 0;

        for (Player player : this.players.values()) {
            if (player.isActive()) {
                playersActive++;
            }
        }

        return playersActive == 1;
    }

    public String generateStatistics() {
        StringBuilder sb = new StringBuilder();
        StringBuilder statsLaps = new StringBuilder();
        StringBuilder statsMoney = new StringBuilder();
        StringBuilder statsRentReceived = new StringBuilder();
        StringBuilder statsRentPaid = new StringBuilder();
        StringBuilder statsPropertyBuyed = new StringBuilder();
        StringBuilder statsPassTurn = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        int commands = 0;
        sb.append("1:");
        for(Command c : this.getComms()){
            if(c.getCommandType().equals(CommandType.DICEROLL)){
                commands++;
            }
        }
        sb.append(commands / this.getPlayers().size()).append('\n');
        for(Player p : this.getPlayers().values()){
            statsLaps.append(p.getId()).append('-').append(p.getStatsLaps()).append(';');
            statsMoney.append(p.getId()).append('-').append(df.format(p.getMoney())).append(';');
            statsRentReceived.append(p.getId()).append('-').append(df.format(p.getStatsRentReceived())).append(';');
            statsRentPaid.append(p.getId()).append('-').append(df.format(p.getStatsRentPaid())).append(';');
            statsPropertyBuyed.append(p.getId()).append('-').append(df.format(p.getStatsPropertyBuyed())).append(';');
            statsPassTurn.append(p.getId()).append('-').append(p.getStatsSkipTurn()).append(';');
        }
        statsLaps.deleteCharAt(statsLaps.lastIndexOf(";"));
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

    private void printToOutput(String toPrint) throws InsufficientGameInformationException{
        if(output == null){
            throw new InsufficientGameInformationException();
        }
        output.println(toPrint);
    }

    private void verifyAndComputeLaps(Player player, int oldCellPos , int diceRoll) throws UnexpectedNegativeNumberException{
        List<BoardCell> cells = this.getBoard().getCells();
        boolean needsTeleport = (oldCellPos + diceRoll) >= cells.size();
        int playerNewCellPosition = (oldCellPos + diceRoll) % cells.size();

        if(needsTeleport){
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
        }else{
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
    
    private void computePlayerMove(Player player, int diceRoll) throws UnexpectedNegativeNumberException{
        if(!player.isActive()){
            return;
        }

        List<BoardCell> cells = this.getBoard().getCells();
        BoardCell oldCell = player.getBoardcell();
        int oldCellPos = cells.indexOf(oldCell);
        int playerNewCellPosition = (oldCellPos + diceRoll) % cells.size();
        BoardCell newCell = cells.get(playerNewCellPosition);
        player.setBoardcell(newCell);
        
        verifyAndComputeLaps(player, oldCellPos, diceRoll);

        if(newCell instanceof PropertyCell){
            PropertyCell propertyCell = (PropertyCell)newCell;
            if(propertyCell.getOwner() != player){
                if(propertyCell.getOwner() == Bank.getInstanceOf() && player.getMoney() >= propertyCell.getBuyValue()){
                    player.buyProperty(propertyCell);
                } else if(propertyCell.getOwner() != Bank.getInstanceOf()){
                    player.payRentTo(propertyCell);
                }
            } // else if (propertyCell.getOwner() == player) nothing happens
        } else if(newCell instanceof PassTurnCell){
            player.incrementStatsSkipTurn();
        }
    }
    
    
    public void play() throws InsufficientGameInformationException, UnexpectedNegativeNumberException {
        this.configureInternalGameStart();

        for(Command c : comms){
            if(c.getCommandType().equals(CommandType.DUMP)){
                printToOutput(this.generateStatistics());
                break;
            }else if(c.getCommandType().equals(CommandType.DICEROLL)){
                PlayCommand play = (PlayCommand)c;
                this.computePlayerMove(play.getPlayer(), play.getNumber());
            }
        }
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public List<Command> getComms() {
        return comms;
    }

    public void setComms(List<Command> comms) {
        this.comms = comms;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

}
