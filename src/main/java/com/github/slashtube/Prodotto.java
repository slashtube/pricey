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
    private final String rel_descr;
    private final Double prezzo;
    private final CellReference reference;
    

    public Prodotto(String fname, String rel_descr, Double prezzo, int row, int col) {
        this.fname = fname;
        this.rel_descr = rel_descr;
        this.prezzo = prezzo;
        this.reference = new CellReference(row, (short) col);
    }

    public String getFname() {
        return this.fname;
    }

    public String getRelDescr() {
        return this.rel_descr;
    }

    public Double getPrezzo() {
        return this.prezzo;
    }

    public CellReference getReference() {
        return this.reference;
    }

    @Override
    public int compareTo(Prodotto p) {
        return this.prezzo.compareTo(p.prezzo);
    }

    

}
