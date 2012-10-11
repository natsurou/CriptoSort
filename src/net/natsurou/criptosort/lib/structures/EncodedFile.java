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
    private java.util.ArrayList<Line> lines;
    private Line currLine;
    private boolean endOfFile;
    
    public EncodedFile() {
        endOfFile = false;
        numberOfLines = 1;
        currLine = new Line();
        lines = new java.util.ArrayList<>(10);
        lines.add(currLine);
    }
    
    public void addSinglet(Singlet s) throws SemanticError {
        currLine.add(s);
    }
    
    public void addNewLine() {
        currLine = new Line();
        lines.add(currLine);
        numberOfLines++;
    }
    
    public int getNumberOfLines() {
        return numberOfLines;
    }
    
    public Singlet[] getArrayLine(int line) {
        return lines.get(line).toArray();
    }
    
    public int getLineWordNum(int line) {
        return lines.get(line).numberOfWords;
    }
    
    public int getLineMaxWordLength(int line) {
        return lines.get(line).maxWordLength;
    }

    public void addEOF() throws SemanticError {
        endOfFile = true;
        if (currLine.singlets.isEmpty()) currLine.add(new Singlet(0, 0, 0));
    }

    private static class Line {
        private java.util.LinkedHashSet<Singlet> singlets;
        protected int numberOfWords, maxWordLength;
        
        public Line() {
            singlets = new java.util.LinkedHashSet<>(16);
            numberOfWords = 0;
            maxWordLength = 0;
        }

        private Singlet[] toArray() {
            System.out.println(singlets.size());
            return singlets.toArray(new Singlet[0]);
        }

        private void add(Singlet s) throws SemanticError {
            for (Singlet singlet : singlets) {
                if ((singlet.getCharCode()!=32) && (s.getCharCode()!=32) 
                        && (s.getPositionInLine()==singlet.getPositionInLine())
                        && (s.getPositionInWord()==singlet.getPositionInWord())) {                    
                    throw (new SemanticError()) ;
                }
            }
            if (s.getPositionInLine() > numberOfWords) {
                numberOfWords++;
            }            
            if (s.getPositionInWord() > maxWordLength) {
                maxWordLength++;
            }
            singlets.add(s);
        }
    }
    
    public StringBuilder dump() {
        StringBuilder string = new StringBuilder();
        for (Line line : lines) {
            for (Singlet singlet : line.singlets) {
                string.append(singlet);
            }
            string.append("\n");
        }
        return string;
    }
}
