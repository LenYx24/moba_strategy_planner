package com.leny.model;

import java.awt.Image;
import java.util.List;
/**
 * Holds the data of a minimap
 */
public class MiniMap {

    Image mapImage = null;
    List<Entity> entities;

    public MiniMap() {
        entities = Loader.getMapElements();
    }

    public Image getMapImage() {
        return mapImage;
    }

    public void setMapImage(Image mapImage) {
        this.mapImage = mapImage;
    }

    public void setImage(Image img) {
        mapImage = img;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
