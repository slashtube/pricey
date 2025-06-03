package com.github.slashtube;

/**
* La classe Prodotto serve per aggiungere un livello di astrazione. 
* 
* 
* 
* @author slashtube 
* 
*/


public class Prodotto implements Comparable<Prodotto> {
    final String fname;
    final Double prezzo;

    public Prodotto(String fname, Double prezzo) {
        this.fname = fname;
        this.prezzo = prezzo;
    }

    public String getFname() {
        return this.fname;
    }

    public Double getPrezzo() {
        return this.prezzo;
    }

    @Override
    public int compareTo(Prodotto p) {
        if(this.prezzo.compareTo(p.prezzo) > 0) {
            return 1;
        } else if(this.prezzo.compareTo(p.prezzo) < 0){
            return -1;
        } else {
            return 0;
        }
    }

    

}
