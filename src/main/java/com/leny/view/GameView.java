package com.leny.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.leny.controller.GamePhaseController;
import com.leny.controller.MapController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Champion;
import com.leny.model.Champion.Team;
import com.leny.model.Entity;
import com.leny.model.EntityFactory;
import com.leny.model.Loader;
import com.leny.model.MiniMap;
import static com.leny.view.Colors.BG_COLOR;
import static com.leny.view.Colors.BG_COLOR_DARK;

public class GameView {

    private final GamePhaseController phaseController;
    JFrame mainFrame;
    MiniMap map;
    List<Champion> champs;
    List<ChampImageBox> champIcons;
    GameDataBar gameDataBar;
    MapView mapView;
    MapController mapController;
    List<Point> currentLine = null;
    List<List<Point>> lines;

    public GameView(GamePhaseController phaseController, JFrame mainFrame, List<Champion> champs) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
        this.map = new MiniMap();
        this.champs = champs;
        champIcons = Loader.getAllChampsIcons(champs);
        this.gameDataBar = new GameDataBar(phaseController);
        lines = new LinkedList<>();
    }

    private class ChampDataDisplayer extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            ChampImageBox box = (ChampImageBox) e.getSource();
            gameDataBar.update(box.getChamp());
        }
    }

    private class ChampMoveMouseListener extends MouseMotionAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            ChampImageBox box = (ChampImageBox) e.getSource();
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            p.x -= box.getWidth() / 2;
            p.y -= box.getHeight() / 2;
            box.setLocation(p);
            redrawLines();
        }
    }

    private class EntityMoveListener extends MouseMotionAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            EntityImageBox box = (EntityImageBox) e.getSource();
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            p.x -= box.getWidth() / 2;
            p.y -= box.getHeight() / 2;
            box.setLocation(p);
            redrawLines();
        }
    }

    private class DrawListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e) {
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            Graphics2D g = (Graphics2D) mapView.getGraphics();
            if (currentLine != null && !currentLine.isEmpty()) {
                Point pLast = currentLine.get(currentLine.size() - 1);
                g.setStroke(new BasicStroke(5));
                g.drawLine((int) p.getX(), (int) p.getY(), pLast.x, pLast.y);
            }
            if (currentLine != null) {
                currentLine.add(p);
            }
        }
    }

    private void redrawLines() {
        Graphics2D g = (Graphics2D) mapView.getGraphics();
        g.setStroke(new BasicStroke(5));
        for (List<Point> line : lines) {
            for (int i = 1; i < line.size(); i++) {
                Point prev = line.get(i - 1);
                Point current = line.get(i);
                g.drawLine(current.x, current.y, prev.x, prev.y);
            }
        }
    }

    private class DrawInputListener extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {
            currentLine = new LinkedList<>();
        }

        public void mouseExited(MouseEvent e) {
            if (currentLine != null) {
                lines.add(currentLine);
            }
        }
    }

    private class PlaceEntityListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            EntityImageBox box;
            switch (phaseController.getState()) {
                case PLACE_MINION: {
                    Entity minion = EntityFactory.createMinion(p);
                    minion.resizeImg(50);
                    box = new EntityImageBox(minion, p);
                    box.setBorder(BorderFactory.createLineBorder(new Color(44, 234, 63)));
                    box.setBounds(p.x, p.y, 50, 40);
                    break;
                }
                case PLACE_WARD: {
                    Entity ward = EntityFactory.createWard(p);
                    ward.resizeImg(40);
                    box = new EntityImageBox(ward, p);
                    box.setBorder(BorderFactory.createLineBorder(new Color(206, 234, 44)));
                    box.setBounds(p.x, p.y, 40, 40);
                    break;
                }
                default: {
                    return;
                }
            }
            box.addMouseMotionListener(new EntityMoveListener());
            mapView.add(box, JLayeredPane.DRAG_LAYER);
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(240, 240, 240));

            // LEFT
            mainPanel.add(new GameSideBar(phaseController), BorderLayout.LINE_START);

            // MIDDLE
            JPanel mapWrapper = new JPanel(new GridBagLayout());
            mapWrapper.setBackground(BG_COLOR);

            map.setImage(Loader.getMapImage());
            mapView = new MapView(map.getMapImage());
            mapController = new MapController(map, mapView);
            mapController.setup();
            Queue<Point> positions = mapView.getChampPoints();
            for (ChampImageBox champIcon : champIcons) {
                champIcon.addMouseMotionListener(new ChampMoveMouseListener());
                champIcon.addMouseListener(new ChampDataDisplayer());
                champIcon.resize(60);
                Color color = new Color(255, 0, 0);
                if (champIcon.getChamp().getTeam() == Team.Blue) {
                    color = new Color(0, 0, 255);
                }
                Point p = positions.poll();
                champIcon.setBounds(p.x, p.y, 60, 60);
                champIcon.setBorder(BorderFactory.createLineBorder(color));
                mapView.add(champIcon, JLayeredPane.DRAG_LAYER);
            }
            mapView.addMouseMotionListener(new DrawListener());
            mapView.addMouseListener(new DrawInputListener());
            mapView.addMouseListener(new PlaceEntityListener());
            mainPanel.add(mapWrapper, BorderLayout.CENTER);
            mapWrapper.add(mapView);

            // RIGHT
            JPanel rightPanelWrapper = new JPanel();
            rightPanelWrapper.add(gameDataBar);
            rightPanelWrapper.setBackground(BG_COLOR_DARK);
            rightPanelWrapper.setPreferredSize(new Dimension(400, windowSize.height));
            mainPanel.add(rightPanelWrapper, BorderLayout.LINE_END);

            mainFrame.setContentPane(mainPanel);
            mainFrame.setVisible(true);
            mainFrame.pack();
        });
    }
}
