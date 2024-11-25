package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.leny.controller.GamePhaseController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Champion;

public class GameDataBar extends JPanel {

    GamePhaseController phaseController;
    Champion selectedChamp;
    JTextField goldArea;

    public GameDataBar(GamePhaseController phaseController) {
        Dimension size = new Dimension(300, windowSize.height);
        setMaximumSize(size);
        setBackground(new Color(46, 50, 58));
        this.setLayout(new GridLayout(0, 2));
        this.phaseController = phaseController;
    }

    class TextChangedAdapter implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        private void update(DocumentEvent e) {
            String text = goldArea.getText();
            if (text.isEmpty()) {
                return;
            }
            System.out.println("typed");
            try {
                selectedChamp.setGold(Integer.parseInt(text));
            } catch (NumberFormatException err) {
                System.out.println("wrong format");
            }
        }
    }

    public void update(Champion champ) {
        this.removeAll();
        selectedChamp = champ;

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);

        this.add(LabelFactory.createDataLabel("name: "), gbc);
        this.add(LabelFactory.createDataLabel(champ.getName()), gbc);

        this.add(LabelFactory.createDataLabel("gold: "), gbc);
        goldArea = new JTextField(Integer.toString(champ.getGold()));
        goldArea.getDocument().addDocumentListener(new TextChangedAdapter());
        this.add(goldArea, gbc);
        this.revalidate();
    }
}
