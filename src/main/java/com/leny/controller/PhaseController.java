package com.leny.controller;

import java.util.List;

public abstract class PhaseController {

    protected final Object done;
    protected List<PhaseController> phases;

    protected PhaseController(List<PhaseController> phases, Object done) {
        this.done = done;
        this.phases = phases;
    }

    public void complete() {
        synchronized (done) {
            done.notifyAll();
        }
    }

    public void back() {
        phases.remove(phases.lastIndexOf(this));
    }

    public void setupPhase() {
    }
}
