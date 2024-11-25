package com.leny.controller;

import java.util.List;

import javax.swing.JFrame;

import com.leny.model.Champion;
import com.leny.view.GameView;

public class GamePhaseController extends PhaseController {

    GameView view;
    List<Champion> champs;
    GameState state;
    JFrame mainFrame;

    public enum GameState {
        DRAW,
        PLACE_MINION,
        PLACE_WARD
    }

    public GamePhaseController(List<PhaseController> phases, JFrame mainFrame, List<Champion> champs) {
        super(phases);
        view = new GameView(this, mainFrame, champs);
        this.champs = champs;
        this.mainFrame = mainFrame;
        this.state = GameState.DRAW;
    }

    @Override
    public void setupPhase() {
        view.show();
    }

    public void setState(GameState s) {
        state = s;
    }

    public GameState getState() {
        return state;
    }

    @Override
    public void back() {
        phases.clear();
        phases.add(new MenuPhaseController(phases, mainFrame));
    }
}
