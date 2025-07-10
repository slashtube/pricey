package com.github.slashtube;

import java.io.IOException;

import com.github.slashtube.CatalogoManager.CatalogoReader;
import com.github.slashtube.CatalogoManager.CatalogoWriter;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
            CatalogoReader reader = new CatalogoReader();
            reader.read();

            CatalogoWriter writer = new CatalogoWriter("Listino.xlsx",reader.getCatalogo());
            writer.write();

            
        } catch (IOException e) {
            e.getStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println((double) duration / 1000);



    }


}