package com.leny.model;

import com.sun.javafx.geom.Vec2d;

public class Champion {
    public Champion(){}
    public Champion(String n, int g){
        name = n;
        gold = g;
        pos = new Vec2d(10,10);
    }
    private Vec2d pos;
    public Vec2d getPos(){return pos;}
    public void setPos(Vec2d v){pos = v;}

    private String name;
    public String getName(){return name;}
    public void setName(String n){name=n;}

    private int gold;
    public int getGold(){return gold;}
    public void setGold(int g){gold=g;}
}
