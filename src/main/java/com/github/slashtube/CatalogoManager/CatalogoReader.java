package com.github.slashtube.CatalogoManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;

import com.github.slashtube.ProdottoManager.Prodotto;
import com.github.slashtube.ProdottoManager.ProdottoEntry;
import com.github.slashtube.Utils.DirScanner;
import com.github.slashtube.Utils.ExcelParser;

public class CatalogoReader {
    private File[] files;
    final private Catalogo catalogo;

    public CatalogoReader() {
        this.files = null;
        this.catalogo = new Catalogo();
    }

    public void read() throws IOException {
        DirScanner scanner = new DirScanner();
        this.files = scanner.GetFiles();

        ExcelParser parser = new ExcelParser();
        String barcode, descrizione;
        Double ivato;

        Sheet foglio;
        int nocode = 0;

        HashMap<String, Prodotto> list = this.catalogo.getList();

        for (File file : this.files) {
            // Lettura dati parser
            parser.parse(file);
            int[] indexes = parser.getIndexes();
            int startrow = parser.getStartRow() + 1;
            Workbook wb = WorkbookFactory.create(file);
            foglio = wb.getSheetAt(0);

            // Setup iterazione workbook
            foglio.setActiveCell(new CellAddress(startrow, 0));

            Row row = foglio.getRow(startrow++);

            while (row != null) {
                // Lettura barcode
                if (row.getCell(indexes[0]) != null) {
                    switch (row.getCell(indexes[0]).getCellType()) {
                        case CellType.NUMERIC:
                            barcode = String.format("%.0f", row.getCell(indexes[0]).getNumericCellValue());
                            break;
                        case CellType.STRING:
                            barcode = row.getCell(indexes[0]).getStringCellValue();
                            if(barcode.equals(".") || barcode.equals("-")) {
                                barcode = String.format("N%d", nocode++);
                            }
                            break;
                        default:
                            barcode = null;
                    }

                    if (barcode != null) {
                        // Lettura descrizione
                        descrizione = row.getCell(indexes[1]).getStringCellValue();

                        // lettura ivato
                        Cell cell = row.getCell(indexes[2]);
                        ivato = cell.getNumericCellValue();

                        if (list.containsKey(barcode)) {
                            list.get(barcode).AddEntry(file, ivato, new CellReference(cell));
                        } else {
                            ProdottoEntry entry = new ProdottoEntry(file, ivato, new CellReference(cell));
                            Prodotto p = new Prodotto(barcode, descrizione, entry);
                            list.put(barcode, p);
                        }
                    }

                }

                row = foglio.getRow(startrow++);
            }
        }

    }

    public Catalogo getCatalogo() {
        return this.catalogo;
    }

}
