package com.github.slashtube;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Prodotto> Prodotti = new HashMap<>();
        // TODO rilevare automaticamente file

        // for loop per ogni file

        Listino test = new Listino("VOLANTINO 22.xls");
        Sheet foglio = test.getListino();
        int count = test.getStartRow() + 1;
        Row row = foglio.getRow(count++);

        // Creazione mappa
        while (row != null) {
            String barcode = row.getCell(test.getColIdx(0)).getStringCellValue();

            if (barcode != null) {
                String descrizione = row.getCell(test.getColIdx(1)).getStringCellValue();
                Double prezzo = row.getCell(test.getColIdx(2)).getNumericCellValue();

                if (Prodotti.containsKey(barcode)) {
                    Prodotti.get(barcode).appendPrezzo(prezzo);
                } else {
                    Prodotto p = new Prodotto(descrizione, prezzo);
                    Prodotti.put(barcode, p);
                }
            }

            row = foglio.getRow(count++);
        }



    }
}