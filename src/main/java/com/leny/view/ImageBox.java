package com.leny.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageBox extends JLabel {

    Image img;

    public ImageBox(Image img) {
        super(new ImageIcon(img));
        this.img = img;
    }

    public void resize(int r) {
        img = img.getScaledInstance(r, r, Image.SCALE_SMOOTH);
    }
}
