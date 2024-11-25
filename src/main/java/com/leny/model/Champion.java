package com.leny.model;

public class Champion {

    public enum Team {
        Blue,
        Red
    }

    public enum Role {
        Top,
        Jg,
        Mid,
        Adc,
        Supp
    }
    Team team;

    public Champion() {
    }

    public Champion(String n) {
        name = n;
        gold = 0;
        pos = new Vec2(10, 10);
    }
    private Vec2 pos;

    public Vec2 getPos() {
        return pos;
    }

    public void setPos(Vec2 v) {
        pos = v;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    private int gold;

    public int getGold() {
        return gold;
    }

    public void setGold(int g) {
        gold = g;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
