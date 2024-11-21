package com.leny.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import static com.leny.model.AppSettings.windowSize;

public class MapView {

    JLabel mapLabel;

    public MapView(Image img) {
        mapLabel = new JLabel(new ImageIcon(img));
        double ratio = (double) windowSize.height / windowSize.width;
        int finalWidth = (int) (windowSize.width * ratio);
        int finalHeight = windowSize.height;
        mapLabel.setBounds(400, 0, finalWidth, finalHeight);
    }

    public void updateImage(ImageIcon icon) {
        mapLabel.setIcon(icon);
        mapLabel.updateUI();
    }

    public JLabel get() {
        return mapLabel;
    }
}
