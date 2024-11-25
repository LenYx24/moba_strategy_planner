package com.leny.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.leny.controller.MenuPhaseController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.ButtonFactory;

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
            panel.setBackground(new Color(56, 61, 69));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);

            BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

            panel.setLayout(boxLayout);

            JLabel title = new JLabel("LoL Macro Marker");
            title.setForeground(Color.white);
            String fontFamily = "Helvetica";
            title.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));

            JButton startBtn = ButtonFactory.createMainMenuButton("Start");
            JButton loadBtn = ButtonFactory.createMainMenuButton("Load State");
            JButton exitBtn = ButtonFactory.createMainMenuButton("Exit");

            startBtn.setBackground(new Color(240, 0, 0));

            startBtn.addActionListener((ActionEvent event) -> {
                phaseController.pushDraftPhase();
                phaseController.complete();
            });
            loadBtn.addActionListener((ActionEvent event) -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setDialogType(JFileChooser.FILES_ONLY);

                int result = fileChooser.showSaveDialog(null);
                if (result == fileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getPath();
                    phaseController.pushGameState(path);
                }
            });
            exitBtn.addActionListener((ActionEvent event) -> {
                phaseController.back();
                phaseController.complete();
            });

            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(javax.swing.Box.createVerticalStrut(50));
            panel.add(title);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(startBtn);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(loadBtn);
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
