package com.github.slashtube;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProdottoMap {
    private HashMap<String, ProdottoSorter> Prodotti;

    public ProdottoMap() {
        this.Prodotti = new HashMap<>();
    }

    public HashMap<String, ProdottoSorter> getProdotti() {
        return this.Prodotti;
    }

    public void WriteFile(String fname) throws IOException {
        try (FileOutputStream file = new FileOutputStream(fname); Workbook wb = new XSSFWorkbook()) {
            var foglio = wb.createSheet("Listino");
            int i = 0;

            // Impostazioni base del foglio
            foglio.setColumnWidth(0, 8000);
            foglio.setColumnWidth(1, 20000);
            foglio.setColumnWidth(2, 6000);

            // Definizione font
            XSSFFont font = ((XSSFWorkbook) wb).createFont();
            font.setFontHeightInPoints((short) 16);

            // Definizione dello stile
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFont(font);

            foglio.setDefaultColumnStyle(0, style);
            foglio.setDefaultColumnStyle(1, style);
            foglio.setDefaultColumnStyle(2, style);

            // Riga principale
            Row row = foglio.createRow(i++);
            row.createCell(0).setCellValue("EAN");
            row.createCell(1).setCellValue("Descrizione");
            row.createCell(2).setCellValue("Ivato Minimo");

            // Riga vuota
            foglio.createRow(i++);

            for (var ean : Prodotti.keySet()) {
                row = foglio.createRow(i++);

                ProdottoSorter prodotto = Prodotti.get(ean);
                prodotto.SortByValue();

                String descrizione = prodotto.getDescrizione();

                int j = 2;

                for (var p : prodotto.GetProdotti()) {
                    Double ivatomin = p.getPrezzo();
                    XSSFHyperlink hprlnk = new XSSFHyperlink(HyperlinkType.URL) {
                    };
                    File f = new File("files/" + p.getFname());

                    // Hyperlink setup
                    hprlnk.setAddress(f.toURI().toString());
                    hprlnk.setTooltip(p.getFname());
                    hprlnk.setLocation(p.getReference().formatAsString());

                    if (ean.equals("") || ean.equals("-")) {
                        row.createCell(0).setCellValue("Prodotto senza codice");
                        row.createCell(1).setCellValue(p.getRelDescr());
                        row.createCell(2).setHyperlink(hprlnk);
                        row.createCell(2).setCellValue(String.format("%.2f", ivatomin));

                        row = foglio.createRow(i++);

                    } else {
                        row.createCell(0).setCellValue(ean);
                        row.createCell(1).setCellValue(descrizione);
                        row.createCell(j).setHyperlink(hprlnk);
                        row.createCell(j).setCellValue(String.format("%.2f", ivatomin));
                        j++;

                    }
                }

                // }
            }

            wb.write(file);

        }
    }

    // @reference: https://www.baeldung.com/java-hashmap-sort
    public void sortByValue() {
        this.Prodotti = this.Prodotti.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

}
