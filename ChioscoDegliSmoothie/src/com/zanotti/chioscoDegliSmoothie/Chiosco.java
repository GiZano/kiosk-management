package com.zanotti.chioscoDegliSmoothie;

import java.util.ArrayList;
import java.util.Collections;

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
                System.out.println((i+1) + ". : " + this.magazzino.get(i).getNome() + "\nquantita: " + this.magazzino.get(i).getQuantitaDisponibile() + "g, maturazione: " + this.magazzino.get(i).getMaturazione() + ", prezzo(al grammo): " + this.magazzino.get(i).getPrezzo());
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
            if(nomeFrutto.equalsIgnoreCase(this.magazzino.get(pointer).getNome())){
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

    public void autoRifornimento(String frutto, int quantita){
        System.out.print("\nRifornimento in corso");
        try {
            // thread sleep per far aspettare l'utente per l'avvenimento del rifornimento, simulando l'attesa per l'arrivo della merce nella vita reale
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(500);
            System.out.println(".");
        }catch(InterruptedException e){
            System.out.println("Interrupted!");
        }
        this.getMagazzino().get(this.trovaFrutto(frutto)).aggiungiScorte(quantita);
        System.out.println("Scorte aggiunte con successo! (" + quantita + ")");

    }


}
