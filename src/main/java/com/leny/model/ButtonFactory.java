package com.leny.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
/**
 * Responsible for creating and styling the buttons used on the UI
 */
public abstract class ButtonFactory {

    private ButtonFactory() {
    }

    public static JButton createMainMenuButton(String name) {
        JButton button = new JButton(name);
        Dimension btnSize = new Dimension(400, 150);
        button.setFont(new Font("Helvetica", Font.TRUETYPE_FONT, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setMaximumSize(btnSize);
        return button;
    }
}
