package com.stuckinadrawer.dungeongame.items;

public class Weapon extends Item{
    private int attackSpeed;
    private int minDamage;
    private int maxDamage;
    private int range;
    private int accuracy;

    public Weapon(String name, int attackSpeed, int minDamage, int maxDamage, int range, int accuracy) {
        super(name);
        this.attackSpeed = attackSpeed;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.range = range;
        this.accuracy = accuracy;
    }
}
