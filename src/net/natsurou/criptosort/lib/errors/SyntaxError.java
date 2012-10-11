/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.errors;

/**
 *
 * @author 至朗
 */
public class SyntaxError extends Exception {
    
    public SyntaxError() {
        super();
    };
    
    public SyntaxError (String s) {
        super(s);
    }
}
