package com.leny.controller;

import java.util.List;

import javax.swing.JFrame;

import com.leny.model.DataOutput;
import com.leny.view.MenuView;

public class MenuPhaseController extends PhaseController {

    MenuView view;
    JFrame mainFrame;

    public MenuPhaseController(List<PhaseController> phases, JFrame mainFrame) {
        super(phases);
        view = new MenuView(this, mainFrame);
        this.mainFrame = mainFrame;
    }

    public void pushDraftPhase() {
        phases.add(new DraftPhaseController(phases, mainFrame));
    }

    @Override
    public void setupPhase() {
        view.show();
    }

    public void pushGameState(String filePath) {
        phases.add(new GamePhaseController(phases, mainFrame, filePath));
    }
}
