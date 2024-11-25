package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import com.leny.controller.GamePhaseController;
import com.leny.controller.GamePhaseController.GameState;
import static com.leny.model.AppSettings.windowSize;
import static com.leny.view.Colors.BG_COLOR_DARK;

public class GameSideBar extends JPanel {

    public GameSideBar(GamePhaseController phaseController) {
        Dimension size = new Dimension(120, windowSize.height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setBackground(BG_COLOR_DARK);
        SidebarButton drawState = new SidebarButton("draw");
        SidebarButton addMinionBtn = new SidebarButton("minion");
        SidebarButton addWardBtn = new SidebarButton("ward");
        SidebarButton deleteBtn = new SidebarButton("delete");
        SidebarButton backBtn = new SidebarButton("Back");
        SidebarButton selected = null;
        backBtn.setBackground(new Color(240, 0, 0));

        drawState.addActionListener((ActionEvent event) -> {
            phaseController.setState(GameState.DRAW);
        });
        addMinionBtn.addActionListener((ActionEvent event) -> {
            phaseController.setState(GameState.PLACE_MINION);
        });
        addWardBtn.addActionListener((ActionEvent event) -> {
            phaseController.setState(GameState.PLACE_WARD);
        });
        deleteBtn.addActionListener((ActionEvent event) -> {
            phaseController.setState(GameState.PLACE_WARD);
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
