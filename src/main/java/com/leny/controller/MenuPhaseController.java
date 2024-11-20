package com.leny.controller;

import java.util.Queue;

import com.leny.view.MenuView;

public class MenuPhaseController implements PhaseController{
    Queue<PhaseController> phases;
    MenuView view;
    public MenuPhaseController(Queue<PhaseController> phases){
        this.phases = phases;
        view = new MenuView();
    }
    @Override
    public void setupPhase() {
        view.showGui();
    }
    @Override
    public boolean isDone() {
        return view.isDone();
    }
    
}
