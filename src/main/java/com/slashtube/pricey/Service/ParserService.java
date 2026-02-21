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

import lombok.Getter;

@Service
public class ParserService {
    @Getter
    final private int [] indexes;
    @Getter
    private Sheet foglio;
    @Getter
    private int startrow;

    private enum IndexValue  {
        BARCODE,
        DESCRIZIONE,
        IVATO,
    };

    public ParserService() {
        this.indexes = new int[3];
        this.foglio = null;
        this.startrow = 0;
    }

    public void parse(File file) throws IOException {
        Workbook wb = WorkbookFactory.create(file);
        int riemp = 0;
        int occ = 0;
        int alt_barcode = 0; // If no barcode field was found, fallbacks to this value

        this.foglio = wb.getSheetAt(0);

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
                            this.indexes[IndexValue.BARCODE.ordinal()] = cell.getColumnIndex();
                            riemp++;
                            break;
                        case "descrizione":
                            this.indexes[IndexValue.DESCRIZIONE.ordinal()] = cell.getColumnIndex();
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
                            this.indexes[IndexValue.IVATO.ordinal()] = cell.getColumnIndex();
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
                if(this.indexes[IndexValue.BARCODE.ordinal()] <= 0) {
                    this.indexes[IndexValue.BARCODE.ordinal()] = alt_barcode;
                    riemp++;
                } 
                this.startrow = row.getRowNum();
            }
        }
    }
    
}
