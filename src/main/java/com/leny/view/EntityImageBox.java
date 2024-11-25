package com.leny.view;

import com.leny.model.Entity;

public class EntityImageBox extends ImageBox {

    Entity entity;

    public EntityImageBox(Entity entity) {
        super(entity.getImg());
        this.entity = entity;
        int r = 50;
        resize(r);
    }
}
