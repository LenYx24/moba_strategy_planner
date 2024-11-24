package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.leny.controller.PhaseController;
import static com.leny.model.AppSettings.windowSize;

public class DraftSideBar extends JPanel {

    public DraftSideBar(PhaseController phaseController) {
        Dimension size = new Dimension(200, windowSize.height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setBackground(new Color(46, 50, 58));

        JButton backBtn = new JButton("Back");

        backBtn.setBackground(new Color(240, 0, 0));
        Dimension btnSize = new Dimension(170, 170);
        backBtn.setMaximumSize(btnSize);

        backBtn.addActionListener((ActionEvent event) -> {
            System.out.println("Going back");
            phaseController.back();
            phaseController.complete();
        });
        this.add(backBtn);
    }
}
