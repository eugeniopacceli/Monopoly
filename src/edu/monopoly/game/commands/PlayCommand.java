package edu.monopoly.game.commands;

import edu.monopoly.game.actors.Player;

/**
 * Specialization of Command. Represents a player move (dice roll) command.
 */
public class PlayCommand extends Command{
    protected Player player; // The player making it's move
    protected int number; // The number rolled in the dice

    /**
     * Constructors
     * 
     */

    public PlayCommand(){
        super(CommandType.DICEROLL);
    }
    
    public PlayCommand(Player player, int number){
        super(CommandType.DICEROLL);
        this.player = player;
        this.number = number;
    }
    
    /**
     * Setters and getters
     *
     */

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
