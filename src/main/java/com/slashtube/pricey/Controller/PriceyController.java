package com.slashtube.pricey.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.slashtube.pricey.Model.Catalog;
import com.slashtube.pricey.Model.ExcelData;
import com.slashtube.pricey.Repo.CatalogRepo;
import com.slashtube.pricey.Service.*;


@RestController
public class PriceyController {

    @Autowired
    private ParserService parserservice;

    @Autowired
    private FileScannerService fileScannerService;

    @Autowired
    private CatalogReaderService catalogReaderService;

    @Autowired
    private CatalogWriterService catalogWriterService;

    @Autowired
    private AddToZipService addToZipService; 

    @Autowired
    private CatalogRepo catalogRepo;

    final String path;

    public PriceyController() {
        this.path = System.getProperty("user.dir") + "/Listino" + File.separator;
    }

    
    @PostMapping(value = "/send", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> sendFiles(@RequestParam("file") MultipartFile[] files, @RequestParam("origin") String[] Origin) {
        int i = 0;
        for (MultipartFile file : files) {
            // File path
            final String upload_path = this.path + file.getOriginalFilename();

            Catalog catalog = new Catalog(file.getOriginalFilename(), Origin[i]);
            catalogRepo.save(catalog);

            // Save File 
            try(InputStream finput = file.getInputStream(); FileOutputStream fout = new FileOutputStream(upload_path)) {
                finput.transferTo(fout);
            }catch(IOException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error");
            }
            i++;
        }
        
        return ResponseEntity.ok("Successfully uploaded files");
    };

    @GetMapping(value = "sort")
    public ResponseEntity<String> getMethodName() {
        // Read Files
        fileScannerService.setPath(this.path);
        final File[] files = fileScannerService.getFileList();

        try {
            for (File file : files) {
                // Parse catalogs
                parserservice.parse(file);
                ExcelData excelData = parserservice.getExcelData();

                // Store products in database
                catalogReaderService.read(excelData);


            }         
            // Create ordered catalog
            catalogWriterService.setPath(this.path);
            catalogWriterService.write();

        }catch(IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("");
        } catch(NullPointerException e) {
            return ResponseEntity.badRequest().body("No files uploaded");
        }
        return ResponseEntity.ok("Successfully sorted files");
    }
    

    @GetMapping(value = "/catalog", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> getCatalog() {
        this.addToZipService.setPath(this.path);

        // Return catalog
        return ResponseEntity
            .ok()
            .header("Content-Disposition", "attachment; filename=\"Listino.zip\"")
            .body(out -> {
                ZipOutputStream zipOutputStream = new ZipOutputStream(out);
                addToZipService.add(zipOutputStream);
            });
    }
    
}
