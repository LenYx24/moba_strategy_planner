package com.leny.model;

import java.awt.Image;
import java.awt.Point;
/**
 * Creates those entities which are only different in some of their attributes
 * but behave in the same way as an entity for now
 */
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
