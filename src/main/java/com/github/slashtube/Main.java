package com.github.slashtube;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.github.slashtube.CatalogoManager.CatalogoReader;
import com.github.slashtube.CatalogoManager.CatalogoWriter;
import com.github.slashtube.GUI.PriceyBar;

public class Main {
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            PriceyBar.CreateProgressBar(0, 3);
            CatalogoReader reader = new CatalogoReader();
            reader.read();

            CatalogoWriter writer = new CatalogoWriter("Listino.xlsx",reader.getCatalogo());
            writer.write();

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            PriceyBar.setStatus("Finito in " + (double) duration / 1000 + "s");

            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore nell'apertura/scrittura dei file", "Errore", JOptionPane.ERROR_MESSAGE);
        } 

    }


}