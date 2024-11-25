package com.leny.controller;

import java.util.List;

public abstract class PhaseController {

    protected final List<PhaseController> phases;

    protected PhaseController(List<PhaseController> phases) {
        this.phases = phases;
    }

    public void complete() {
        synchronized (phases) {
            phases.notifyAll();
        }
    }

    public void back() {
        phases.remove(phases.lastIndexOf(this));
    }

    public void setupPhase() {
    }
}
