package com.slashtube.pricey.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin
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
    private CleanupService cleanupService;

    @Autowired
    private AddToZipService addToZipService; 

    @Autowired
    private CatalogRepo catalogRepo;

    final String path;
    String origin;

    public PriceyController() {
        this.path = System.getProperty("user.dir") + File.separator;
        this.origin = "";
    }

    @PostMapping("/sendOrigin")
    public ResponseEntity<String> sendOrigin(@RequestBody String origin) {
        this.origin = origin;
        
        return ResponseEntity.ok("Origine letta con successo");
    }
    

    
    @PostMapping(value = "/send", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> sendFiles(@RequestParam("file") MultipartFile[] files) {
        for (MultipartFile file : files) {
            // File path
            final String upload_path = this.path + "files/" + file.getOriginalFilename();

            Catalog catalog = new Catalog(file.getOriginalFilename(), this.origin + file.getOriginalFilename());
            catalogRepo.save(catalog);

            // Save File 
            try(InputStream finput = file.getInputStream(); FileOutputStream fout = new FileOutputStream(upload_path)) {
                finput.transferTo(fout);
            }catch(IOException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error");
            }
        }
        
        return ResponseEntity.ok("Successfully uploaded files");
    };

    @GetMapping(value = "sort")
    public ResponseEntity<String> getMethodName() {
        // Read Files
        fileScannerService.setPath(this.path + "files/");
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
            catalogWriterService.setPath(this.path + "Listino/");
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
        cleanupService.setPath(this.path + "files/");
        cleanupService.cleanup();

        this.addToZipService.setPath(this.path + "Listino/");

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
