package com.leny;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import com.leny.controller.MenuPhaseController;
import com.leny.controller.PhaseController;
import com.leny.model.AppSettings;
import static com.leny.model.AppSettings.windowSize;

public class App {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("LoL Macro Marker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(windowSize);
        mainFrame.setLocation(AppSettings.getWindowPosCentered(windowSize));

        Object done = new Object();

        List<PhaseController> phases = new LinkedList<>();

        MenuPhaseController menuPhase = new MenuPhaseController(phases, done, mainFrame);
        phases.add(menuPhase);

        PhaseController currentPhaseController = menuPhase;

        while (!phases.isEmpty()) {
            currentPhaseController = phases.get(phases.size() - 1);
            currentPhaseController.setupPhase();

            synchronized (done) {
                try {
                    done.wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
            System.out.println("Phase done: moving to the next one");
        }
        mainFrame.dispose();
    }
}
