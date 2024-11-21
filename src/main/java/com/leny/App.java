package com.leny;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.leny.controller.MapController;
import com.leny.controller.MenuPhaseController;
import com.leny.controller.PhaseController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Map;
import com.leny.view.MapView;

public class App {

    public static Image getResizedImage(Image img, double zoomLevel) {
        double ratio = (double) windowSize.height / windowSize.width;
        int finalWidth = (int) (windowSize.width * ratio * zoomLevel);
        int finalHeight = (int) (windowSize.height * zoomLevel);
        return img.getScaledInstance(finalWidth, finalHeight, Image.SCALE_FAST);
    }

    public static class windowSize {

        public windowSize() {
        }
    }

    private class MenuActionClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("menu clicked");
        }
    }

    private class ChampMoveMouseListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            Point p = e.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(p, panel);
            p.x -= champLabel.getWidth() / 2;
            p.y -= champLabel.getHeight() / 2;
            champLabel.setLocation(p);
        }

        public void mouseMoved(MouseEvent e) {
            // empty
        }
    }

    public class FrameClickListener implements MouseWheelListener {

        public MapController mc;

        public FrameClickListener(MapController mc) {
            this.mc = mc;
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            mc.frameClicked(e);
        }
    }

    JFrame frame = new JFrame("Lol Marker");
    JPanel panel = new JPanel();
    MapController mapController;

    Image champImage = null;
    JLabel champLabel;

    private void createAndShowGUI() {
        frame.setBackground(new Color(255, 255, 0));
        frame.setPreferredSize(windowSize);
        frame.setLayout(new GridBagLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(getWindowPosCentered(windowSize));

        panel.setLayout(null);
        panel.setBackground(new Color(255, 0, 0));

        JMenuBar menuBar = new JMenuBar();
        JMenuItem item1 = new JMenuItem("New");
        item1.addActionListener(new MenuActionClickListener());
        menuBar.add(item1);

        Map map = new Map();
        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(menuBar);
        try {
            map.loadImage();
            champImage = ImageIO.read(ClassLoader.getSystemResource("champs/ahri.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        map.setup();
        MapView mapView = new MapView(map.getMapImage());
        mapController = new MapController(map, mapView);
        frame.addMouseWheelListener(new FrameClickListener(mapController));

        champImage = champImage.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        champLabel = new JLabel(new ImageIcon(champImage));
        champLabel.setBounds(new Rectangle(0, 0, 48, 48));
        champLabel.setPreferredSize(new Dimension(48, 48));
        champLabel.addMouseMotionListener(new ChampMoveMouseListener());

        panel.add(champLabel);
        panel.add(mapView.get());
        panel.setOpaque(false);
        panel.setVisible(true);
        frame.add(panel);
        frame.setContentPane(panel);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private Point getWindowPosCentered(Dimension windowSize) {
        Point result = new Point();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        result.x = (screenSize.width - windowSize.width) / 2;
        result.y = (screenSize.height - windowSize.height) / 2;
        return result;
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("LoL Macro Marker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Object done = new Object();

        List<PhaseController> phases = new LinkedList<>();

        MenuPhaseController menuPhase = new MenuPhaseController(phases, done, mainFrame);
        phases.add(menuPhase);

        PhaseController currentPhaseController = menuPhase;

        while (!phases.isEmpty()) {
            currentPhaseController.setupPhase();
            currentPhaseController = phases.get(phases.indexOf(currentPhaseController) + 1);

            synchronized (done) {
                try {
                    done.wait();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
            System.out.println("Phase done: moving to the next one");
        }
        mainFrame.dispose();
    }
}
