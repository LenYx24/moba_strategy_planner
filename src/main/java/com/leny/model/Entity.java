package com.leny.model;

import java.awt.Image;
import java.awt.Point;

public class Entity {

    String name;
    transient Image icon;
    double xp;
    double yp;
    int goldValue;
    Point pos = null;

    public Entity() {
    }

    public Entity(String name, Image icon, double xp, double yp) {
        this.icon = icon;
        this.name = name;
        this.xp = xp;
        this.yp = yp;
    }

    public Entity(String name, Image icon, Point pos) {
        this.name = name;
        this.icon = icon;
        this.pos = pos;
    }

    public Image getImg() {
        return icon;
    }

    public void resizeImg(int r) {
        icon = icon.getScaledInstance(r, r, Image.SCALE_SMOOTH);
    }

    public Point getLocation(int w, int h) {
        int wr = (int) Math.round(xp * w);
        int hr = (int) Math.round(yp * h);
        return new Point(wr, hr);
    }

    public Point getLocation() {
        return pos;
    }

    public void setLocation(Point pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }
}
