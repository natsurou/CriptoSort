/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.structures;

import java.io.Serializable;

/**
 *
 * @author 至朗
 */
public class Singlet implements Serializable {
    
    private int charCode, positionInWord, positionInLine;
    
    public Singlet(int code, int wordPos, int linePos) {
        charCode = code;
        positionInWord = wordPos;
        positionInLine = linePos;
    }
    
    @Override
    public String toString() {
        return "("+ charCode + ", " + positionInWord + ", " + positionInLine + ")"; 
    }
    
    public int getCharCode() {
        return charCode;
    }
    
    public int getPositionInWord() {
        return positionInWord;
    }
    
    public int getPositionInLine() {
        return positionInLine;
    }
}
