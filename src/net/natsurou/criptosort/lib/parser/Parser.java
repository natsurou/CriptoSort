/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.parser;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.natsurou.criptosort.lib.errors.SemanticError;
import net.natsurou.criptosort.lib.errors.SyntaxError;
import net.natsurou.criptosort.lib.structures.EncodedFile;
import net.natsurou.criptosort.lib.structures.Singlet;
import net.natsurou.criptosort.lib.structures.SourcePosition;

/**
 *
 * @author 至朗
 */
public class Parser {
    
    private Scanner lexicalAnalyser;
    private Token currentToken;
    private SourcePosition previousTokenPosition;
    private boolean moreToParse;
    private boolean newLine;
    
    public Parser(Scanner lexer) {
        lexicalAnalyser = lexer;
        newLine = false;
        moreToParse = true;
        previousTokenPosition = new SourcePosition();
    }
    
    private void acceptIt() {
        previousTokenPosition = currentToken.position;
        currentToken = lexicalAnalyser.scan();
    }
    
    public EncodedFile parseCode() {
        
        EncodedFile efile = new EncodedFile();
        
        previousTokenPosition.start = 0;
        previousTokenPosition.finish = 0;
        currentToken = lexicalAnalyser.scan();
        
        try {
            while (moreToParse) {                
                Singlet s = parseSinglet();
                if (s!=null) {
                    efile.addSinglet(s);
                }
                if (newLine) {
                    efile.addNewLine();
                    newLine = false;
                }
            }
        } catch (SyntaxError ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (SemanticError ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        return efile;
    }

    private Singlet parseSinglet() throws SyntaxError {
        Singlet s;
        int code = 0, wordPos = 0, linePos = 0;
        SourcePosition singletPos = new SourcePosition();
        
        singletPos.start = currentToken.position.start;
        
        if (currentToken.kind == Token.LPAREN) {
            acceptIt();
            if (currentToken.kind == Token.INTLITERAL) {
                code = Integer.parseInt(currentToken.spelling);
                acceptIt();
                if (currentToken.kind == Token.COMMA) {
                    acceptIt();
                    if (currentToken.kind == Token.INTLITERAL) {
                        wordPos = Integer.parseInt(currentToken.spelling);
                        acceptIt();
                        if (currentToken.kind == Token.COMMA) {
                            acceptIt();
                            if (currentToken.kind == Token.INTLITERAL) {
                                linePos = Integer.parseInt(currentToken.spelling);
                                acceptIt();
                                if (currentToken.kind == Token.RPAREN) {
                                    acceptIt();    
                                    if (currentToken.kind == Token.EOL){
                                        newLine = true;
                                        acceptIt();
                                    } else if (currentToken.kind == Token.EOT){
                                        moreToParse = false;
                                    }
                                } else { throw (new SyntaxError());}
                            } else { throw (new SyntaxError());}
                        } else { throw (new SyntaxError());}
                    } else { throw (new SyntaxError());}
                } else { throw (new SyntaxError());}
            } else { throw (new SyntaxError());}
        } else if (currentToken.kind == Token.EOL){
            newLine = true;
            acceptIt();
        } else if (currentToken.kind == Token.EOT){
            moreToParse = false;
            return null;
        } else { throw (new SyntaxError());}
        
        s = new Singlet(code, wordPos, linePos);
        
        return s;
    }
    
}
