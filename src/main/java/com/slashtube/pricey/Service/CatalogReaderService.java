package com.slashtube.pricey.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slashtube.pricey.Model.Catalog;
import com.slashtube.pricey.Model.Entry;
import com.slashtube.pricey.Model.EntryKey;
import com.slashtube.pricey.Model.ExcelData;
import com.slashtube.pricey.Model.Product;
import com.slashtube.pricey.Repo.CatalogRepo;
import com.slashtube.pricey.Repo.EntryRepo;
import com.slashtube.pricey.Repo.ProductRepo;

@Service
public class CatalogReaderService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private EntryRepo entryRepo;
    @Autowired
    private CatalogRepo catalogRepo;


    public void read(ExcelData excelData) throws IOException {
        Sheet foglio;
        int[] indexes = excelData.getIndexes();
        int startrow = excelData.getStartrow() + 1;
        String file = excelData.getFile().getName();

        String EAN;
        String description;
        double price;
        ArrayList<Product> products = new ArrayList<Product>();
        ArrayList<Entry> entries = new ArrayList<Entry>();
            

        try(Workbook wb = WorkbookFactory.create(excelData.getFile())) {
            foglio = wb.getSheetAt(0);
            foglio.setActiveCell(new CellAddress(startrow, 0));

            Row row = foglio.getRow(startrow++);
            while(row != null) {
                // barcode
                if (row.getCell(indexes[ExcelData.IndexValue.BARCODE.ordinal()]) != null) {
                    switch (row.getCell(indexes[ExcelData.IndexValue.BARCODE.ordinal()]).getCellType()) {
                        case CellType.NUMERIC:
                            EAN = String.format("%.0f", row.getCell(indexes[ExcelData.IndexValue.BARCODE.ordinal()]).getNumericCellValue());
                            break;
                        case CellType.STRING:
                            EAN = row.getCell(indexes[ExcelData.IndexValue.BARCODE.ordinal()]).getStringCellValue();
                            break;
                        default:
                            EAN = null;
                    }

                    if(EAN != null) {
                        // Description
                        description = row.getCell(indexes[ExcelData.IndexValue.DESCRIZIONE.ordinal()]).getStringCellValue();
                        description = filterDescription(description);

                        // price
                        Cell cell = row.getCell(indexes[ExcelData.IndexValue.IVATO.ordinal()]);
                        price = cell.getNumericCellValue();

                        // Insert into DB
                        EntryKey entryKey = new EntryKey(EAN, file);
                        Product product = new Product(EAN, description);
                        Catalog catalog = catalogRepo.findById(file).get();
                        String reference = cell.getAddress().formatAsString();

                        if(!productRepo.existsById(EAN)) {
                            products.add(product);
                        } 

                        Entry entry = new Entry(entryKey,product,catalog, price, reference);
                        entries.add(entry);

                    }

                }
                row = foglio.getRow(startrow++);
            }

            productRepo.saveAll(products);
            entryRepo.saveAll(entries);

        } 
    }

    private String filterDescription(String description) {
        Pattern pattern = Pattern.compile("^[., ]");
        Matcher matcher = pattern.matcher(description);
        
        return matcher.replaceAll("");
    }
    
}
