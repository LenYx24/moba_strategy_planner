/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leny.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.leny.controller.DraftPhaseController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Champion;
import com.leny.model.Draft.ChampsList;
import com.leny.model.Draft.Group;
import com.leny.model.Loader;
import static com.leny.view.Colors.BG_COLOR;

/**
 * The UI for the draft state
 */
public class DraftView {

    DraftPhaseController phaseController;
    JFrame mainFrame;
    ChampImageBox selectedChamp;
    ChampListPanel champListPanel;
    private ChampsPanel pickPanelBlue;
    private ChampsPanel pickPanelRed;
    private ChampsPanel banPanelBlue;
    private ChampsPanel banPanelRed;
    JPanel draftPanel;
    Border defaultBorder;

    public DraftView(DraftPhaseController phaseController, JFrame mainFrame) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
        defaultBorder = BorderFactory.createStrokeBorder(new BasicStroke());
    }

    public void updateChampsPanel(Group group) {
        switch (group) {
            case BLUEPICK: {
                if(pickPanelBlue != null){
                    pickPanelBlue.update();
                }
                break;
            }
            case REDPICK: {
                if(pickPanelRed != null){
                    pickPanelRed.update();
                }
                break;
            }
            case BLUEBAN: {
                if(banPanelBlue != null){
                    banPanelBlue.update();
                }
                break;
            }
            case REDBAN: {
                if(banPanelRed != null){
                    banPanelRed.update();
                }
                break;
            }
        }
        if(champListPanel != null){
            champListPanel.update();
        }
    }

    class ChampsPanel extends JPanel {

        ImageBox[] champBoxes;
        ChampsList champsList;
        int axis;
        Image unknownIcon;

        public ChampsPanel(Image unknownIcon, int axis, ChampsList champsList) {
            this.champsList = champsList;
            this.unknownIcon = unknownIcon;
            champBoxes = new ImageBox[5];
            this.setBackground(BG_COLOR);
            this.axis = axis;
            for (int i = 0; i < champBoxes.length; i++) {
                ImageBox imgBox = new ImageBox(unknownIcon);

                this.add(imgBox);
                if (axis == BoxLayout.Y_AXIS) {
                    this.add(javax.swing.Box.createVerticalStrut(5));
                }
                if (axis == BoxLayout.X_AXIS) {
                    this.add(javax.swing.Box.createHorizontalStrut(5));
                }
                champBoxes[i] = imgBox;
            }
            this.setLayout(new BoxLayout(this, axis));
        }

        private void draw() {
            for (ImageBox champBox : champBoxes) {
                if (champBox == null) {
                    continue;
                }
                this.add(champBox);
                if (axis == BoxLayout.Y_AXIS) {
                    this.add(javax.swing.Box.createVerticalStrut(5));
                }
                if (axis == BoxLayout.X_AXIS) {
                    this.add(javax.swing.Box.createHorizontalStrut(5));
                }
            }
        }

        public void update() {
            int index = champsList.getLength() - 1;
            Champion last = champsList.last();
            if (last == null || index < 0) {
                return;
            }
            champBoxes[index] = phaseController.findImageBoxByChamp(last);
            this.removeAll();
            draw();
            this.validate();
        }
    }

    class ChampListPanel extends JPanel {

        private List<ChampImageBox> allChampsIcons;
        JPanel inside = new JPanel();

        public ChampListPanel(List<ChampImageBox> allChampsIcons) {
            this.allChampsIcons = allChampsIcons;
            this.setLayout(new BorderLayout());

            inside.setBackground(new Color(1, 37, 43));
            inside.setPreferredSize(new Dimension(300, 800));
            inside.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            addBoxes();
            for (ChampImageBox box : allChampsIcons) {
                box.addMouseListener(new ChampSelectListener());
            }

            JScrollPane scrollPane = new JScrollPane(inside);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            this.add(scrollPane, BorderLayout.CENTER);
        }

        public void update() {
            this.validate();
        }

        private void addBoxes() {
            for (ChampImageBox box : allChampsIcons) {
                box.setBorder(defaultBorder);
                box.setBounds(0,0,100,100);
                inside.add(box);
            }
        }

        public void remove(ChampImageBox box) {
            allChampsIcons.remove(box);
            inside.remove(box);
            inside.revalidate();
        }

        public boolean contains(ChampImageBox box) {
            return allChampsIcons.contains(box);
        }
    }

    class ChampSelectListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            ChampImageBox champImageBox = (ChampImageBox) e.getSource();
            if (selectedChamp != null) {
                selectedChamp.setBorder(defaultBorder);
            }

            selectedChamp = champImageBox;
            selectedChamp.setBorder(BorderFactory.createLineBorder(new Color(181, 162, 121)));
        }
    }

    class LockInListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (selectedChamp == null || !champListPanel.contains(selectedChamp)) {
                return;
            }
            phaseController.next(selectedChamp);
            champListPanel.remove(selectedChamp);
            phaseController.checkDone();
            draftPanel.updateUI();
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(240, 240, 240));

            mainPanel.add(new DraftSideBar(phaseController), BorderLayout.LINE_START);

            draftPanel = new JPanel(new BorderLayout());
            draftPanel.setBackground(BG_COLOR);
            mainPanel.add(draftPanel, BorderLayout.CENTER);

            // TOP
            JPanel top = new JPanel(new BorderLayout());
            top.setBackground(BG_COLOR);

            JLabel blue = new JLabel("Blue");

            JLabel title = new JLabel("Draft");
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setForeground(new Color(240, 244, 232));

            JLabel red = new JLabel("Red");

            Border border = new EmptyBorder(0, 40, 0, 40);
            blue.setBorder(border);
            red.setBorder(border);

            blue.setOpaque(true);
            red.setOpaque(true);

            blue.setBackground(new Color(17, 77, 114));
            red.setBackground(new Color(162, 68, 94));

            Color white = new Color(255, 255, 255);
            blue.setForeground(white);
            red.setForeground(white);

            String fontFamily = "Helvetica";
            title.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 30));

            top.add(blue, BorderLayout.LINE_START);
            top.add(title, BorderLayout.CENTER);
            top.add(red, BorderLayout.LINE_END);
            draftPanel.add(top, BorderLayout.PAGE_START);

            Image unknownIcon = Loader.getUnknownIcon();
            unknownIcon = unknownIcon.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            // LEFT
            pickPanelBlue = new ChampsPanel(unknownIcon, BoxLayout.Y_AXIS, phaseController.getChampsGroups(Group.BLUEPICK));
            draftPanel.add(pickPanelBlue, BorderLayout.LINE_START);

            // RIGHT
            pickPanelRed = new ChampsPanel(unknownIcon, BoxLayout.Y_AXIS, phaseController.getChampsGroups(Group.REDPICK));
            draftPanel.add(pickPanelRed, BorderLayout.LINE_END);

            // MIDDLE
            List<ChampImageBox> allChampIcons = phaseController.getAllChampsIcons();
            champListPanel = new ChampListPanel(allChampIcons);
            draftPanel.add(champListPanel, BorderLayout.CENTER);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            // DOWN
            JPanel down = new JPanel();
            down.setBackground(BG_COLOR);
            banPanelBlue = new ChampsPanel(unknownIcon, BoxLayout.X_AXIS, phaseController.getChampsGroups(Group.BLUEBAN));
            down.add(banPanelBlue);
            JButton lockBtn = new JButton("LOCK IN");
            lockBtn.setPreferredSize(new Dimension(250, 50));
            lockBtn.addMouseListener(new LockInListener());
            down.add(lockBtn);
            banPanelRed = new ChampsPanel(unknownIcon, BoxLayout.X_AXIS, phaseController.getChampsGroups(Group.REDBAN));
            down.add(banPanelRed);
            draftPanel.add(down, BorderLayout.PAGE_END);

            mainFrame.setContentPane(mainPanel);
            mainFrame.setVisible(true);
            mainFrame.setPreferredSize(windowSize);
            mainFrame.pack();
        });
    }
}
