package com.leny.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageBox extends JLabel {

    Image img;

    public ImageBox(Image img) {
        super(new ImageIcon(img));
        this.img = img;
        img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    }
}
