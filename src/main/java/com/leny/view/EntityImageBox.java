package com.leny.view;

import com.leny.model.Entity;

/**
 * This holds an entity and its corresponding image box
 */
public class EntityImageBox extends ImageBox {

    Entity entity;

    public EntityImageBox(Entity entity) {
        super(entity.getImg());
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
