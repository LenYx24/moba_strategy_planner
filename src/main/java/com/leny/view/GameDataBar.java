package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.leny.controller.GamePhaseController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Champion;

public class GameDataBar extends JPanel {

    GamePhaseController phaseController;
    Champion selectedChamp;

    public GameDataBar(GamePhaseController phaseController) {
        Dimension size = new Dimension(300, windowSize.height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setBackground(new Color(46, 50, 58));
        this.setLayout(new GridLayout(0, 2));
        this.phaseController = phaseController;
    }

    class AreaChangedListener extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            JTextArea area = (JTextArea) e.getSource();
            if (area.getText().isEmpty()) {
                return;
            }
            selectedChamp.setGold(Integer.parseInt(area.getText()));
        }

    }

    public void update(Champion champ) {
        this.removeAll();
        selectedChamp = champ;
        this.add(LabelFactory.createDataLabel("name: "));
        this.add(LabelFactory.createDataLabel(champ.getName()));

        this.add(LabelFactory.createDataLabel("gold: "));
        JTextArea goldArea = new JTextArea(Integer.toString(champ.getGold()));
        goldArea.addKeyListener(new AreaChangedListener());
        this.add(goldArea);
        this.revalidate();
    }
}
