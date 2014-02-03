package com.stuckinadrawer.dungeongame.actors.enemies;

public class Rat extends Enemy {
    public Rat(int x, int y) {
        super(x, y);
        spriteName = "char_rat";
        dmgRange = 4;
        maxHP = 10;
        currentHP = maxHP;
    }
}
