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
 * 
 */
public class Entidades implements Serializable  {
    
    private ArrayList<String> id,nome,user,mail,type;

    public ArrayList<String> getId() {
        return id;
    }

    public ArrayList<String> getMail() {
        return mail;
    }

    public ArrayList<String> getNome() {
        return nome;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public ArrayList<String> getUser() {
        return user;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }

    public void setMail(ArrayList<String> mail) {
        this.mail = mail;
    }

    public void setNome(ArrayList<String> nome) {
        this.nome = nome;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public void setUser(ArrayList<String> user) {
        this.user = user;
    }
    
}
