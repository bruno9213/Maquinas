/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author bruno
 */
public class Historico implements Serializable{
    
    private ArrayList<String> data;
    private ArrayList<String> vel;

    public Historico(ArrayList<String> data, ArrayList<String> vel) {
        this.data = data;
        this.vel = vel;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getVel() {
        return vel;
    }

    
}
