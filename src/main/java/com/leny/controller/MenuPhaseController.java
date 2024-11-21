package com.leny.controller;

import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;

import com.leny.view.MenuView;

public class MenuPhaseController extends PhaseController {

    MenuView view;
    JFrame mainFrame;

    public MenuPhaseController(List<PhaseController> phases, Object done, JFrame mainFrame) {
        super(phases, done);
        view = new MenuView(this, mainFrame);
        this.mainFrame = mainFrame;
    }

    public void pushDraftPhase() {
        phases.add(new DraftPhaseController(phases, done, mainFrame));
    }

    @Override
    public void setupPhase() {
        view.show();
    }
}
