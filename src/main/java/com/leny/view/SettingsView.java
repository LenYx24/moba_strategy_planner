package com.leny.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.leny.controller.SettingsPhaseController;
import static com.leny.model.AppSettings.windowSize;

public class SettingsView {

    SettingsPhaseController phaseController;
    JFrame mainFrame;

    public SettingsView(SettingsPhaseController phaseController, JFrame mainFrame) {
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
            JButton exitBtn = new JButton("Back");

            Dimension btnSize = new Dimension(400, 150);

            exitBtn.setMaximumSize(btnSize);
            exitBtn.addActionListener((ActionEvent event) -> {
                phaseController.complete();
            });

            exitBtn.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));

            exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(title);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(javax.swing.Box.createVerticalStrut(40));
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
