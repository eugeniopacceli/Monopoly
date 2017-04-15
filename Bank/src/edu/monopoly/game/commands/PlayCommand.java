/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.monopoly.game.commands;

import edu.monopoly.game.actors.Player;

/**
 *
 * @author eugen
 */
public class PlayCommand extends Command{
    protected Player player;
    protected int number;

    public PlayCommand(){
        super(CommandType.DICEROLL);
    }
    
    public PlayCommand(Player player, int number){
        super(CommandType.DICEROLL);
        this.player = player;
        this.number = number;
    }
    
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
