/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.parser;

import net.natsurou.criptosort.lib.structures.SourcePosition;

/**
 *
 * @author 至朗
 */
public class Token {
    
    protected int kind;
    protected String spelling;
    protected SourcePosition position;
    
    public Token(int kind, String spelling, SourcePosition position) {
        
        this.kind = kind;
        this.spelling = spelling;
        this.position = position;
        
    }
    
    public static String spell (int kind) {
        return tokenTable[kind];
    }
    
    @Override
    public String toString() {
        return "Kind=" + kind + ", spelling=" + spelling +
                ", position=" + position;
    }
    
    // Token classes...
    
    public static final int
            
            // literals, identifiers, operators...
            INTLITERAL	= 0,
            
            // punctuation...
            COMMA	= 1,
            
            // brackets...
            LPAREN	= 2,
            RPAREN	= 3,
            
            // special tokens...
            EOL         = 4,
            EOT		= 5,
            ERROR	= 6;
    
    private static String[] tokenTable = new String[] {
        "<int>",
        ",",
        "(",
        ")",
        "\n",
        "",
        "<error>"
    };
    
}
