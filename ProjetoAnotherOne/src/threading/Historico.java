/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.Serializable;

/**
 *
 * @author bruno
 */
public class Historico implements Serializable{
    
    private String data, vel;

    public Historico(String data, String vel) {
        this.data = data;
        this.vel = vel;
    }

    public String getData() {
        return data;
    }

    public String getVel() {
        return vel;
    }

    
}
