/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microcity.metrics;

/**
 *
 * @author Erik
 */
public class Stopwatch {

    long start, end;
    
    public Stopwatch() {
    }

    public void start(){
        start = System.currentTimeMillis();
    }
    
    public void end(){
        end = System.currentTimeMillis();
    }
    
    public long time(){
        return end - start;
    }
}
