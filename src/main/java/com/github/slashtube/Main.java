package com.github.slashtube;

import java.io.IOException;

import com.github.slashtube.CatalogoManager.CatalogoReader;
import com.github.slashtube.CatalogoManager.CatalogoWriter;
import com.github.slashtube.GUI.PriceyBar;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
            PriceyBar.CreateProgressBar(0, 3);
            CatalogoReader reader = new CatalogoReader();
            reader.read();

            CatalogoWriter writer = new CatalogoWriter("Listino.xlsx",reader.getCatalogo());
            writer.write();

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            PriceyBar.setStatus("Finito in " + (double) duration / 1000 + "s");

            
        } catch (IOException e) {
            e.getStackTrace();
        }




    }


}