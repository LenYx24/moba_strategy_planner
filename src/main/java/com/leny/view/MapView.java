package com.leny.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.leny.App;

public class MapView{
    JLabel mapLabel;
    public MapView(Image img){
        mapLabel = new JLabel(new ImageIcon(img));
        double ratio = (double)App.windowSize.height / App.windowSize.width;
        int finalWidth = (int)(App.windowSize.width * ratio);
        int finalHeight = App.windowSize.height;
        mapLabel.setBounds(400, 0, finalWidth, finalHeight);
    }
    public void updateImage(ImageIcon icon){
        mapLabel.setIcon(icon);
        mapLabel.updateUI();
    }
    public JLabel get(){
        return mapLabel;
    }
}