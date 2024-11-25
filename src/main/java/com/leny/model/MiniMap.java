package com.leny.model;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import com.leny.model.Champion.Team;
import com.leny.view.GameView;

public class MiniMap {

    Image mapImage = null;
    Image unchangedMapImage = null;
    double zoomLevel = 1;
    List<Entity> entities;

    public MiniMap() {
        entities = Loader.getMapElements();
    }

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

    public void setImage(Image img) {
        mapImage = img;
    }

    public void setup() {
        unchangedMapImage = mapImage;
        // I need to shrink the mapimages width to keep the ratio of the image
        mapImage = GameView.getResizedImage(mapImage, zoomLevel);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Point getChampLocation(Champion champ, JPanel observer) {
        Point p = new Point(0, 0);
        int width = mapImage.getWidth(observer);
        int height = mapImage.getHeight(observer);
        int margin = 40;
        if (champ.getTeam() == Team.Blue) {
            p = new Point(0 + margin, height - margin);
        } else {
            p = new Point(width - margin, 0 + margin);
        }
        return p;
    }
}
