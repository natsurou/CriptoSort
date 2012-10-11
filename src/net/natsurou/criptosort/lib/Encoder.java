/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib;

import net.natsurou.criptosort.lib.errors.SemanticError;
import net.natsurou.criptosort.lib.structures.EncodedFile;
import net.natsurou.criptosort.lib.structures.Singlet;
import net.natsurou.criptosort.lib.structures.SourceFile;

/**
 *
 * @author 至朗
 */
public class Encoder {
    private SourceFile source;
    private EncodedFile efile;
    private int currChar;
    private boolean alreadyWhiteSpace;
    private boolean beginningOfLine;
    private boolean emptyLine;
    
    public Encoder(SourceFile sourceFile) {
        source = sourceFile;
        efile = new EncodedFile();
    }
    
    public EncodedFile encode() throws SemanticError {
        int wordPos = 1, linePos = 1;
        beginningOfLine = true;
        alreadyWhiteSpace = false;
        emptyLine = true;
        while((currChar=source.getSource())!=SourceFile.EOT) {
            switch (currChar) {
                case ' ':
                    if (!alreadyWhiteSpace) {
                        wordPos = 1;
                        if (beginningOfLine) {
                            efile.addSinglet(new Singlet(currChar, 0, 1));
                            beginningOfLine = false;
                        } else {
                            efile.addSinglet(new Singlet(currChar, linePos, linePos+1));
                            linePos++;
                        }
                        if (emptyLine) {
                            emptyLine=false;
                        }
                        alreadyWhiteSpace = true;
                    }
                    break;
                case '\n':
                    beginningOfLine = true;
                    wordPos = 1;
                    linePos = 1;
                    if (emptyLine) {
                        efile.addSinglet(new Singlet(0,0,0));
                        efile.addNewLine();
                    } else {
                        efile.addNewLine();
                        emptyLine = true;
                    }
                    break;
                case '\r':
                    break;
                default:
                    alreadyWhiteSpace = false;
                    beginningOfLine = false;
                    emptyLine=false;
                    efile.addSinglet(new Singlet(currChar, wordPos, linePos));
                    wordPos++;
            }
        }
        return null;
    }
}
