package com.leny;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

public class App {
    private class FrameClickListener implements MouseWheelListener{
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.isControlDown()){
                double testZoomLevel = zoomLevel + -1 * e.getUnitsToScroll() / 20.0;
                // need to check in an intervall, because of the way doubles work
                if(0.98 <= testZoomLevel && testZoomLevel <= 1.02){
                    // reset the image so it doens't look dim
                    // in the future I should implement a better method to deal with this
                    zoomLevel = 1;
                    mapImage = unchangedMapImage;
                    maplabel.setIcon(new ImageIcon(mapImage));
                    maplabel.updateUI();
                }
                // If this zoom change wouldn't bring us out of the intervall then we allow it
                else if(0.3 <= testZoomLevel && testZoomLevel <= 3){
                    // multiplying by -1, because if zooming in the method returns a negative number
                    zoomLevel = testZoomLevel;
                    mapImage = getResizedImage(mapImage);
                    maplabel.setIcon(new ImageIcon(mapImage));
                    maplabel.updateUI();
                }
            }
        }
    }
    private Image getResizedImage(Image img){
        double ratio = (double)windowSize.height / windowSize.width;
        int finalWidth = (int)(windowSize.width*ratio*zoomLevel);
        int finalHeight = (int)(windowSize.height*zoomLevel);
        return img.getScaledInstance(finalWidth, finalHeight, Image.SCALE_FAST);
    }

    Dimension windowSize = new Dimension(1600,800);
    Image mapImage = null;
    Image unchangedMapImage = null;
    double zoomLevel = 1;
    JFrame frame = new JFrame("Lol Marker");
    JLabel maplabel;
    
    private void createAndShowGUI() {
        frame.setBackground(new Color(255,255,0));
        frame.setPreferredSize(windowSize);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(getWindowPosCentered(windowSize));

        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));

        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(greenMenuBar);
        //frame.add(yellowLabel, BorderLayout.CENTER);

        try {
            mapImage = ImageIO.read(ClassLoader.getSystemResource("map.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        unchangedMapImage = mapImage;
        // I need to shrink the mapimages width to keep the ratio of the image
        mapImage = getResizedImage(mapImage);
        maplabel = new JLabel(new ImageIcon(mapImage));
        frame.add(maplabel);
        frame.addMouseWheelListener(new FrameClickListener());

        BufferedImage resizedImage;
        resizedImage = new BufferedImage(1000 , 1000, Image.SCALE_SMOOTH);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(resizedImage, 0, 0, 1000 , 1000 , null);
        g.dispose();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private Point getWindowPosCentered(Dimension windowSize){
        Point result = new Point();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        result.x = (screenSize.width - windowSize.width) /2;
        result.y = (screenSize.height - windowSize.height) /2;
        return result;
    }
   
    public static void main(String[] args) {
        App app = new App();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app.createAndShowGUI();
            }
        });
    }
}