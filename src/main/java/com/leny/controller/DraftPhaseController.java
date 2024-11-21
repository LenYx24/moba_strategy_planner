package com.leny.controller;

import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;

import com.leny.view.DraftView;

public class DraftPhaseController extends PhaseController {

    DraftView view;

    public DraftPhaseController(List<PhaseController> phases, Object done, JFrame mainFrame) {
        super(phases, done);
        view = new DraftView(this, mainFrame);
    }

    @Override
    public void setupPhase() {
        view.show();
    }

}
