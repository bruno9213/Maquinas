package com.example.testgitapp;

public class Radar {
    private int id;
    private String nome;
    private String localizacao;
    private int num_vias;
    private int num_sentido;

    public Radar(int id, String nome, String localizacao, int num_vias, int num_sentido){
        this.id=id;
        this.nome=nome;
        this.localizacao=localizacao;
        this.num_vias=num_vias;
        this.num_sentido=num_sentido;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getNum_sentido() {
        return num_sentido;
    }

    public int getNum_vias() {
        return num_vias;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    //tem de se criar um método que crie os radares a partir da info da base de dados


}
