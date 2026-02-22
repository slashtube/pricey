package com.slashtube.pricey.Service;

import java.io.File;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class FileScannerService {
    String path;

    public File[] getFileList() {
        return (new File(this.path).listFiles());
    }


    
}
