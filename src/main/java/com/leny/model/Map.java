package com.leny.model;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.leny.view.GameView;

public class Map {

    Image mapImage = null;
    Image unchangedMapImage = null;
    double zoomLevel = 1;

    public double getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public Image getMapImage() {
        return mapImage;
    }

    public void setMapImage(Image mapImage) {
        this.mapImage = mapImage;
    }

    public void resetMapImage() {
        mapImage = unchangedMapImage;
    }

    public void loadImage() throws IOException {
        mapImage = ImageIO.read(ClassLoader.getSystemResource("map.jpg"));
    }

    public void setup() {
        unchangedMapImage = mapImage;
        // I need to shrink the mapimages width to keep the ratio of the image
        mapImage = GameView.getResizedImage(mapImage, zoomLevel);
    }
}
