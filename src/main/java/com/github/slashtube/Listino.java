package com.github.slashtube;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * La classe Listino serve per parsare il file excel.
 * Il costruttore si occupera' di individuare l'indice delle colonne che
 * necessarie per la comparazione.
 * 
 * 
 * @author slashtube
 * 
 */

public class Listino {
    private final String fname; // Nome del file del listino
    private int[] colIndex; // indice delle colonne da confrontare (0 = BARCODE, 1 = descrizione, 2 = ivato)
    private int riemp;
    private int startrow; // Riga dalla quale incominciare a cercare i prodotti
    private Sheet foglio;

    public Listino(String fname) {
        this.fname = fname;
        this.colIndex = new int[3];
        this.riemp = 0;
        int codicealtcol = 0;

        /*
         * In certi file (depa) aggiungono piu' voci dell'ivato e nascondono quelli che
         * non vengono utilizzati.
         * L'idea e' quella di tenere conto solo della prima inserzione
         */
        int occ = 0;

        String extension = GetExtension(fname);

        // Cerca l'indice delle colonne per l'ivato, la descrizione e il codice a barre
        try (FileInputStream file = new FileInputStream(this.fname)) {
            Workbook wb = null;

            if (extension.equals("xlsx")) {
                wb = new XSSFWorkbook(file);
            } else if (extension.equals("xls")) {
                wb = new HSSFWorkbook(file);
            }

            if (wb == null) {
                System.err.println("Estensione file non riconosciuta");
            } else {
                this.foglio = wb.getSheetAt(0);
                for (var rows : foglio) {
                    for (var cell : rows) {
                        if (cell != null && cell.getCellType() == CellType.STRING) {
                            switch (cell.getStringCellValue().toLowerCase()) {
                                case "codice ean":
                                case "ean":
                                case "barcode":
                                    this.colIndex[0] = cell.getColumnIndex();
                                    this.riemp++;
                                    break;

                                case "descrizione":
                                    this.colIndex[1] = cell.getColumnIndex();
                                    this.riemp++;
                                    break;

                                case "prezzo offerta":
                                case "ivato":
                                case "ivato sc.":
                                    if (occ < 1) {
                                        this.colIndex[2] = cell.getColumnIndex();
                                        this.riemp++;
                                        occ++;
                                    }

                                    break;

                                case "codice":
                                    codicealtcol = cell.getColumnIndex();
                                    break;

                            }
                        }
                    }

                    if (riemp >= 2) {
                        this.startrow = rows.getRowNum();
                        break;
                    }
                }

                if (this.colIndex[0] <= 0) {
                    this.colIndex[0] = codicealtcol;
                }
                wb.close();
            }

        } catch (IOException e) {
            System.err.println("Errore nell'apertura di uno dei file: " + e);

        }
    }

    private String GetExtension(String fname) {
        String extension = fname.split("[.]")[1];

        return extension.toLowerCase();
    }

    public String getFname() {
        return this.fname;
    }

    public int getColIdx(int index) {
        return this.colIndex[index];
    }

    public int getStartRow() {
        return this.startrow;
    }

    public Sheet getFoglio() {
        return this.foglio;
    }

}
