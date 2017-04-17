/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.monopoly.exceptions;

/**
 *
 * @author eugen
 */
public class InsufficientGameInformationException extends Exception {

    public InsufficientGameInformationException(){
        super("Game Emulator lacks information to start a game.");
    }
}
