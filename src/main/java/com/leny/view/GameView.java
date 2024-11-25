package com.leny.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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

    public GameView(GamePhaseController phaseController, JFrame mainFrame, List<Champion> champs) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
        this.map = new MiniMap();
        this.champs = champs;
        champIcons = Loader.getAllChampsIcons(champs);
        this.gameDataBar = new GameDataBar(phaseController);
    }

    public static Image getResizedImage(Image img, double zoomLevel) {
        double ratio = (double) windowSize.height / windowSize.width;
        int finalWidth = (int) (windowSize.width * ratio * zoomLevel);
        int finalHeight = (int) (windowSize.height * zoomLevel);
        return img.getScaledInstance(finalWidth, finalHeight, Image.SCALE_FAST);
    }

    private class ChampDataDisplayer extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            ChampImageBox box = (ChampImageBox) e.getSource();
            gameDataBar.update(box.getChamp());
        }
    }

    private class ChampMoveMouseListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            ChampImageBox box = (ChampImageBox) e.getSource();
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            p.x -= box.getWidth() / 2;
            p.y -= box.getHeight() / 2;
            box.setLocation(p);
        }

        public void mouseMoved(MouseEvent e) {
            // empty
        }
    }

    private class DrawListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e) {
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, mapView);
            Graphics g = mapView.getGraphics();
            g.drawOval((int) p.getX(), (int) p.getY(), 1, 1);
        }
    }

    public class FrameClickListener implements MouseWheelListener {

        public MapController mc;

        public FrameClickListener(MapController mc) {
            this.mc = mc;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            mc.frameClicked(e);
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
            map.setup();
            mapView = new MapView(map.getMapImage());
            MapController mapController = new MapController(map, mapView);
            mapController.setup();
            mainFrame.addMouseWheelListener(new FrameClickListener(mapController));
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
