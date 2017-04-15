/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.monopoly.game.commands;

/**
 *
 * @author eugen
 */
public abstract class Command {
    protected CommandType commandType;

    protected Command(CommandType type){
        this.commandType = type;
    }

    public CommandType getCommandType() {
        return commandType;
    }

}
