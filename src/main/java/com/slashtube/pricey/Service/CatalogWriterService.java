package com.slashtube.pricey.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slashtube.pricey.Model.Entry;
import com.slashtube.pricey.Model.Product;
import com.slashtube.pricey.Repo.EntryRepo;
import com.slashtube.pricey.Repo.ProductRepo;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
public class CatalogWriterService {
    @Setter
    private String path;

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private ProductRepo productRepo;

    public void write() throws IOException {
        ArrayList<Product> products = (ArrayList<Product>) productRepo.findEntryProducts();

        XSSFWorkbook xwb = new XSSFWorkbook();

        SXSSFWorkbook wb = new SXSSFWorkbook(xwb);
        SXSSFSheet sheet = wb.createSheet("Listino");
        sheet.trackAllColumnsForAutoSizing();

        wb.setCompressTempFiles(true);
        sheet.setRandomAccessWindowSize(5);


        // Set Styles
        this.setStyle(sheet, wb);
        this.createHeader(sheet);

        int row_count = 2;

        Iterator<Product> itr = products.iterator();

        while(itr.hasNext()) {
            int price_col = 2;
            Product product = (Product) itr.next();
            ArrayList<Entry> entryList = (ArrayList<Entry>) entryRepo.findEntryByEAN(product.getEAN());
            SXSSFRow row =  sheet.createRow(row_count++);

            row.createCell(0).setCellValue(product.getEAN());
            row.createCell(1).setCellValue(product.getDescription());
            Iterator<Entry> entryitr = entryList.iterator();
            while(entryitr.hasNext()) {
                Entry e = entryitr.next();
                XSSFHyperlink link = createLink(e.getCatalog().getOrigin(), wb, e.getReference());
                row.createCell(price_col).setHyperlink(link);
                row.createCell(price_col++).setCellValue(String.format("%.2f", e.getPrice()));
                entryitr.remove();
            }


            itr.remove();
        }

        for(int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream outputStream = new FileOutputStream(this.path + "Listino.xlsx"); 
        wb.write(outputStream);
        outputStream.close();

    }

    private void setStyle(Sheet foglio, SXSSFWorkbook wb) {
        // Font
        Font font = ((SXSSFWorkbook) wb).createFont();
        font.setFontHeightInPoints((short) 16);

        // Style
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font);

        foglio.setDefaultColumnStyle(0, style);
        foglio.setDefaultColumnStyle(1, style);
        foglio.setDefaultColumnStyle(2, style);

        
    }

    private void createHeader(Sheet foglio) {
        Row row = foglio.createRow(0);
        row.createCell(0).setCellValue("EAN");
        row.createCell(1).setCellValue("Descrizione");
        row.createCell(2).setCellValue("Ivato Minimo");

        
    }

    private XSSFHyperlink createLink(String filename, SXSSFWorkbook wb, String reference) throws IOException{
        CreationHelper createHelper = wb.getCreationHelper();
        XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.FILE);
        link.setAddress(filename.replaceAll(" ", "%20"));
        link.setTooltip(filename);
        link.setLocation(reference);


        return link;
    }
    
}
