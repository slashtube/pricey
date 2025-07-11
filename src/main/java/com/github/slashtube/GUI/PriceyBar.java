package com.github.slashtube.GUI;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class PriceyBar {
    static private int progress = 0;
    final static private JProgressBar progressbar = new JProgressBar(); 
    final static private JLabel status = new JLabel();


    public static void increaseProgress() {
        progress++;
        progressbar.setValue(progress);
    }
    
    public static void CreateProgressBar(int minvalue, int maxvalue) {
        progressbar.setMinimum(minvalue);
        progressbar.setMaximum(maxvalue);
        progressbar.setStringPainted(true);

        createFrame();

    }

    public static void setStatus(String newstatus) {
        status.setText(newstatus);
    }

    private static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,30,20));

        panel.add(status, BorderLayout.CENTER);
        panel.add(progressbar, BorderLayout.SOUTH);

        return panel;

    }

    private static void createFrame() {
        JFrame frame = new JFrame("Pricey");
        BorderLayout layout = new BorderLayout(5, 5);
        JPanel panel = createPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(layout);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Se settato a null la finestra compare sempre al centro dello schermo

        frame.setVisible(true);

    }

    

}
