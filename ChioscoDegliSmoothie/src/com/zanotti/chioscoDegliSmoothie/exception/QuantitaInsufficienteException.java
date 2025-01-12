package com.zanotti.chioscoDegliSmoothie.exception;
import com.zanotti.chioscoDegliSmoothie.Frutto;

public class QuantitaInsufficienteException extends Exception {

    private int quantitaMancante;
    private String frutto;

    public QuantitaInsufficienteException(String messaggio, int quantitaMancante, String frutto) {
        super(messaggio);
        this.quantitaMancante = quantitaMancante;
        this.frutto = frutto;
    }

    public int getQuantitaMancante() {return this.quantitaMancante;}
    public String getFrutto() {return this.frutto;}
}
