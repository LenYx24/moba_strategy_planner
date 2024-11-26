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
import com.leny.model.DataOutput;
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
    List<Entity> saveableEntities;

    public GameView(GamePhaseController phaseController, JFrame mainFrame, List<Champion> champs) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
        this.map = new MiniMap();
        this.champs = champs;
        lines = new LinkedList<>();
        saveableEntities = new LinkedList<>();
        this.gameDataBar = new GameDataBar(phaseController);
        champIcons = Loader.getAllChampsIcons(champs);
        map.setImage(Loader.getMapImage());
        mapView = new MapView(map.getMapImage());
        setupChampIcons();
    }

    public GameView(GamePhaseController phaseController, JFrame mainFrame, DataOutput input) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
        this.map = new MiniMap();
        this.champs = input.getChamps();
        lines = input.getLines();
        saveableEntities = input.getEntities();
        // todo saveentities
        this.gameDataBar = new GameDataBar(phaseController);
        champIcons = Loader.getAllChampsIcons(champs);
        map.setImage(Loader.getMapImage());
        mapView = new MapView(map.getMapImage());
        setupChampIcons();
        redrawLines();
        loadEntities(input.getEntities());
    }

    public void setupChampIcons() {
        Queue<Point> positions = mapView.getChampPoints();
        for (ChampImageBox champIcon : champIcons) {
            champIcon.addMouseMotionListener(new ChampMoveMouseListener());
            champIcon.addMouseListener(new ChampDataDisplayer());
            int r = 60;
            champIcon.resize(r);
            Color color = new Color(255, 0, 0);
            if (champIcon.getChamp().getTeam() == Team.BLUE) {
                color = new Color(0, 0, 255);
            }
            Point p = positions.poll();
            Point loc = champIcon.getChamp().getLocation();
            if (loc != null) {
                p = loc;
            } else {
                champIcon.getChamp().setLocation(p);
            }
            champIcon.setBounds(p.x, p.y, r, r);
            champIcon.setBorder(BorderFactory.createLineBorder(color));
            mapView.add(champIcon, JLayeredPane.DRAG_LAYER);
        }
    }

    public List<Champion> getChamps() {
        return champs;
    }

    public List<List<Point>> getDrawnLines() {
        return lines;
    }

    public List<Entity> getSaveableEntities() {
        return saveableEntities;
    }

    public void setLines(List<List<Point>> lines) {
        this.lines = lines;
    }

    public void loadEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            int gold = entity.getGoldValue();
            Point loc = entity.getLocation();
            if (entity.getName().equals("Minion")) {
                entity = EntityFactory.createMinion(loc);
            } else {
                entity = EntityFactory.createWard(loc);
            }
            entity.setGoldValue(gold);
            entity.resizeImg(50);
            EntityImageBox box = new EntityImageBox(entity);
            box.addMouseListener(new EntityDataDisplayer());
            box.addMouseListener(new EntityDeletorListener());
            box.setBorder(BorderFactory.createLineBorder(new Color(44, 234, 63)));
            box.setBounds(loc.x, loc.y, 50, 40);
            saveableEntities.add(entity);
            box.addMouseMotionListener(new EntityMoveListener());
            mapView.add(box, JLayeredPane.DRAG_LAYER);
        }
    }

    private class ChampDataDisplayer extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            ChampImageBox box = (ChampImageBox) e.getSource();
            gameDataBar.update(box.getChamp());
        }
    }

    private class EntityDataDisplayer extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            EntityImageBox box = (EntityImageBox) e.getSource();
            gameDataBar.update(box.getEntity());
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
            box.getChamp().setLocation(new Point(p.x, p.y));
            box.setLocation(p);
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
            box.getEntity().setLocation(new Point(p.x, p.y));
            box.setLocation(p);
        }
    }

    private class DrawListener extends MouseMotionAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (phaseController.getState() != GamePhaseController.GameState.DRAW) {
                return;
            }
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            Graphics2D g = (Graphics2D) mapView.getGraphics();
            if (currentLine != null && !currentLine.isEmpty()) {
                Point pLast = currentLine.get(currentLine.size() - 1);
                g.setStroke(new BasicStroke(2));
                g.drawLine((int) p.getX(), (int) p.getY(), pLast.x, pLast.y);
            }
            if (currentLine != null) {
                currentLine.add(p);
            }
        }
    }

    private void redrawLines() {
        Graphics2D g = (Graphics2D) mapView.getGraphics();
        g.setStroke(new BasicStroke(2));
        for (List<Point> line : lines) {
            for (int i = 1; i < line.size(); i++) {
                Point prev = line.get(i - 1);
                Point current = line.get(i);
                g.drawLine(current.x, current.y, prev.x, prev.y);
            }
        }
    }

    private class DrawInputListener extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            if (phaseController.getState() != GamePhaseController.GameState.DRAW) {
                return;
            }
            currentLine = new LinkedList<>();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (phaseController.getState() != GamePhaseController.GameState.DRAW) {
                return;
            }
            if (currentLine != null) {
                lines.add(currentLine);
            }
            redrawLines();
        }
    }

    private class PlaceEntityListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            EntityImageBox box;
            Entity entity;
            switch (phaseController.getState()) {
                case PLACE_MINION: {
                    entity = EntityFactory.createMinion(p);
                    entity.resizeImg(50);
                    box = new EntityImageBox(entity);
                    box.setBorder(BorderFactory.createLineBorder(new Color(44, 234, 63)));
                    box.setBounds(p.x, p.y, 50, 40);
                    break;
                }
                case PLACE_WARD: {
                    entity = EntityFactory.createWard(p);
                    entity.resizeImg(40);
                    box = new EntityImageBox(entity);
                    box.setBorder(BorderFactory.createLineBorder(new Color(206, 234, 44)));
                    box.setBounds(p.x, p.y, 40, 40);
                    break;
                }
                default: {
                    return;
                }
            }
            saveableEntities.add(entity);
            box.addMouseListener(new EntityDeletorListener());
            box.addMouseMotionListener(new EntityMoveListener());
            box.addMouseListener(new EntityDataDisplayer());
            mapView.add(box, JLayeredPane.DRAG_LAYER);
        }
    }

    private class EntityDeletorListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            EntityImageBox box = (EntityImageBox) e.getSource();
            Entity remove = box.getEntity();
            saveableEntities.remove(remove);
            mapView.remove(box);
            mapView.revalidate();
            mapView.update(mapView.getGraphics());
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
            mapController = new MapController(map, mapView);
            mapController.setup();
            this.setupChampIcons();
            for (ChampImageBox champIcon : champIcons) {
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
            mainFrame.pack();
        });
    }
}
