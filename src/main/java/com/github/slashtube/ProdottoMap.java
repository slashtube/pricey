package com.github.slashtube;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
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

            // Impostazioni base del foglio
            foglio.setColumnWidth(0, 6000);
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


            for (var ean : Prodotti.keySet()) {
                ProdottoSorter prodotto = Prodotti.get(ean);
                prodotto.SortByValue();

                String descrizione = prodotto.getDescrizione();

                row = foglio.createRow(i++);
                row.createCell(0).setCellValue(ean);
                row.createCell(1).setCellValue(descrizione);

                int j = 2;

                for (var p : prodotto.GetProdotti()) {
                    Double ivatomin = p.getPrezzo();
                    Hyperlink hprlnk;
                    switch (GetExtension(p.getFname())) {
                        case "xlsx":
                            hprlnk = new XSSFHyperlink(HyperlinkType.URL){};
                            break;
                        case "xls":
                            hprlnk = new HSSFHyperlink(HyperlinkType.URL){};
                            break;
                        default:
                            hprlnk = null;
                            break;
                    }

                    if(hprlnk != null) {
                        File f = new File("files/" + p.getFname());
                        hprlnk.setAddress(f.toURI().toString());
                        row.createCell(j).setHyperlink(hprlnk);
                    }
                    row.createCell(j).setCellValue(String.format("%.2f",ivatomin));
                    j++;
                }

            }

            wb.write(file);

        } catch (IOException e) {
            System.err.println("Errore nell'apertura del file: " + e);
        }

    }

    private String GetExtension(String fname) {
        String extension = fname.split("[.]")[1];

        return extension.toLowerCase();
    }

}
