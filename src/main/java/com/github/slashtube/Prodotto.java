package com.github.slashtube;

import java.util.ArrayList;

/**
* La classe Prodotto serve per aggiungere un ulteriore livello di astrazione. 
* 
* 
* 
* @author slashtube 
* 
*/
public class Prodotto {
    private String descrizione;
    private ArrayList<Double> prezzi;

    Prodotto(String descrizione, Double prezzo) {
        this.descrizione = descrizione;
        this.prezzi =  new ArrayList<>();
        this.prezzi.add(prezzo);
    }

    public void appendPrezzo(Double prezzo) {
        this.prezzi.add(prezzo);
    }

    public String getDescrizione() {
        return this.descrizione;
    }

}
