package com.slashtube.pricey.Service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class FileScannerService {
    final String path;

    public FileScannerService() {
        this.path = System.getProperty("user.dir") + "/files" + File.separator;
    }

    public File[] getFileList() {
        return (new File(this.path).listFiles());
    }


    
}
