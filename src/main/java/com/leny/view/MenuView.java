package com.leny.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.leny.controller.MenuPhaseController;
import static com.leny.model.AppSettings.windowSize;

public class MenuView {

    MenuPhaseController phaseController;
    JFrame mainFrame;

    public MenuView(MenuPhaseController phaseController, JFrame mainFrame) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel();

            mainFrame.setBackground(new Color(200, 200, 200));
            panel.setBackground(new Color(240, 240, 240));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);

            BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

            panel.setLayout(boxLayout);

            JLabel title = new JLabel("LoL Macro Marker");
            String fontFamily = "Helvetica";
            title.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));

            JButton startBtn = new JButton("Start");
            JButton settingsBtn = new JButton("Settings");
            JButton exitBtn = new JButton("Exit");

            startBtn.setBackground(new Color(240, 0, 0));
            Dimension btnSize = new Dimension(400, 150);

            startBtn.setMaximumSize(btnSize);
            settingsBtn.setMaximumSize(btnSize);
            exitBtn.setMaximumSize(btnSize);

            startBtn.addActionListener((ActionEvent event) -> {
                System.out.println("pushing state");
                phaseController.pushDraftPhase();
                phaseController.complete();
            });
            settingsBtn.addActionListener((ActionEvent event) -> {
                phaseController.pushDraftPhase();
                phaseController.complete();
            });
            exitBtn.addActionListener((ActionEvent event) -> {
                phaseController.back();
                phaseController.complete();
            });

            startBtn.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));
            settingsBtn.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));
            exitBtn.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));

            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.red),
                    startBtn.getBorder()));

            startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            settingsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(title);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(startBtn);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(settingsBtn);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(exitBtn);

            mainFrame.setContentPane(panel);
            mainFrame.setVisible(true);
            mainFrame.setPreferredSize(windowSize);
            mainFrame.pack();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
