package edu.monopoly.app;

import edu.monopoly.gameboard.Board;

public class Main
{
    public static final String BOARDPATH = "\\home\\grad\\ccomp\\14\\danielvsc\\Desktop\\tabuleiro.txt";
    public static final String PLAYINGPATH = "\\home\\grad\\ccomp\\14\\danielvsc\\Desktop\\jogadores.txt";
	
    public static void main(String[] args)
    {	
	    Board board = new Board("tabuleiro.txt");	
        Gameplay gameplay = new Gameplay(PLAYINGPATH);       
        gameplay.play(board);    
        board.printBoard(System.out);
    }
	
}
