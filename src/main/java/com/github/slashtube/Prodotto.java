package com.github.slashtube;

import org.apache.poi.ss.util.CellReference;

/**
* La classe Prodotto serve per aggiungere un livello di astrazione. 
* 
* 
* 
* @author slashtube 
* 
*/


public class Prodotto implements Comparable<Prodotto> {
    private final String fname;
    private final Double prezzo;
    private final CellReference reference;
    

    public Prodotto(String fname, Double prezzo, int row, int col) {
        this.fname = fname;
        this.prezzo = prezzo;
        this.reference = new CellReference(row, (short) col);
    }

    public String getFname() {
        return this.fname;
    }

    public Double getPrezzo() {
        return this.prezzo;
    }

    public CellReference getReference() {
        return this.reference;
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
