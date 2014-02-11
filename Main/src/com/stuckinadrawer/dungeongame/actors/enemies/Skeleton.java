package com.stuckinadrawer.dungeongame.actors.enemies;

public class Skeleton extends Enemy {
    public Skeleton(int x, int y) {
        super(x, y);
        spriteName = "char_skeleton";
        dmgRange = 5;
        maxHP = 10;
        currentHP = maxHP;
        XPRewarded = 15;
    }
}
