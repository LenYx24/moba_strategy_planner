package com.leny.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.leny.controller.GamePhaseController;
import com.leny.controller.GamePhaseController.GameState;
import static com.leny.model.AppSettings.windowSize;
import static com.leny.view.Colors.BG_COLOR_DARK;

/**
 * The side menu bar for the game phase
 * This contains the buttons
 */
public class GameSideBar extends JPanel {

    public GameSideBar(GamePhaseController phaseController) {
        Dimension size = new Dimension(120, windowSize.height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setBackground(BG_COLOR_DARK);
        Map<String, SidebarButton> buttons = new HashMap<>();
        buttons.put("draw", new SidebarButton("draw", (ActionEvent event) -> {
            phaseController.setState(GameState.DRAW);
        }, buttons));
        buttons.put("minion", new SidebarButton("minion", (ActionEvent event) -> {
            phaseController.setState(GameState.PLACE_MINION);
        }, buttons));
        buttons.put("ward", new SidebarButton("ward", (ActionEvent event) -> {
            phaseController.setState(GameState.PLACE_WARD);
        }, buttons));
        buttons.put("delete", new SidebarButton("delete", (ActionEvent event) -> {
            phaseController.setState(GameState.DELETE);
        }, buttons));
        buttons.put("save", new SidebarButton("save", (ActionEvent event) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setDialogType(JFileChooser.DIRECTORIES_ONLY);

            int result = fileChooser.showSaveDialog(null);
            if (result == fileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getPath();
                phaseController.saveState(path);
            }
        }, buttons));
        buttons.put("load", new SidebarButton("load", (ActionEvent event) -> {
            String path = GamePhaseController.fileLoader();
            if (path != null) {
                phaseController.loadGameData(path);
                phaseController.complete();
            }
        }, buttons));
        buttons.put("back", new SidebarButton("back", (ActionEvent event) -> {
            phaseController.back();
            phaseController.complete();
        }, buttons));
        List<String> order = Arrays.asList("draw", "minion", "ward", "delete", "save", "load", "back");
        for (String current : order) {
            if (buttons.containsKey(current)) {
                this.add(buttons.get(current));
            }
        }
    }
}
