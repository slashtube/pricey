package com.slashtube.pricey.Model;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExcelData {
    final private int [] indexes;
    private Sheet foglio;
    private int startrow;
    private File file;

    static public enum IndexValue  {
        BARCODE,
        DESCRIZIONE,
        IVATO,
    };

}
