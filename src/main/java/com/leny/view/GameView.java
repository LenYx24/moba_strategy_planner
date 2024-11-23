package com.leny.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.leny.controller.GamePhaseController;
import com.leny.controller.MapController;
import static com.leny.model.AppSettings.windowSize;
import com.leny.model.Map;

public class GameView {

    private final GamePhaseController phaseController;
    JFrame mainFrame;
    Image champImage;
    JLabel champLabel;
    JPanel panel;
    Map map;

    public GameView(GamePhaseController phaseController, JFrame mainFrame) {
        this.phaseController = phaseController;
        this.mainFrame = mainFrame;
        this.champImage = null;
        this.map = new Map();
        this.champLabel = null;
    }

    public static Image getResizedImage(Image img, double zoomLevel) {
        double ratio = (double) windowSize.height / windowSize.width;
        int finalWidth = (int) (windowSize.width * ratio * zoomLevel);
        int finalHeight = (int) (windowSize.height * zoomLevel);
        return img.getScaledInstance(finalWidth, finalHeight, Image.SCALE_FAST);
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

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            mc.frameClicked(e);
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("SHOWING GAMEPHASE");
            mainFrame.setBackground(new Color(255, 255, 0));
            mainFrame.setLayout(new GridBagLayout());

            panel = new JPanel();

            mainFrame.setBackground(new Color(200, 200, 200));
            panel.setBackground(new Color(240, 240, 240));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);

            BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

            panel.setLayout(boxLayout);

            JLabel title = new JLabel("Draft");
            String fontFamily = "Helvetica";
            title.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));
            JButton backBtn = new JButton("Back");
            Dimension btnSize = new Dimension(200, 30);
            backBtn.setMaximumSize(btnSize);
            backBtn.addActionListener((ActionEvent event) -> {
                phaseController.back();
                phaseController.complete();
            });
            backBtn.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 60));
            backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            //Set the menu bar and add the label to the content pane.
            try {
                map.loadImage();
                champImage = ImageIO.read(ClassLoader.getSystemResource("champs/ahri.png"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            map.setup();
            MapView mapView = new MapView(map.getMapImage());
            MapController mapController = new MapController(map, mapView);
            mainFrame.addMouseWheelListener(new FrameClickListener(mapController));

            champImage = champImage.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            champLabel = new JLabel(new ImageIcon(champImage));
            champLabel.setBounds(new Rectangle(0, 0, 48, 48));
            champLabel.setPreferredSize(new Dimension(48, 48));
            champLabel.addMouseMotionListener(new ChampMoveMouseListener());

            panel.add(champLabel);
            panel.add(mapView.get());
            panel.setOpaque(false);
            panel.setVisible(true);

            panel.add(title);
            panel.add(javax.swing.Box.createVerticalStrut(40));
            panel.add(backBtn);

            mainFrame.setContentPane(panel);
            mainFrame.setVisible(true);
            mainFrame.pack();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
