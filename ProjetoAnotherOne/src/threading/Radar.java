package threading;

import java.io.Serializable;


public class Radar implements Serializable{
    
    private String name, local,id, n_vias, n_sent;
    
    public Radar(String id, String name, String local, String n_vias, String n_sent){
        this.id = id;
        this.name = name;
        this.local = local;
        this.n_vias = n_vias;
        this.n_sent = n_sent;       
    }

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
    
    
}
