package com.leny.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * Holds the statically available application settings
 * which are globally true across the application
 */
public class AppSettings {

    public static final Dimension windowSize = new Dimension(1600, 800);

    public static Point getWindowPosCentered(Dimension windowSize) {
        Point result = new Point();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        result.x = (screenSize.width - windowSize.width) / 2;
        result.y = (screenSize.height - windowSize.height) / 2;
        return result;
    }
}
