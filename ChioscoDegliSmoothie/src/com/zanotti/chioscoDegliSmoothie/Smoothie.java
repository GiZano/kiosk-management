package com.zanotti.chioscoDegliSmoothie;

import java.util.HashMap;

public class Smoothie {

    // definizione degli attributi della classe
    private String nome;
    private HashMap<String, Integer> ricetta;
    private int temperaturaIdeale;

    // definizione dei metodi per la gestione della classe

    // costruttore della classe

    public Smoothie (String nome, HashMap<String, Integer> ricetta, int temperaturaIdeale) {
        this.nome = nome;
        this.ricetta = ricetta;
        this.temperaturaIdeale = temperaturaIdeale;
    }

    // getter per gli attributi

    public String getNome() { return nome; }
    public HashMap<String, Integer> getRicetta() { return ricetta; }
    public int getTemperaturaIdeale() { return temperaturaIdeale; }

}
