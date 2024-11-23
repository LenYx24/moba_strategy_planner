package com.leny.controller;

import java.util.List;

import javax.swing.JFrame;

import com.leny.view.GameView;

public class GamePhaseController extends PhaseController {

    GameView view;

    public GamePhaseController(List<PhaseController> phases, Object done, JFrame mainFrame) {
        super(phases, done);
        view = new GameView(this, mainFrame);
    }

    @Override
    public void setupPhase() {
        view.show();
    }
}
