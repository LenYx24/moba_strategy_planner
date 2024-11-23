package com.leny.controller;

import java.util.List;

import javax.swing.JFrame;

import com.leny.view.DraftView;

public class DraftPhaseController extends PhaseController {

    DraftView view;
    JFrame mainFrame;

    public DraftPhaseController(List<PhaseController> phases, Object done, JFrame mainFrame) {
        super(phases, done);
        view = new DraftView(this, mainFrame);
        this.mainFrame = mainFrame;
    }

    @Override
    public void setupPhase() {
        view.show();
    }

    public void pushGamePhase() {
        phases.add(new GamePhaseController(phases, done, mainFrame));
    }
}
