package edu.monopoly.app;

import java.util.HashMap;

import edu.monopoly.actors.Player;
import edu.monopoly.gameboard.Board;

public class Gameplay
{    
    private HashMap<Integer, Player> players; 

    public Gameplay(String playPath)
    {
       this.players = new HashMap<Integer, Player>();
    }

    public Player getPlayer(int playerId)
    {    	
        return this.players.get(playerId);
    }

    public void payRent(double value,int playerSourceId, int playerTargetId)
    {
        Player source = this.getPlayer(playerSourceId);
        Player target = this.getPlayer(playerTargetId);

        double currentMoney = source.getMoney();
        double newMoney = currentMoney - value;
        source.setMoney(newMoney);
        
        // Se a grana não for suficiente, apenas o que resta irá para
        // o jogador alvo do pagamento
        if(source.getMoney() < 0)        
            target.setMoney(target.getMoney() + currentMoney);        
        else
            target.setMoney(target.getMoney() + value);
    }

    public void startPass(int playerId, double value)
    {
    	Player target = this.getPlayer(playerId);
    	double currentMoney = target.getMoney();
        target.setMoney(currentMoney + value);
    }

    public boolean hasWinner()
    {
        int playersActive = 0;
        
        for(Player player: this.players.values())        
        	if(player.isActive())
        		playersActive++;

        return playersActive == 1;
    }
    
    public void play(Board board)
    {
    	
    }
}
