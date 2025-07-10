package com.github.slashtube.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*
 * Identifica le colonne che devono essere lette dal Reader
 */

public class ExcelParser {
    final private int [] indexes;
    private Sheet foglio;
    private int startrow;

    public ExcelParser() {
        this.indexes = new int[3];
        this.foglio = null;
        this.startrow = 0;
    }

    public void parse(File file) throws IOException {
        Workbook wb = WorkbookFactory.create(file);
        int riemp = 0;
        int occ = 0;
        int alt_barcode = 0; // Variabile utilizzata se non viene rilevato il barcode

        this.foglio = wb.getSheetAt(0);

        Iterator<Row> iterator = foglio.iterator();
        Row row;

        while(riemp < 3 && iterator.hasNext()) {
            row = iterator.next();

            for(var cell : row) {
                if(cell != null && cell.getCellType() == CellType.STRING && riemp < 4) {
                    String value = cell.getStringCellValue().toLowerCase();
                    switch(value) {
                        case "ean":
                        case "barcode":
                        case "codice ean":
                            this.indexes[0] = cell.getColumnIndex();
                            riemp++;
                            break;
                        case "descrizione":
                            this.indexes[1] = cell.getColumnIndex();
                            riemp++;
                            break;
                        // Se rileva piu' di un ivato copia solo la sua prima occorrenza
                        case "ivato":
                            if(occ >= 1) {
                                break;
                            }
                        // Sovrascrive ivato trovato in precedenza se rileva un prezzo scontato
                        case "prezzo offerta":
                        case "ivato sc.":
                            this.indexes[2] = cell.getColumnIndex();
                            riemp++;
                            occ++;
                            break;
                        case "codice":
                            alt_barcode = cell.getColumnIndex();
                            break;
                    }
                }
            }

            // Se dopo una iterazione trova 2 elementi su 3 molto probabilmente il barcode avra' come nome "codice"
            if(riemp >= 2) {
                if(this.indexes[0] <= 0) {
                    this.indexes[0] = alt_barcode;
                    riemp++;
                } 
                this.startrow = row.getRowNum();
            }
        }
    }

    public int getStartRow() {
        return this.startrow;
    }

    public Sheet getFoglio() {
        return this.foglio;
    }

    public int[] getIndexes() {
        return this.indexes;
    }

}
