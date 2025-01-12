package com.zanotti.chioscoDegliSmoothie;

public class Frutto implements Comparable<Frutto> {

    // definizione degli attributi della classe
    private String nome;
    private int quantitaDisponibile;
    private int maturazione;
    private double prezzo;

    // definizione dei metodi della classe

    // costruttore della classe

    public Frutto(String nome, int quantitaIniziale, int maturazione, double prezzo) {
        this.nome = nome;
        this.quantitaDisponibile = quantitaIniziale;
        this.maturazione = maturazione;
        this.prezzo = prezzo;
    }

    // getter per gli attributi

    public String getNome() { return nome; }
    public int getQuantitaDisponibile() { return quantitaDisponibile; }
    public int getMaturazione() { return maturazione; }
    public double getPrezzo() { return prezzo; }

    // setter per la maturazione che può variare

    public void setMaturazione(int maturazione) { this.maturazione = maturazione; }

    // metodi per l'aggiunta e la consumazione di scorte

    public void aggiungiScorte(int quantita){
        this.quantitaDisponibile += quantita;
    }

    public void consuma(int quantita){
        this.quantitaDisponibile -= quantita;
    }

    // implementazione del metodo "compareTo" dell'interfaccia "Comparable" che permette di comparare oggetti tramite un attributo a scelta
    @Override
    public int compareTo(Frutto o) {
        Frutto f = (Frutto) o;
        return this.nome.compareTo(f.nome);
    }



}
