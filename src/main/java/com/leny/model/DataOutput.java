package com.leny.model;

import java.awt.Point;
import java.util.List;
/**
 * Holds those variables which are saved to and loaded from a file
 */
public class DataOutput {

    List<Champion> champs;
    List<Entity> entities;
    List<List<Point>> lines;

    public DataOutput(List<Champion> champs, List<Entity> entities, List<List<Point>> lines) {
        this.champs = champs;
        this.entities = entities;
        this.lines = lines;
    }

    public List<Champion> getChamps() {
        return champs;
    }

    public void setChamps(List<Champion> champs) {
        this.champs = champs;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<List<Point>> getLines() {
        return lines;
    }

    public void setLines(List<List<Point>> lines) {
        this.lines = lines;
    }
}
