package com.leny.controller;

import java.awt.Image;
import java.util.List;

import javax.swing.JFrame;

import com.leny.model.Champion;
import com.leny.model.Draft;
import com.leny.model.Draft.ChampsList;
import com.leny.model.Draft.Group;
import com.leny.view.ChampImageBox;
import com.leny.view.DraftView;

public class DraftPhaseController extends PhaseController {

    DraftView view;
    Draft draft;
    JFrame mainFrame;

    public DraftPhaseController(List<PhaseController> phases, Object done, JFrame mainFrame) {
        super(phases, done);
        view = new DraftView(this, mainFrame);
        this.mainFrame = mainFrame;
        this.draft = new Draft(this);
    }

    @Override
    public void setupPhase() {
        view.show();
    }

    public void pushGamePhase() {
        phases.add(new GamePhaseController(phases, done, mainFrame, draft.getFinalChampList()));
    }

    public List<Champion> getAllChamps() {
        return draft.getAllChamps();
    }

    public List<ChampImageBox> getAllChampsIcons() {
        return draft.getAllChampsIcons();
    }

    public void next(ChampImageBox box) {
        draft.next(box.getChamp());
    }

    public ChampsList getChampsGroups(Draft.Group group) {
        return draft.getChampsGroups(group);
    }

    public Group getCurrentGroup() {
        return draft.getCurrentGroup();
    }

    public void updateChampsPanel(Group group) {
        view.updateChampsPanel(group);
    }

    public ChampImageBox findImageBoxByChamp(Champion champ) {
        return draft.findImageBoxByChamp(champ);
    }

    public void checkDone() {
        if (draft.isDone()) {
            this.pushGamePhase();
            this.complete();
        }
    }

}
