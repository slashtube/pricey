package com.github.slashtube.Utils;

import java.io.File;

/* 
 * Scannerizza la cartella files per rilevare i nomi dei file excel da analizzare.
 */

public class DirScanner {
    final private File [] files;

    public DirScanner() {
        File folder = new File("files/");
        this.files = folder.listFiles();
    }

    public File [] GetFiles() {
        return this.files;
    }

}
