package com.leny.controller;

import java.util.List;

import javax.swing.JFrame;

import com.leny.model.Champion;
import com.leny.view.GameView;

public class GamePhaseController extends PhaseController {

    GameView view;
    List<Champion> champs;
    boolean drawState;
    JFrame mainFrame;

    enum GameStates {
        DRAW,
        PLACE_MINION,
        PLACE_WARD
    }

    public GamePhaseController(List<PhaseController> phases, Object done, JFrame mainFrame, List<Champion> champs) {
        super(phases, done);
        view = new GameView(this, mainFrame, champs);
        this.champs = champs;
        this.mainFrame = mainFrame;
    }

    @Override
    public void setupPhase() {
        view.show();
    }

    public void setDrawState(boolean b) {
        drawState = b;
    }

    @Override
    public void back() {
        phases.clear();
        phases.add(new MenuPhaseController(phases, done, mainFrame));
    }
}
