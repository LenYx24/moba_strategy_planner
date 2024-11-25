package com.leny.view;

import java.awt.Dimension;

import javax.swing.JButton;

public class SidebarButton extends JButton {

    public SidebarButton(String text) {
        super(text);
        Dimension btnSize = new Dimension(100, 100);
        this.setPreferredSize(btnSize);
        this.setMaximumSize(btnSize);
        this.setMinimumSize(btnSize);
    }
}
