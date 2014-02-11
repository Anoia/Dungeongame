package com.stuckinadrawer.dungeongame.actors.enemies;

public class Witch extends Enemy{
    public Witch(int x, int y) {
        super(x, y);
        spriteName = "char_witch";
        dmgRange = 6;
        maxHP = 18;
        currentHP = maxHP;
        XPRewarded = 20;
    }
}
