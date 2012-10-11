/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.errors;

/**
 *
 * @author 至朗
 */
public class SemanticError extends Exception {
    
    public SemanticError() {
        super();
    };
    
    public SemanticError (String s) {
        super(s);
    }
}

