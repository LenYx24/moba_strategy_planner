package com.leny.controller;

import java.util.List;

/**
 * The base class for phase controllers
 * Every phasecontroller should derive from this class
 * It has access to the programs main phase queue
 * It can single that its phase has ended,
 * and it can change the phases queue completely
 * This freedom is given to make development in the future as easy as possible,
 * because a current phase should have the knowledge to change what comes after it
 */
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
