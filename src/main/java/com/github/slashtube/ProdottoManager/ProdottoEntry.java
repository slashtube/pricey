package com.github.slashtube.ProdottoManager;

import java.io.File;

import org.apache.poi.ss.util.CellReference;

public class ProdottoEntry {
    final private File origin;
    final private Double price;
    final private CellReference reference;

    public ProdottoEntry(File origin, Double price, CellReference reference) {
        this.origin = origin;
        this.price = price;
        this.reference = reference;
    }

    
    public File getOrigin() {
        return this.origin;
    }

    public Double getPrice() {
        return this.price;
    }

    public CellReference getReference() {
        return this.reference;
    }

}
