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
public class Dados implements Serializable{
    
    private String name, local,id, n_vias, n_sent;
    private ArrayList<String> data, data2;
    private ArrayList<String> vel, vel2;
    
    //Historico
    
    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getVel() {
        return vel;
    }
    
    public ArrayList<String> getData2() {
        return data2;
    }

    public ArrayList<String> getVel2() {
        return vel2;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
      
    public void setVel(ArrayList<String> vel) {
        this.vel = vel;
    }
    
    public void setData2(ArrayList<String> data2) {
        this.data2 = data2;
    }
      
    public void setVel2(ArrayList<String> vel2) {
        this.vel2 = vel2;
    }
    
    //Radar
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocal() {
        return local;
    }

    public String getN_vias() {
        return n_vias;
    }

    public String getN_sent() {
        return n_sent;
    }

    public void setRadarData(String id, String name, String local, String n_vias, String n_sent) {
        this.id = id;
        this.name = name;
        this.local = local;
        this.n_vias = n_vias;
        this.n_sent = n_sent;
    }
    
    
}
