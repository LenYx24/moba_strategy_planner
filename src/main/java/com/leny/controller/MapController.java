package com.leny.controller;

import com.leny.model.MiniMap;
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

}
