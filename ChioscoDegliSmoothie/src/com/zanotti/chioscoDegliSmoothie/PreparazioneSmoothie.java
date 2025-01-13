package com.zanotti.chioscoDegliSmoothie;

import com.zanotti.chioscoDegliSmoothie.exception.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PreparazioneSmoothie {

    private ArrayList<String> logErrori = new ArrayList<>();

    public void prepara(Chiosco chiosco, Smoothie smoothie, int temperatura) throws FruttoTroppoMaturoException, FruttoAcerboException, QuantitaInsufficienteException, TemperaturaErrataException, FrullatoreRottoException {
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

            if(chiosco.getMagazzino().get(chiosco.trovaFrutto(entry.getKey())).getMaturazione() > 8){
                throw new FruttoTroppoMaturoException("Errore: " + entry.getKey() + " troppo maturo!");
            }
            else if(chiosco.getMagazzino().get(chiosco.trovaFrutto(entry.getKey())).getMaturazione() < 2 ){
                throw new FruttoAcerboException("Errore: " + entry.getKey() + " acerbo!");
            }
            if(entry.getValue() > chiosco.getMagazzino().get(chiosco.trovaFrutto(entry.getKey())).getQuantitaDisponibile() ){

                quantitaMancante = (entry.getValue() - chiosco.getMagazzino().get(chiosco.trovaFrutto(entry.getKey())).getQuantitaDisponibile());
                throw new QuantitaInsufficienteException("Errore: quantita' di " + entry.getKey() + " insufficiente di " + quantitaMancante + "!", quantitaMancante, entry.getKey());

            }
        }

        // consumazione frutti dal magazzino

        for(Map.Entry<String, Integer> entry : smoothie.getRicetta().entrySet()  ){
            chiosco.getMagazzino().get(chiosco.trovaFrutto(entry.getKey())).consuma(entry.getValue());
            costo += chiosco.getMagazzino().get(chiosco.trovaFrutto(entry.getKey())).getPrezzo() * (double)entry.getValue();
        }

        // messaggio di riuscita preparazione
        System.out.println("Smoothie " + smoothie.getNome() + " preparato con successo!\nPrezzo: " + costo);
    }

    public ArrayList<String> getLogErrori(){ return this.logErrori; }
}
