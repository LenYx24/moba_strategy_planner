package com.leny;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class App {
    private double mapImageScaler = 0.9;
    private Point getWindowPosCentered(Dimension windowSize){
        Point result = new Point();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        result.x = (screenSize.width - windowSize.width) /2;
        result.y = (screenSize.height - windowSize.height) /2;
        return result;
    }
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Lol Marker");
        Color c = new Color(100,100,100);
        frame.setBackground(c);
        Dimension windowSize = new Dimension(1600,800);
        frame.setPreferredSize(windowSize);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(getWindowPosCentered(windowSize));

        Image mapimage = null;
        try {
            mapimage = ImageIO.read(ClassLoader.getSystemResource("map.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // I need to shrink the mapimages width to keep the ratio of the image
        double ratio = (double)windowSize.height / windowSize.width;
        int finalWidth = (int)(windowSize.width*ratio*mapImageScaler);
        System.out.println(finalWidth);
        mapimage = mapimage.getScaledInstance(finalWidth, (int)(windowSize.height*mapImageScaler), Image.SCALE_SMOOTH);
        ImageIcon mapicon = new ImageIcon(mapimage);
        JLabel map = new JLabel(mapicon);
        frame.add(map);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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