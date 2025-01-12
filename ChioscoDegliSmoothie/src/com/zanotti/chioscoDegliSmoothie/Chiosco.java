package com.zanotti.chioscoDegliSmoothie;

import com.zanotti.chioscoDegliSmoothie.exception.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Chiosco {

    // definizione attributi della classe
    private ArrayList<Frutto> magazzino = new ArrayList<>();
    private ArrayList<Smoothie> menu = new ArrayList<>();

    // definizione dei metodi per la gestione del chiosco

    // getter del magazzino e del menu

    public ArrayList<Frutto> getMagazzino() { return this.magazzino; }
    public ArrayList<Smoothie> getMenu() { return this.menu; }


    // metodi per la stampa a video di menu e magazzino

    public void stampaMenu(){

        if(menu.isEmpty()){
            System.out.println("Menu' vuoto!");
        }
        else{
        System.out.println("Menu degli Smoothie:");
        for(int i = 0; i < menu.size(); i++){
            System.out.println((i+1) + ". : " + menu.get(i).getNome());
        }
        }
    }

    public void stampaMagazzino(){
        if(this.magazzino.isEmpty()){
            System.out.println("Magazzino vuoto!");
        }
        else {
            System.out.println("Magazzino:");
            for (int i = 0; i < this.magazzino.size(); i++) {
                System.out.println((i+1) + ". : " + this.magazzino.get(i).getNome() + "\nquantita: " + this.magazzino.get(i).getQuantitaDisponibile() + ", maturazione: " + this.magazzino.get(i).getMaturazione() + ", prezzo(al grammo): " + this.magazzino.get(i).getPrezzo());
            }
        }
    }

    // metodi per l'aggiunta di Frutti e Smoothie alle corrispettive liste

    public void aggiungiFrutto(Frutto frutto){
        int checkPresenza = trovaFrutto(frutto.getNome());
        if(checkPresenza == -1){
            this.magazzino.add(frutto);
            // ordinamento alfabetico per rendere la ricerca durante la preparazione più semplice tramite la ricerca dicotomica
            Collections.sort(magazzino);
        }
        else {
            System.out.println("Frutto gia' presente!");
        }
    }

    public void aggiungiSmoothie(Smoothie smoothie){
        if(this.menu.contains(smoothie)){
            System.out.println("Smoothie gia' presente!");
        }else {
            this.menu.add(smoothie);
        }
    }

    // metodo per la ricerca di un frutto all'interno del magazzino in base al nome

    public int trovaFrutto(String nomeFrutto){
        if(this.magazzino.isEmpty()){
            return -1;
        }
        int inizio = 0, fine = this.magazzino.size(), pointer = -1;
        boolean found = false;
        while(!found && inizio <= fine){
            pointer = (inizio+fine)/2;
            if(Objects.equals(nomeFrutto.toLowerCase(), this.magazzino.get(pointer).getNome().toLowerCase())){
                found = true;
            }
            else if(nomeFrutto.compareTo(this.magazzino.get(pointer).getNome()) < 0){
                fine = pointer-1;
            }
            else{
                pointer = pointer+1;
            }
        }
        if(!found){
            return -1;
        }
        return pointer;
    }

    public void prepara(Smoothie smoothie, int temperatura) throws FruttoTroppoMaturoException, FruttoAcerboException, QuantitaInsufficienteException, TemperaturaErrataException, FrullatoreRottoException{
        int quantitaMancante;
        double costo = 0;

        if(temperatura > ( smoothie.getTemperaturaIdeale() + 2 ) ){
            throw new TemperaturaErrataException("Errore: temperatura ideale superata di " + (temperatura - smoothie.getTemperaturaIdeale()) + "!");
        }
        else if(temperatura < ( smoothie.getTemperaturaIdeale() - 2 ) ){
            throw new TemperaturaErrataException("Errore: temperatura inferiore di " + (smoothie.getTemperaturaIdeale() - temperatura) + " rispetto a quella ideale!");
        }

        Random rand = new Random();
        if(rand.nextDouble() < 0.1){
            throw new FrullatoreRottoException("Errore: frullatore rotto!");
        }

        for(Map.Entry<String, Integer> entry : smoothie.getRicetta().entrySet()  ){

            if(this.magazzino.get(trovaFrutto(entry.getKey())).getMaturazione() > 8){
                throw new FruttoTroppoMaturoException("Errore: " + entry.getKey() + " troppo maturo!");
            }
            else if(this.magazzino.get(trovaFrutto(entry.getKey())).getMaturazione() < 2 ){
                throw new FruttoAcerboException("Errore: " + entry.getKey() + " acerbo!");
            }
            if(entry.getValue() > magazzino.get(trovaFrutto(entry.getKey())).getQuantitaDisponibile() ){

                quantitaMancante = (entry.getValue() - magazzino.get(trovaFrutto(entry.getKey())).getQuantitaDisponibile());
                throw new QuantitaInsufficienteException("Errore: quantita' di " + entry.getKey() + " insufficiente di " + quantitaMancante + "!", quantitaMancante, entry.getKey());

            }
        }

        // consumazione frutti dal magazzino

        for(Map.Entry<String, Integer> entry : smoothie.getRicetta().entrySet()  ){
            magazzino.get(trovaFrutto(entry.getKey())).consuma(entry.getValue());
            costo += magazzino.get(trovaFrutto(entry.getKey())).getPrezzo() * (double)entry.getValue();
        }

        // messaggio di riuscita preparazione
        System.out.println("Smoothie " + smoothie.getNome() + " preparato con successo!\nPrezzo: " + costo);
    }

}
