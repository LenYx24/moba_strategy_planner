package com.leny.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * The label creator class
 */
public class LabelFactory {

    public static JLabel createDataLabel(String name) {
        JLabel label = new JLabel(name);
        label.setForeground(Color.white);
        String fontFamily = "Helvetica";
        label.setFont(new Font(fontFamily, Font.TRUETYPE_FONT, 18));
        return label;
    }
}
