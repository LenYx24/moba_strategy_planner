package com.leny.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;

public class SidebarButton extends JButton {

    private class SelectionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            b.setBackground(new Color(110, 240, 55));
            for (SidebarButton button : buttons.values()) {
                if (b != button) {
                    button.resetColor();
                }
            }
            onclick.accept(e);
        }

    }
    Consumer<ActionEvent> onclick;
    Map<String, SidebarButton> buttons;

    public SidebarButton(String text, Consumer<ActionEvent> onclick, Map<String, SidebarButton> buttons) {
        super(text);
        Dimension btnSize = new Dimension(100, 100);
        this.setPreferredSize(btnSize);
        this.setMaximumSize(btnSize);
        this.setMinimumSize(btnSize);
        this.addActionListener(new SelectionListener());
        this.onclick = onclick;
        this.buttons = buttons;
    }

    public void resetColor() {
        this.setBackground(Color.white);
    }
}
