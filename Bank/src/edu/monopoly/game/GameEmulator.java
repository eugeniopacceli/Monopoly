package edu.monopoly.game;

import edu.monopoly.game.commands.Command;
import edu.monopoly.game.commands.PlayCommand;
import edu.monopoly.game.actors.Player;
import edu.monopoly.exceptions.InsufficientGameInformationException;
import edu.monopoly.game.board.Board;
import edu.monopoly.game.board.cells.BoardCell;
import java.util.List;
import java.util.Map;

public class GameEmulator {

    private Map<Integer, Player> players;
    private List<Command> comms;
    private Board board;

    public GameEmulator() {
        players = null;
        comms = null;
        board = null;
    }

    public GameEmulator(Map<Integer, Player> players, List<Command> comms, Board board) {
        this.players = players;
        this.comms = comms;
        this.board = board;
    }

    private void payRent(double value, int playerSourceId, int playerTargetId) {
        Player source = this.players.get(playerSourceId);
        Player target = this.players.get(playerTargetId);

        double currentMoney = source.getMoney();
        double newMoney = currentMoney - value;
        source.setMoney(newMoney);

        // Se a grana não for suficiente, apenas o que resta irá para
        // o jogador alvo do pagamento
        if (source.getMoney() < 0) {
            target.setMoney(target.getMoney() + currentMoney);
        } else {
            target.setMoney(target.getMoney() + value);
        }
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
        for(BoardCell c : this.getBoard().getCells()){
            sb.append(c.toString());
            sb.append("\n");
        }
        for(Player p : this.getPlayers().values()){
            sb.append(p.getId() + " " + p.getMoney());
            sb.append("\n");
        }
        for(Command c : this.getComms()){
            sb.append(c.getCommandType());
            if(c instanceof PlayCommand){
                sb.append(" ");
                sb.append(((PlayCommand)c).getPlayer().getId());
                sb.append(" rolled a ");
                sb.append(((PlayCommand)c).getNumber());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void play() throws InsufficientGameInformationException {
        if(this.players == null || this.comms == null || this.board == null){
            throw new InsufficientGameInformationException();
        }
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, Player> players) {
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

}
