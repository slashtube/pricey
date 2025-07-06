package com.github.slashtube;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Main {
    public static void main(String[] args) {
        final Logger LOGGER = LogManager.getLogger();
        ProdottoMap map = new ProdottoMap();
        HashMap<String, ProdottoSorter> Prodotti = map.getProdotti();

        try {
            String[] Files = FindFiles();

            // for loop per ogni file
            for (var file : Files) {
                final String filename = "files/" + file;
                final Listino listino = new Listino(filename);
                final Sheet foglio = listino.getFoglio();
                int count = listino.getStartRow() + 1;
                Row row = foglio.getRow(count++);

                // Creazione mappa
                final int barindx = listino.getColIdx(0);
                String barcode;

                while (row != null) {
                    if (row.getCell(barindx) != null) {
                        switch (row.getCell(barindx).getCellType()) {
                            case CellType.STRING:
                                barcode = row.getCell(listino.getColIdx(0)).getStringCellValue();
                                break;
                            case CellType.NUMERIC:
                                barcode = String.format("%.0f",
                                        row.getCell(listino.getColIdx(0)).getNumericCellValue());
                                break;
                            default:
                                barcode = null;
                        }

                        if (barcode != null) {
                            final String descrizione = row.getCell(listino.getColIdx(1)).getStringCellValue();

                            if(barcode.equals("") || barcode.equals("-")) {
                                barcode = "Prodotto senza codice";
                            }

                            final int prezzoidx = listino.getColIdx(2);
                            Double prezzo = row.getCell(prezzoidx).getNumericCellValue();

                            if (Prodotti.containsKey(barcode)) {
                                Prodotti.get(barcode).appendProdotto(file, descrizione, prezzo, row.getRowNum(), 0);
                            } else {
                                ProdottoSorter p = new ProdottoSorter(descrizione, descrizione, file, prezzo,
                                        row.getRowNum(), 0);
                                Prodotti.put(barcode, p);
                            }
                        }

                    }

                    row = foglio.getRow(count++);
                }
            }

            map.sortByValue();
            map.WriteFile("Listino.xlsx");
            showMessageDialog(null, "Operazione avvenuta con successo");

        } catch (IOException e) {
            showMessageDialog(null, "Errore nella creazione del file", "Errore", ERROR_MESSAGE);
        } catch (Exception e) {
            showMessageDialog(null, "Errore", "Errore", ERROR_MESSAGE);
            LOGGER.catching(e);
        }

    }

    public static String[] FindFiles() {
        FilenameFilter filter = (dir, name) -> name.toLowerCase().endsWith(".xls")
                || name.toLowerCase().endsWith(".xlsx");
        File dir = new File("files");

        return dir.list(filter);
    }

}