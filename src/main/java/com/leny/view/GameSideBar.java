package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import com.leny.controller.GamePhaseController;
import static com.leny.model.AppSettings.windowSize;

public class GameSideBar extends JPanel {

    public GameSideBar(GamePhaseController phaseController) {
        Dimension size = new Dimension(120, windowSize.height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setBackground(new Color(46, 50, 58));
        SidebarButton drawState = new SidebarButton("draw");
        SidebarButton addMinionBtn = new SidebarButton("minion");
        SidebarButton addWardBtn = new SidebarButton("ward");
        SidebarButton backBtn = new SidebarButton("Back");

        backBtn.setBackground(new Color(240, 0, 0));

        drawState.addActionListener((ActionEvent event) -> {
            phaseController.setDrawState(true);
        });
        backBtn.addActionListener((ActionEvent event) -> {
            phaseController.back();
            phaseController.complete();
        });
        this.add(drawState);
        this.add(addMinionBtn);
        this.add(addWardBtn);
        this.add(backBtn);
    }
}
