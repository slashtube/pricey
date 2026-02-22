package com.slashtube.pricey.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.slashtube.pricey.Model.ExcelData;

import lombok.Getter;

@Service
public class ParserService {
    @Getter
    ExcelData excelData;

    public void parse(File file) throws IOException {
        Workbook wb = WorkbookFactory.create(file);
        int riemp = 0;
        int occ = 0;
        int alt_barcode = 0; // If no barcode field was found, fallbacks to this value

        int [] indexes = new int[3];
        Sheet foglio = null;
        int startrow = 0;


        foglio = wb.getSheetAt(0);

        Iterator<Row> iterator = foglio.iterator();
        Row row;

        while(riemp < 3 && iterator.hasNext()) {
            row = iterator.next();

            for(var cell : row) {
                if(cell != null && cell.getCellType() == CellType.STRING ) {
                    String value = cell.getStringCellValue().toLowerCase();
                    switch(value) {
                        case "ean":
                        case "barcode":
                        case "codice ean":
                            indexes[ExcelData.IndexValue.BARCODE.ordinal()] = cell.getColumnIndex();
                            riemp++;
                            break;
                        case "descrizione":
                            indexes[ExcelData.IndexValue.DESCRIZIONE.ordinal()] = cell.getColumnIndex();
                            riemp++;
                            break;
                        // considers only the first occurence of 'ivato'
                        case "ivato":
                            if(occ >= 1) {
                                break;
                            }
                        // overwrites the 'ivato' field if it finds a sale offer
                        case "prezzo offerta":
                        case "ivato sc.":
                            indexes[ExcelData.IndexValue.IVATO.ordinal()] = cell.getColumnIndex();
                            riemp++;
                            occ++;
                            break;
                        case "codice":
                            alt_barcode = cell.getColumnIndex();
                            break;
                    }
                }
            }

            // If no 'ean', 'barcode' or 'codice ean' was found fallbacks to the alt_barcode
            if(riemp >= 2) {
                if(indexes[ExcelData.IndexValue.BARCODE.ordinal()] <= 0) {
                    indexes[ExcelData.IndexValue.BARCODE.ordinal()] = alt_barcode;
                    riemp++;
                } 
                startrow = row.getRowNum();
            }
        }

        this.excelData = new ExcelData(indexes, foglio, startrow, file);
    }
    
}
