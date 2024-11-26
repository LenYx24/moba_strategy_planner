package com.leny;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;
import com.leny.controller.MenuPhaseController;
import com.leny.controller.PhaseController;
import com.leny.model.AppSettings;
import static com.leny.model.AppSettings.windowSize;

public class App {

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Unsupported look and feel");
        }
        JFrame mainFrame = new JFrame("LoL Macro Marker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(windowSize);
        mainFrame.setLocation(AppSettings.getWindowPosCentered(windowSize));

        List<PhaseController> phases = new LinkedList<>();

        MenuPhaseController menuPhase = new MenuPhaseController(phases, mainFrame);
        phases.add(menuPhase);

        PhaseController currentPhaseController;

        while (!phases.isEmpty()) {
            currentPhaseController = phases.get(phases.size() - 1);
            currentPhaseController.setupPhase();

            synchronized (phases) {
                try {
                    phases.wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        }
        mainFrame.dispose();
    }
}
