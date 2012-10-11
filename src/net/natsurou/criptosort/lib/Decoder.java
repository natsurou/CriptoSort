/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib;

import net.natsurou.criptosort.lib.structures.EncodedFile;
import net.natsurou.criptosort.lib.structures.Singlet;

/**
 *
 * @author 至朗
 */
public class Decoder {
    private java.util.LinkedList<String> lines;
    private EncodedFile efile;
    private String currLine;
    
    public Decoder(EncodedFile inputfile) {
        efile = inputfile;  
        lines = new java.util.LinkedList<>();
    }
    
    public void decode() {
        lines = new java.util.LinkedList<>(); 
        for (int i = 0; i < efile.getNumberOfLines(); i++) {
            currLine = "";
            if (efile.getArrayLine(i)[0].getCharCode()==0) {
                lines.add(currLine);
                continue;
            }
            char[][] temp = new char[efile.getLineWordNum(i)][efile.getLineMaxWordLength(i)];
            for (Singlet singlet : efile.getArrayLine(i)) {
                temp[singlet.getPositionInLine()-1][singlet.getPositionInWord()-1] 
                        = (char) singlet.getCharCode();
            }
            for (int j = 0; j < temp.length; j++) {
                char[] cs = temp[j];
                for (int k = 0; k < cs.length; k++) {
                    if (cs[k] == 0) {
                        break;
                    }
                    currLine+=cs[k];
                }
                currLine+=' ';
            }
            lines.add(currLine);
        }
    }
    
    public String getLine(int line) {
        return lines.get(line);
    }
    
    public int getNumberOfLines() {
        return lines.size();
    }
}
