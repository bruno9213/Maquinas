package threading;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * Classe Entidades têm parâmetros para criar ArrayList com todos os dados de
 * todas as entidades da Base de Dados.
 */
public class Entidades implements Serializable {

    private ArrayList<String> id, nome, user, mail, type;

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
