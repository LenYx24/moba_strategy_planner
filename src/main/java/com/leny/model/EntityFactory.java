package com.leny.model;

import java.awt.Image;
import java.awt.Point;

public class EntityFactory {

    public static Entity createMinion(Point p) {
        Image img = Loader.getMapIconImg("minion");
        return new Entity("Minion", img, p);
    }

    public static Entity createWard(Point p) {
        Image img = Loader.getMapIconImg("ward");
        return new Entity("Ward", img, p);
    }
}
