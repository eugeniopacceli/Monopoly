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
public class InvalidCellTypeException extends Exception{
    public InvalidCellTypeException() {
        super("Invalid cell type read from file.");
    }
}
