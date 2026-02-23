package com.slashtube.pricey.Service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slashtube.pricey.Repo.CatalogRepo;
import com.slashtube.pricey.Repo.EntryRepo;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@Setter
public class CleanupService {
    private String path;

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private CatalogRepo catalogRepo;

    public void cleanup() {
        try {
            FileUtils.cleanDirectory(new File(this.path));
            entryRepo.deleteAll();
            catalogRepo.deleteAll();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }


    
}
