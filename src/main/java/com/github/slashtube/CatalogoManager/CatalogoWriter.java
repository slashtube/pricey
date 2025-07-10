package com.github.slashtube.CatalogoManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.slashtube.ProdottoManager.Prodotto;
import com.github.slashtube.ProdottoManager.ProdottoEntry;

public class CatalogoWriter {
    final private String path;
    final private Catalogo catalogo;

    public CatalogoWriter(String path, Catalogo catalogo) {
        this.path = path;
        this.catalogo = catalogo;
    }

    public void write() throws IOException {
        try(FileOutputStream file = new FileOutputStream(path); XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet foglio = wb.createSheet("Listino");
            
            setStyle(foglio, wb);
            createHeader(foglio);

            int row_index = 2;
            Row row;
            List<Prodotto> prodotti = catalogo.getProdotti();
            
            for(Prodotto prodotto : prodotti) {
                int ivato_index = 2;
                String barcode = prodotto.getBarcode();
                prodotto.SortEntries();
                
                row = foglio.createRow(row_index++);
                if(barcode.contains("N")) {
                    row.createCell(0).setCellValue("Prodotto senza codice");
                } else {
                    row.createCell(0).setCellValue(barcode);
                }

                row.createCell(1).setCellValue(prodotto.getDescrizione());

                for(ProdottoEntry entry : prodotto.getEntryList()) {
                    XSSFHyperlink link = createLink(entry.getOrigin(), entry.getReference());
                    row.createCell(ivato_index).setHyperlink(link);
                    row.createCell(ivato_index++).setCellValue(String.format("%.2f", entry.getPrice()));
                }
            }

            
            // Ridimensionamento automatico delle colonne
            foglio.autoSizeColumn(0);
            foglio.autoSizeColumn(1);
            foglio.autoSizeColumn(2);
         
            wb.write(file);
        }
    }

    private void setStyle(XSSFSheet foglio, XSSFWorkbook wb) {
            // Definizione font
            XSSFFont font = ((XSSFWorkbook) wb).createFont();
            font.setFontHeightInPoints((short) 16);

            // Definizione dello stile
            XSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setFont(font);


            // Applicazione stile
            foglio.setDefaultColumnStyle(0, style);
            foglio.setDefaultColumnStyle(2, style);
            foglio.setDefaultColumnStyle(1, style);
        
        
    }

    private void createHeader(XSSFSheet foglio) {
        Row row = foglio.createRow(0);
        row.createCell(0).setCellValue("EAN");
        row.createCell(1).setCellValue("Descrizione");
        row.createCell(2).setCellValue("Ivato Minimo");

        // Crea una riga vuota per fare spazio
        foglio.createRow(1);
        
    }

    private XSSFHyperlink createLink(File file, CellReference reference) {
        XSSFHyperlink link = new XSSFHyperlink(HyperlinkType.URL){};
        link.setAddress(file.toURI().toString());
        link.setTooltip(file.toString());
        link.setLocation(reference.formatAsString());


        return link;
    }

}
