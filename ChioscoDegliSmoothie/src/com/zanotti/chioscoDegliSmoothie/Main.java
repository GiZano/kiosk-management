package com.zanotti.chioscoDegliSmoothie;

import com.zanotti.chioscoDegliSmoothie.exception.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Object;

public class Main {

    // metodo per la selezione nei menù e di variabili che devono essere positive o entro un determinato range di valori interi
    static int scelta(Scanner scn, int scelte){
        int input = -1;
        do{
            try{
                System.out.print("Inserire valore:");
                input = scn.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Inserire valori numerici!");
                input = -1;
                scn.next();
            }
        }while(input < 0 || input > scelte);
        return input;
    }

    static synchronized void autoRifornimento(Chiosco chiosco, String frutto, int quantita){
        System.out.print("\nRifornimento in corso");
        try {
            // thread sleep per far aspettare l'utente per l'avvenimento del rifornimento, simulando l'attesa per l'arrivo della merce nella vita reale
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print(".");
            Thread.sleep(1000);
            System.out.println(".");
        }catch(InterruptedException e){
            System.out.println("Interrupted!");
        }
        chiosco.getMagazzino().get(chiosco.trovaFrutto(frutto)).aggiungiScorte(quantita);
        System.out.println("Scorte aggiunte con successo! (" + quantita + ")");

    }


    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String nome;
        int quantita, maturazione, temperatura;
        double prezzo;
        HashMap<String, Integer> ricetta = new HashMap<String, Integer>();
        ArrayList<String> logErrori = new ArrayList<>();
        Chiosco chiosco = new Chiosco();

