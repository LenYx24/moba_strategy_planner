package com.leny.view;

import java.awt.Image;

import com.leny.model.Champion;
/**
 * Holds a champ with its imagebox
 */
public class ChampImageBox extends ImageBox {

    Champion champ;

    public ChampImageBox(Image img, Champion champ) {
        super(img);
        this.champ = champ;
    }

    public Champion getChamp() {
        return champ;
    }
}
