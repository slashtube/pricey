package com.github.slashtube;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* La classe Listino serve per parsare il file excel che poi sara' confrontato. 
* Il costruttore si occupera' di individuare l'indice delle colonne che poi saranno confrontate dalla classe Comparator.
* 
* 
* @author slashtube 
* 
*/

public class Listino {
    private final String fname; // Nome del file del listino
    private int[] colIndex; // indice delle colonne da confrontare (0 = BARCODE, 1 = descrizione, 2 = ivato)
    private int riemp;

    public Listino(String fname) {
        this.fname = fname;
        this.colIndex = new int[3];
        this.riemp = 0;

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
                Sheet sheet = wb.getSheetAt(0);

                for (var rows : sheet) {
                    for (var cell : rows) {
                        if (cell != null && cell.getCellType() == CellType.STRING) {
                            switch (cell.getStringCellValue().toLowerCase()) {
                                case "ean":
                                case "barcode":
                                    this.colIndex[0] = cell.getColumnIndex();
                                    this.riemp++;
                                    break;

                                case "descrizione":
                                    this.colIndex[1] = cell.getColumnIndex();
                                    this.riemp++;
                                    break;

                                case "ivato":
                                case "ivato sc.":
                                    this.colIndex[2] = cell.getColumnIndex();
                                    this.riemp++;
                                    break;
                            }
                        }
                    }

                    if (riemp >= 2) {
                        System.out.println(rows.getCell(this.colIndex[0]).getStringCellValue());
                        break;
                    }
                }
                

            }

        } catch (IOException e) {
            System.err.println("Errore nell'apertura del file: " + e);

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

    @Override
    public String toString() {
        return String.format("Barcode: %d\nDescrizione: %d\nIvato: %d\n", this.colIndex[0], this.colIndex[1],
                this.colIndex[2]);
    }

}
