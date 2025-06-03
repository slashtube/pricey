package com.github.slashtube;

import java.util.ArrayList;
import java.util.Collections;

/**
* La classe Prodotto serve per aggiungere un ulteriore livello di astrazione. 
* 
* 
* 
* @author slashtube 
* 
*/

public class ProdottoSorter {
    final private String descrizione;
    final private ArrayList<Prodotto> prodotti;

    ProdottoSorter(String descrizione, String fname, Double prezzo) {
        // In certi file le descrizioni incominciano con un punto, una virgola o uno spazio. Il regex le rimuove.
        this.descrizione = descrizione.replaceAll("^(\s|[.,])*","");
        this.prodotti = new ArrayList<>();
        this.prodotti.add(new Prodotto(fname, prezzo));
    }

    public void appendProdotto(String fname, Double prezzo) {
        this.prodotti.add(new Prodotto(fname, prezzo));
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public ArrayList<Prodotto> GetProdotti() {
        return this.prodotti;
    }

    public void SortByValue() {
        Collections.sort(prodotti);
    }

}
