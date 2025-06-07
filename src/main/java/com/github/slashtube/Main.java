package com.github.slashtube;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Main {
    public static void main(String[] args) {
        ProdottoMap map = new ProdottoMap();
        HashMap<String, ProdottoSorter> Prodotti = map.getProdotti();

        try {
            String[] Files = FindFiles();

            // for loop per ogni file
            for (var file : Files) {
                Listino test = new Listino("files/" + file);
                Sheet foglio = test.getFoglio();
                int count = test.getStartRow() + 1;
                Row row = foglio.getRow(count++);

                // Creazione mappa
                String barcode;
                while (row != null) {
                    if (row.getCell(test.getColIdx(0)) != null) {
                        switch (row.getCell(test.getColIdx(0)).getCellType()) {
                            case CellType.STRING:
                                barcode = row.getCell(test.getColIdx(0)).getStringCellValue();
                                break;
                            case CellType.NUMERIC:
                                barcode = String.format("%.0f", row.getCell(test.getColIdx(0)).getNumericCellValue());
                                break;
                            default:
                                barcode = null;
                        }

                        if (barcode != null) {
                            String descrizione = row.getCell(test.getColIdx(1)).getStringCellValue();
                            Double prezzo = row.getCell(test.getColIdx(2)).getNumericCellValue();

                            if (Prodotti.containsKey(barcode)) {
                                Prodotti.get(barcode).appendProdotto(file, prezzo, row.getRowNum(), 0);
                            } else {
                                ProdottoSorter p = new ProdottoSorter(descrizione, file, prezzo, row.getRowNum(), 0);
                                Prodotti.put(barcode, p);
                            }
                        }

                    }

                    row = foglio.getRow(count++);
                }
            }

            map.WriteFile("Listino.xlsx");
            showMessageDialog(null, "Operazione avvenuta con successo");

        } catch (Exception e) {
            System.out.println(e);
            showMessageDialog(null, "Errore", "Errore", ERROR_MESSAGE);
        }

    }

    public static String[] FindFiles() {
        FilenameFilter filter = (dir, name) -> name.toLowerCase().endsWith(".xls")
                || name.toLowerCase().endsWith(".xlsx");
        File dir = new File("files");

        return dir.list(filter);
    }

}