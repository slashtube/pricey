package com.github.slashtube.ProdottoManager;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.ss.util.CellReference;

public class Prodotto {
    private String barcode;
    final private String descrizione;
    final private ArrayList<ProdottoEntry> entryList;

    public Prodotto(String barcode, String descrizione, ProdottoEntry entry) {
        this.entryList = new ArrayList<>();
        this.barcode = barcode;
        this.descrizione = descrizione;
        this.entryList.add(entry);
    }

    public void setNewBarcode(String newbarcode) {
        this.barcode = newbarcode;
    }


    public String getBarcode() {
        return this.barcode;
    }

    public String getBarcodeAlias(String aliasPrefix) {
        return this.barcode.startsWith(aliasPrefix) ? "Prodotto senza codice" : this.barcode;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public ArrayList<ProdottoEntry> getEntryList() {
        return this.entryList;

    }

    public void AddEntry(File origin, Double price, CellReference reference) {
        ProdottoEntry newEntry = new ProdottoEntry(origin, price, reference);

        entryList.add(newEntry);
    }

    public void SortEntries() {
        this.entryList.sort(new OrdinaPerPrezzo());
    }

    

}
