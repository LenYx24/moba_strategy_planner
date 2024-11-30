package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.leny.controller.GamePhaseController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Champion;
import com.leny.model.Entity;

public class GameDataBar extends JPanel {

    GamePhaseController phaseController;
    Champion selectedChamp;
    Entity selectedEntity;
    GridBagConstraints gbc;

    public GameDataBar(GamePhaseController phaseController) {
        Dimension size = new Dimension(300, windowSize.height);
        setMaximumSize(size);
        setBackground(new Color(46, 50, 58));
        this.setLayout(new GridLayout(0, 2));
        this.phaseController = phaseController;
    }
    private void bindField(JTextField textField, Consumer<Integer> onChange) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                notifyChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                notifyChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                notifyChange();
            }

            private void notifyChange() {
                String text = textField.getText();
                if (text.isEmpty()) {
                    return;
                }
                try {
                    onChange.accept(Integer.parseInt(textField.getText()));
                } catch (NumberFormatException err) {
                    System.out.println("wrong format");
                }
            }
        });
    }

    public void update(Champion champ) {
        this.removeAll();
        selectedChamp = champ;

        addLabelAndValue("name: ", champ.getName());

        addLabel("gold value: ");
        JTextField goldArea = new JTextField(Integer.toString(champ.getGoldValue()));
        bindField(goldArea, (Integer text) -> {
            selectedChamp.setGoldValue(text);
        });
        this.add(goldArea, gbc);

        addLabel("strength: ");
        JTextField strengthArea = new JTextField(Integer.toString(champ.getStrength()));
        bindField(strengthArea, (Integer text) -> {
            selectedChamp.setStrength(text);
        });
        this.add(strengthArea, gbc);

        addLabel("level: ");
        JTextField levelArea = new JTextField(Integer.toString(champ.getLevel()));
        bindField(levelArea, (Integer text) -> {
            selectedChamp.setLevel(text);
        });
        this.add(levelArea, gbc);

        this.revalidate();
    }

    private void addLabel(String name) {
        this.add(LabelFactory.createDataLabel(name), gbc);
    }

    private void addLabelAndValue(String name, String value) {
        this.add(LabelFactory.createDataLabel(name), gbc);
        this.add(LabelFactory.createDataLabel(value), gbc);
    }

    private GridBagConstraints getGbc() {
        GridBagConstraints myGbc = new GridBagConstraints();

        myGbc.fill = GridBagConstraints.HORIZONTAL;
        myGbc.weightx = 1.0;
        myGbc.weighty = 0;
        myGbc.gridx = 0;
        myGbc.gridy = GridBagConstraints.RELATIVE;
        myGbc.insets = new Insets(5, 5, 5, 5);
        return myGbc;
    }

    public void update(Entity entity) {
        this.removeAll();
        selectedEntity = entity;

        GridBagConstraints gbc = getGbc();

        addLabelAndValue("name: ", entity.getName());

        addLabel("gold value: ");
        JTextField goldArea = new JTextField(Integer.toString(entity.getGoldValue()));
        bindField(goldArea, (Integer text) -> {
            selectedEntity.setGoldValue(text);
        });
        this.add(goldArea, gbc);
        this.revalidate();
    }
}
