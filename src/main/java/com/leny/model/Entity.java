package com.leny.model;

import java.awt.Image;
import java.awt.Point;

public class Entity {

    String name;
    Image icon;
    double xp;
    double yp;

    public Entity(String name, Image icon, double xp, double yp) {
        this.icon = icon;
        this.name = name;
        this.xp = xp;
        this.yp = yp;
    }

    public Image getImg() {
        return icon;
    }

    public Point getLocation(int w, int h) {
        int wr = (int) Math.round(xp * w);
        int hr = (int) Math.round(yp * h);
        return new Point(wr, hr);
    }

    public String getName() {
        return name;
    }
}
