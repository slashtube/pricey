package com.slashtube.pricey.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
public class AddToZipService {
    @Setter
    private String path;

    public void add(ZipOutputStream zipOutputStream) {
        // Get files to add
        final String[] filenames = new File(this.path).list();

        try {
            for(String filename : filenames) {
                File file = new File(this.path + filename);
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);

                IOUtils.copy(fileInputStream, zipOutputStream);

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }

            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);

        }catch(IOException e) {
            e.printStackTrace();
        } 

    }
    
}
