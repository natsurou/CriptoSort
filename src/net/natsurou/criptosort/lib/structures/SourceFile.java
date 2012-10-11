/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.structures;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 至朗
 */
public class SourceFile {
    
    public static final char EOL = '\n';
    public static final char EOT = '\u0000';

    java.io.File sourceFile;
    java.io.InputStream source;
    int currentLine;
    
    public SourceFile(String filename) {
        
        try {
            sourceFile = new java.io.File(filename);
            source = new java.io.FileInputStream(sourceFile);
            currentLine = 1;
        } catch (FileNotFoundException ex) {
            sourceFile = null;
            source = null;
            currentLine = 0;
            Logger.getLogger(SourceFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public char getSource() {
        try {
            int c = source.read();
            
            if (c == -1) {
                c = EOT;
            } else if (c == EOL) {
                currentLine++;
            }
            return (char) c;
        }
        catch (java.io.IOException s) {
            return EOT;
        }
    }
    
    public int getCurrentLine() {
        return currentLine;
    }
}
