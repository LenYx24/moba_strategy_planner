package com.leny.view;

import java.awt.Dimension;
import java.awt.Image;

import com.leny.model.Champion;

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
