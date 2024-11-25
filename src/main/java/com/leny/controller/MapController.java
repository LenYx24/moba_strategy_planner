package com.leny.controller;

import java.awt.event.MouseWheelEvent;

import javax.swing.ImageIcon;

import com.leny.model.MiniMap;
import com.leny.view.GameView;
import com.leny.view.MapView;

public class MapController {

    MapView mapView;
    MiniMap map;

    public MapController(MiniMap map, MapView mapView) {
        this.map = map;
        this.mapView = mapView;
    }

    public void setup() {
        mapView.setup(map.getEntities());
    }

    public void frameClicked(MouseWheelEvent e) {
        if (e.isControlDown()) {
            double testZoomLevel = map.getZoomLevel() + -1 * e.getUnitsToScroll() / 20.0;
            boolean changedZoom = false;
            // need to check in an intervall, because of the way doubles work
            if (0.98 <= testZoomLevel && testZoomLevel <= 1.02) {
                // reset the image so it doens't look dim
                // in the future I should implement a better method to deal with this
                map.setZoomLevel(1);
                map.resetMapImage();
                changedZoom = true;
            } // If this zoom change wouldn't bring us out of the intervall then we allow it
            else if (0.3 <= testZoomLevel && testZoomLevel <= 3) {
                // multiplying by -1, because if zooming in the method returns a negative number
                map.setZoomLevel(testZoomLevel);
                map.setMapImage(GameView.getResizedImage(map.getMapImage(), map.getZoomLevel()));
                changedZoom = true;
            }
            if (changedZoom) {
                mapView.updateImage(new ImageIcon(map.getMapImage()));
            }
        }
    }
}
