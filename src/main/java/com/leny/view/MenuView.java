package com.leny.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuView {
    boolean windowClosed = false;
    JFrame frame = new JFrame("Lol Marker");
    public void showGui(){
        JLabel title = new JLabel("Test");
        frame.add(title);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public boolean isDone(){
        return windowClosed;
    }
}
