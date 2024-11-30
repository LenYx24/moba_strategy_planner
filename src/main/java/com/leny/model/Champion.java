package com.leny.model;
/**
 * Holds the champion entity class
 * some fields are unused yet, there are here for future advancement
 */
public class Champion extends Entity {

    public enum Team {
        BLUE,
        RED
    }
    private int goldEarned;
    private int strength;
    private int level;

    public int getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(int goldEarned) {
        this.goldEarned = goldEarned;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    Team team;

    public Champion() {
    }

    public Champion(String n) {
        name = n;
        goldValue = 300;
        goldEarned = 0;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
