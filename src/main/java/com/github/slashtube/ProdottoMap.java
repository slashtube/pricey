package com.github.slashtube;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProdottoMap {
    final private HashMap<String, ProdottoSorter> Prodotti;

    public ProdottoMap() {
        this.Prodotti = new HashMap<>();
    }

    public HashMap<String, ProdottoSorter> getProdotti() {
        return this.Prodotti;
    }

    public void WriteFile(String fname) {
        try (FileOutputStream file = new FileOutputStream(fname); Workbook wb = new XSSFWorkbook()) {
            var foglio = wb.createSheet("Listino");
            int i = 0;

            foglio.setColumnWidth(0, 6000);
            foglio.setColumnWidth(1, 20000);
            foglio.setColumnWidth(2, 6000);

            Row row = foglio.createRow(i++);
            row.createCell(0).setCellValue("EAN");
            row.createCell(1).setCellValue("Descrizione");
            row.createCell(2).setCellValue("Ivato Minimo");

            for (var ean : Prodotti.keySet()) {
                ProdottoSorter prodotto = Prodotti.get(ean);
                prodotto.SortByValue();

                String descrizione = prodotto.getDescrizione();
                Double ivatomin = prodotto.GetProdotti().getFirst().getPrezzo();




                row = foglio.createRow(i++);
                row.createCell(0).setCellValue(ean);
                row.createCell(1).setCellValue(descrizione);
                row.createCell(2).setCellValue(ivatomin);

            }

            wb.write(file);

        } catch (IOException e) {
            System.err.println("Errore nell'apertura del file: " + e);
        }

    }

}
