package edu.monopoly.game.commands;

/**
 * An abstract class for a game command. A game command can be a play by some player,
 * or a GameEmulator command such as DUMP.
 */
public abstract class Command {
    protected CommandType commandType; // The command type.

    /**
     * The only constructor possible must receive the command type.
     * @param type CommandType of the command.
     */
    protected Command(CommandType type){
        this.commandType = type;
    }

    public CommandType getCommandType() {
        return commandType;
    }

}
