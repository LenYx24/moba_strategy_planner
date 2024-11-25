package com.leny.view;

import java.awt.Point;

import com.leny.model.Entity;

public class EntityImageBox extends ImageBox {

    Entity entity;

    public EntityImageBox(Entity entity, Point pos) {
        super(entity.getImg());
        this.entity = entity;
    }
}
