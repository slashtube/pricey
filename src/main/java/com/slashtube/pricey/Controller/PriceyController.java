package com.slashtube.pricey.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class PriceyController {
    final String path;

    public PriceyController() {
        this.path = System.getProperty("user.dir") + "/files" + File.separator;
    }

    
    @PostMapping(value = "/send", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> sendFiles(@RequestParam("file") MultipartFile entity) {
        // File path
        final String upload_path = this.path + entity.getOriginalFilename();

        // Save File 
        try(InputStream finput = entity.getInputStream(); FileOutputStream fout = new FileOutputStream(upload_path)) {
            finput.transferTo(fout);
        }catch(IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error");
        }
        
        return ResponseEntity.ok("200 OK");
    };

    @GetMapping(value = "/catalog", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> getCatalog() {
        return ResponseEntity
            .ok()
            .header("Content-Disposition", "attachment; filename=\"Listino.zip\"")
            .body(out -> {
                ZipOutputStream zipOutputStream = new ZipOutputStream(out);
                addToZip(zipOutputStream);
            });
    }

    private void addToZip(ZipOutputStream zipOutputStream) {
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
