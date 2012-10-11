/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.structures;

import net.natsurou.criptosort.lib.errors.SemanticError;

/**
 *
 * @author 至朗
 */
public class EncodedFile {
    private int numberOfLines;
    private java.util.ArrayList<java.util.LinkedHashSet<Singlet>> lines;
    private java.util.LinkedHashSet<Singlet> currLine;
    
    public EncodedFile() {
        numberOfLines = 1;
        currLine = new java.util.LinkedHashSet<>(16);
        lines = new java.util.ArrayList<>(10);
        lines.add(currLine);
    }
    
    public void addSinglet(Singlet s) throws SemanticError {
        
        for (Singlet singlet : currLine) {
            if ((singlet.getCharCode()!=32) && (s.getCharCode()!=32) 
                    && (s.getPositionInLine()==singlet.getPositionInLine())
                    && (s.getPositionInWord()==singlet.getPositionInWord())) {
                
                throw (new SemanticError()) ;
            }
        }
 
        currLine.add(s);
        System.out.print(s);
    }
    
    public void addNewLine() {
        currLine = new java.util.LinkedHashSet<>(16);
        lines.add(currLine);
        System.out.println();
        numberOfLines++;
    }
    
    public int getNumberOfLines() {
        return numberOfLines;
    }
    
    public Singlet[] getArrayLine(int line) {
        return lines.get(line).toArray(new Singlet[0]);
    }
}
