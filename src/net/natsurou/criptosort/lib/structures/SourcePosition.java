/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort.lib.structures;

/**
 *
 * @author 至朗
 */
public class SourcePosition {

    public int start, finish;
    
    public SourcePosition () {
        start = 0;
        finish = 0;
    }
    
    public SourcePosition (int s, int f) {
        start = s;
        finish = f;
    }
    
    @Override
    public String toString() {
        return "(" + start + ", " + finish + ")";
    }
}
