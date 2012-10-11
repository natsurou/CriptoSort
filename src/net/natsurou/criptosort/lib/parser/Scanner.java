/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.parser;

import net.natsurou.criptosort.lib.structures.SourcePosition;
import net.natsurou.criptosort.lib.structures.SourceFile;

/**
 *
 * @author 至朗
 */
public class Scanner {
    
    private SourceFile sourceFile;
    private boolean debug;
    
    private char currentChar;
    private StringBuffer currentSpelling;
    private boolean currentlyScanningToken;
    
    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    
    public Scanner(SourceFile source) {
        sourceFile = source;
        currentChar = sourceFile.getSource();
        debug = false;
    }
    
    public void enableDebugging() {
        debug = true;
    }
    
    // takeIt appends the current character to the current token, and gets
    // the next character from the source program.
    
    private void takeIt() {
        if (currentlyScanningToken) {
            currentSpelling.append(currentChar);
        }
        currentChar = sourceFile.getSource();
    }
    
    // scanSeparator skips a single separator.
    
    private void scanSeparator() {
        switch (currentChar) {
            case '!':
            {
                takeIt();
                while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT)) {
                    takeIt();
                }
            }
            break;
                
            case ' ': case '\r': case '\t':
                takeIt();
                break;
        }
    }
    
    private int scanToken() {
        
        switch (currentChar) {
            
                
            case '0':  case '1':  case '2':  case '3':  case '4':
            case '5':  case '6':  case '7':  case '8':  case '9':
                takeIt();
                while (isDigit(currentChar)) {
                    takeIt();
                }
                return Token.INTLITERAL;
                
            case ',':
                takeIt();
                return Token.COMMA;
                
            case '(':
                takeIt();
                return Token.LPAREN;
                
            case ')':
                takeIt();
                return Token.RPAREN;
                
            case SourceFile.EOL:
                takeIt();
                return Token.EOL;
                
            case SourceFile.EOT:
                return Token.EOT;
                
            default:
                takeIt();
                return Token.ERROR;
        }
    }
    
    public Token scan () {
        Token tok;
        SourcePosition pos;
        int kind;
        
        currentlyScanningToken = false;
        while (currentChar == '!'
                || currentChar == ' '
                || currentChar == '\r'
                || currentChar == '\t') {
            scanSeparator();
        }
        
        currentlyScanningToken = true;
        currentSpelling = new StringBuffer("");
        pos = new SourcePosition();
        pos.start = sourceFile.getCurrentLine();
        
        kind = scanToken();
        
        pos.finish = sourceFile.getCurrentLine();
        tok = new Token(kind, currentSpelling.toString(), pos);
        if (debug) {
            System.out.println(tok);
        }
        return tok;
    }
    
}