        System.out.println("Benvenuto al programma di gestione del chiosco!");
        System.out.println("Potrai eseguire le azioni tramite il menu delle azioni.");
        while(true) {

            System.out.println("""
                    
                    MENU'
                    1. Gestione Frutta/Magazzino
                    2. Gestione Smoothie/Menu'
                    3. Stampa log degli errori
                    0. Esci
                    """);

            int scelta = scelta(scn, 3);

            switch(scelta){

                case 1:
                    // gestione frutta/magazzino

                    System.out.println("Gestione Frutta e Magazzino!");
                    System.out.println("""
                            MENU'
                            1. Aggiungi nuovo frutto al magazzino
                            2. Aggiungi scorte per un frutto
                            3. Aggiorna maturazione di un frutto
                            4. Stampa magazzino
                            0. Torna indietro
                            """);
                    scelta = scelta(scn, 4);

                    switch(scelta){
                        case 1:
                            // aggiungi nuovo frutto
                            System.out.print("Inseirisci nome del frutto:");
                            scn.nextLine();
                            nome = scn.nextLine();

                            do {
                                try {
                                    System.out.print("Inserisci quantita iniziale:");
                                    quantita = scn.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Inserire valore numerico!");
                                    quantita = -1;
                                    scn.next();
                                }
                            }while(quantita < 0);

                            System.out.println("Inserisci maturazione: (da 1 a 10)");
                            do {
                                maturazione = scelta(scn, 10);
                            }while(maturazione <= 0);
                            do {
                                try {
                                    System.out.print("Inserisci prezzo:");
                                    prezzo = scn.nextDouble();
                                } catch (InputMismatchException e) {
                                    System.out.println("Inserire valore numerico!");
                                    prezzo = -1;
                                    scn.next();
                                }
                            }while(prezzo <= 0);

                            Frutto f = new Frutto(nome, quantita, maturazione, prezzo);
                            chiosco.aggiungiFrutto(f);

                            break;
                        case 2:
                            // aggiungi scorte a un frutto

                            if(chiosco.getMagazzino().isEmpty()){
                                System.out.println("Nessun frutto presente in magazzino!");
                                continue;
                            }

                            System.out.println("Selezionare frutto:");
                            chiosco.stampaMagazzino();
                            do {
                                scelta = scelta(scn, chiosco.getMagazzino().size() + 1);
                            }while(scelta <= 0);
                            System.out.println("Quanti grammi si devono aggiungere?");
                            quantita = scelta(scn, Integer.MAX_VALUE);

                            chiosco.getMagazzino().get(scelta-1).aggiungiScorte(quantita);

                            break;
                        case 3:
                            // modifica la maturazione di un frutto
                            if(chiosco.getMagazzino().isEmpty()){
                                System.out.println("Nessun frutto presente!");
                            }
                            else{
                                System.out.println("Selezionare frutto");
                                chiosco.stampaMagazzino();
                                do{
                                    scelta = scelta(scn, chiosco.getMagazzino().size() + 1);
                                }while(scelta <= 0);

                                System.out.println("Inserire maturazione attuale");
                                do{
                                    maturazione = scelta(scn, 10);
                                }while(maturazione <= 0);
                                chiosco.getMagazzino().get(scelta-1).setMaturazione(maturazione);
                            }
                            break;
                        case 4:
                            // stampa del magazzino
                            chiosco.stampaMagazzino();
                        default:
                            continue;
                    }

                    break;
                case 2:
                    // gestione Smoothie/Menu
                    System.out.println("Gestione Smoothie e Menu'!");
                    System.out.println("""
                            MENU'
                            1. Aggiungi nuovo smoothie al menu'
                            2. Prepara smoothie
                            3. Mostra menu'
                            0. Torna indietro
                            """);
                    scelta = scelta(scn, 3);

                    switch(scelta){
                        case 1:
                            // aggiungi nuovo smoothie

                            if(chiosco.getMagazzino().isEmpty()){
                                System.out.println("Nessun frutto presente! Impossibile aggiungere smoothie");
                                continue;
                            }

                            System.out.print("Inserisci nome dello smoothie:");
                            nome = scn.next();
                            System.out.println("Inserisci temperatura ideale:");
                            temperatura = scelta(scn, Integer.MAX_VALUE);
                            System.out.println("Inserire ricetta selezionando frutto e quantita' necessaria\nInserire 0 per terminare la ricetta");
                            ricetta.clear();
                            do{
                                System.out.println("Lista frutta:");
                                chiosco.stampaMagazzino();
                                System.out.println("0. Fine");
                                System.out.println("Inserire numero del frutto:");
                                scelta = scelta(scn, chiosco.getMagazzino().size()+1);

                                if(scelta != 0) {
                                    if(ricetta.containsKey(chiosco.getMagazzino().get(scelta-1).getNome())){
                                        System.out.println("Frutto gia' scelto!");
                                    }
                                    else {
                                        do{
                                            try{
                                                System.out.print("Inserire quantita' necessaria:");
                                                quantita = scn.nextInt();
                                            }catch(InputMismatchException e){
                                                System.out.println("Inserire valori numerici!");
                                                quantita = -1;
                                            }

                                        }while(quantita < 1);
                                        ricetta.put(chiosco.getMagazzino().get(scelta - 1).getNome(), quantita);
                                    }
                                }
                            }while(scelta != 0);

                            Smoothie s = new Smoothie(nome, ricetta, temperatura);
                            chiosco.aggiungiSmoothie(s);

                            break;
                        case 2:
                            // prepara smoothie

                            if(chiosco.getMenu().isEmpty()){
                                System.out.println("Nessuno smoothie presente nel menu'!");
                                continue;
                            }

                            System.out.println("Selezionare smoothie da preparare");
                            chiosco.stampaMenu();
                            do {
                                scelta = scelta(scn, chiosco.getMenu().size() + 1);
                            }while(scelta <= 0);
                            System.out.println("A che temperatura viene servito?");
                            temperatura = scelta(scn, Integer.MAX_VALUE);

                            try {
                                chiosco.prepara(chiosco.getMenu().get(scelta - 1), temperatura);
                            }catch(FruttoTroppoMaturoException | FruttoAcerboException | TemperaturaErrataException | FrullatoreRottoException e){
                                System.out.println(e.getMessage());
                                logErrori.add(e.getMessage());
                            }catch(QuantitaInsufficienteException e){
                                System.out.println(e.getMessage());
                                logErrori.add(e.getMessage());
                                autoRifornimento(chiosco, e.getFrutto(), e.getQuantitaMancante());

                            }

                            break;
                        case 3:
                            // mostra menu'
                            chiosco.stampaMenu();
                            break;
                        default:
                            continue;
                    }
                    break;
                case 3:
                    for(int i = 0; i < logErrori.size(); i++){
                        System.out.println((i+1) + ". : " + logErrori.get(i));
                    }
                    break;
                case 0:
                    return;
            }
        }
    }
}
