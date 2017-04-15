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
public class UnexpectedNegativeNumberException extends Exception {
    
    public UnexpectedNegativeNumberException(){
        super("A negative number appeard into one of the game's transactions.");
    }
}
