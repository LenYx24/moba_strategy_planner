package com.leny.model;

import java.awt.Point;

public class Champion extends Entity {

    public enum Team {
        BLUE,
        RED
    }

    public enum Role {
        TOP,
        JG,
        MID,
        ADC,
        SUPP
    }
    private int goldEarned;
    Team team;

    public Champion() {
    }

    public Champion(String n) {
        name = n;
        goldValue = 300;
        goldEarned = 0;
        setPos(new Point(0, 0));
    }

    public int getGoldValue() {
        return goldValue;
    }

    public void setGoldValue(int g) {
        goldValue = g;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
